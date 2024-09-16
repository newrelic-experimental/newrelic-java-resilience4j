package com.newrelic.instruementation.labs.circuitbreaker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.newrelic.agent.config.AgentConfig;
import com.newrelic.agent.config.AgentConfigListener;
import com.newrelic.agent.config.ConfigService;
import com.newrelic.agent.service.ServiceFactory;
import com.newrelic.api.agent.NewRelic;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.Metrics;

public class MetricsCollector implements AgentConfigListener {

	private static final String CIRCUITBREAKERMETRICSENABLED = "Resilience4j.metrics.circuitbreaker.enabled";
	private static final String CIRCUITBREAKERMETRICSINTERVAL = "Resilience4j.metrics.circuitbreaker.intervalminutes";
	private static Set<CircuitBreaker> circuitBreakers = new HashSet<CircuitBreaker>();
	private static boolean initialized = false;
	private static boolean enabled = true;
	private static long interval = 1L;
	private static ScheduledFuture<?> future = null;
	
	private MetricsCollector() {
		
	}

	public static void addCircuitBreaker(CircuitBreaker circuitBreaker) {
		if(!initialized) {
			init();
		}

		if(!circuitBreakers.contains(circuitBreaker)) {
			circuitBreakers.add(circuitBreaker);
		}
	}

	private static void setupCollector() {
		if(future != null) {
			future.cancel(true);
			future = null;
		}
		if(enabled) {
			Collector collector = new Collector();
			future = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(collector, interval, interval, TimeUnit.MINUTES);
		}
	}
	
	private static void init() {
		ConfigService configService = ServiceFactory.getConfigService();
		configService.addIAgentConfigListener(new MetricsCollector());
		setupCollector();
		initialized = true;
	}


	private static class Collector implements Runnable {

		@Override
		public void run() {

			for(CircuitBreaker breaker : circuitBreakers) {
				String name = breaker.getName();
				Metrics metrics = breaker.getMetrics();
				HashMap<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("Name", name);
				attributes.put("FailureRate", metrics.getFailureRate());
				attributes.put("BufferedCalls", metrics.getNumberOfBufferedCalls());
				attributes.put("Failed", metrics.getNumberOfFailedCalls());
				attributes.put("NotPermittedCalls", metrics.getNumberOfNotPermittedCalls());
				attributes.put("SlowCalls", metrics.getNumberOfSlowCalls());
				attributes.put("SuccessfulCalls", metrics.getNumberOfSuccessfulCalls());
				attributes.put("SlowCallRate", metrics.getSlowCallRate());
				NewRelic.getAgent().getInsights().recordCustomEvent("CircuitBreakerMetrics", attributes);
			}

		}
	}


	@Override
	public void configChanged(String appName, AgentConfig agentConfig) {
		Object obj = agentConfig.getValue(CIRCUITBREAKERMETRICSENABLED);
		boolean configPresent = false;
		if(obj != null) {
			configPresent = true;
			if(obj instanceof Boolean) {
				enabled = (Boolean)obj;
			} else {
				try {
					String tmp = obj.toString();
					enabled = Boolean.getBoolean(tmp);
				} catch (Exception e) {
					NewRelic.getAgent().getLogger().log(Level.FINER, e, "Failed to get enabled value for CircuitBreaker from {0}", obj);
				}
			}
		}
		
		obj = agentConfig.getValue(CIRCUITBREAKERMETRICSINTERVAL);
		if(obj != null) {
			configPresent = true;
			if(obj instanceof Long) {
				interval = (Long)obj;
			} else {
				try {
					String tmp = obj.toString();
					interval = Long.parseLong(tmp);
				} catch (Exception e) {
					NewRelic.getAgent().getLogger().log(Level.FINER, e, "Failed to get interval value for CircuitBreaker from {0}", obj);
				}
			}
		}
		if(configPresent) {
			setupCollector();
		}
	}
}
