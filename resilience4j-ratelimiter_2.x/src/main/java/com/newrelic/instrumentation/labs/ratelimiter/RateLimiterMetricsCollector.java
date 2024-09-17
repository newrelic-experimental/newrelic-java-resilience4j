package com.newrelic.instrumentation.labs.ratelimiter;

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
import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiter.Metrics;

public class RateLimiterMetricsCollector implements AgentConfigListener {

	private static final String RATELIMITER_METRICS_ENABLED = "Resilience4j.metrics.ratelimiter.enabled";
	private static final String RATELIMITER_METRICS_INTERVAL = "Resilience4j.metrics.ratelimiter.intervalminutes";
	private static Set<RateLimiter> rateLimiters = new HashSet<>();
	private static boolean initialized = false;
	private static boolean enabled = true;
	private static long interval = 1L;
	private static ScheduledFuture<?> future = null;

	private RateLimiterMetricsCollector() {
	}

	public static void addRateLimiter(RateLimiter rateLimiter) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Call to add RateLimiter {0} to list to collect metrics from",
				rateLimiter);
		if (!initialized) {
			init();
		}

		if (!rateLimiters.contains(rateLimiter)) {
			rateLimiters.add(rateLimiter);
		}
	}

	private static void setupCollector() {
		NewRelic.getAgent().getLogger().log(Level.FINE,
				"Call to setup RateLimiter Metrics Collector, enabled = {0}, interval = {1}", enabled, interval);
		if (future != null) {
			future.cancel(true);
			future = null;
		}
		if (enabled) {
			Collector collector = new Collector();
			future = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(collector, interval, interval,
					TimeUnit.MINUTES);
		}
	}

	private static void init() {
		ConfigService configService = ServiceFactory.getConfigService();
		configService.addIAgentConfigListener(new RateLimiterMetricsCollector());
		Config config = NewRelic.getAgent().getConfig();
		setValues(config, RateLimiterMetricsCollector.class);
		setupCollector();
		initialized = true;
	}

	private static class Collector implements Runnable {

		@Override
		public void run() {
			for (RateLimiter rateLimiter : rateLimiters) {
				String name = rateLimiter.getName();
				Metrics metrics = rateLimiter.getMetrics();
				HashMap<String, Object> attributes = new HashMap<>();
				attributes.put("Name", name);
				attributes.put("AvailablePermissions", metrics.getAvailablePermissions());
				attributes.put("NumberOfWaitingThreads", metrics.getNumberOfWaitingThreads());

				NewRelic.getAgent().getInsights().recordCustomEvent("RateLimiterMetrics", attributes);
			}
		}
	}

	private static boolean setValues(Config config, Class<?> clazz) {
		Object obj = config.getValue(RATELIMITER_METRICS_ENABLED);
		NewRelic.getAgent().getLogger().log(Level.FINE, "Call to {0}.configChanged, got value for {1} of {2}", clazz,
				RATELIMITER_METRICS_ENABLED, obj);
		boolean configPresent = false;
		if (obj != null) {
			configPresent = true;
			if (obj instanceof Boolean) {
				enabled = (Boolean) obj;
			} else {
				try {
					String tmp = obj.toString();
					enabled = Boolean.parseBoolean(tmp);
				} catch (Exception e) {
					NewRelic.getAgent().getLogger().log(Level.FINER, e,
							"Failed to get enabled value for RateLimiter from {0}", obj);
				}
			}
		}

		obj = config.getValue(RATELIMITER_METRICS_INTERVAL);
		NewRelic.getAgent().getLogger().log(Level.FINE, "Call to {0}.configChanged, got value for {1} of {2}", clazz,
				RATELIMITER_METRICS_INTERVAL, obj);
		if (obj != null) {
			configPresent = true;
			if (obj instanceof Long) {
				interval = (Long) obj;
			} else {
				try {
					String tmp = obj.toString();
					interval = Long.parseLong(tmp);
				} catch (Exception e) {
					NewRelic.getAgent().getLogger().log(Level.FINER, e,
							"Failed to get interval value for RateLimiter from {0}", obj);
				}
			}
		}
		return configPresent;
	}

	@Override
	public void configChanged(String appName, AgentConfig agentConfig) {
		boolean configPresent = setValues(agentConfig, getClass());
		if (configPresent) {
			setupCollector();
		}
	}
}