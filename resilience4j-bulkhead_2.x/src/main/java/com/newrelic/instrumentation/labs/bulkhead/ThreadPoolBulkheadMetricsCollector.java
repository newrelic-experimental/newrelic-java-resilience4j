package com.newrelic.instrumentation.labs.bulkhead;

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

import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead.Metrics;

public class ThreadPoolBulkheadMetricsCollector implements AgentConfigListener {

	private static final String THREADPOOLBULKHEADMETRICSENABLED = "Resilience4j.metrics.threadpoolbulkhead.enabled";
	private static final String THREADPOOLBULKHEADMETRICSINTERVAL = "Resilience4j.metrics.threadpoolbulkhead.intervalminutes";
	private static Set<ThreadPoolBulkhead> threadpoolbulkheads = new HashSet<>();
	private static boolean initialized = false;
	private static boolean enabled = true;
	private static long interval = 1L;
	private static ScheduledFuture<?> future = null;

	private ThreadPoolBulkheadMetricsCollector() {
	}

	public static void addThreadPoolBulkhead(ThreadPoolBulkhead threadpoolbulkhead) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Call to add Bulkhead {0} to list to collect metrics from",
				threadpoolbulkhead);
		if (!initialized) {
			init();
		}

		if (!threadpoolbulkheads.contains(threadpoolbulkhead)) {
			threadpoolbulkheads.add(threadpoolbulkhead);
		}
	}

	private static void setupCollector() {
		NewRelic.getAgent().getLogger().log(Level.FINE,
				"Call to setup Bulkhead Metrics Collector, enabled = {0}, interval = {1}", enabled, interval);
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
		configService.addIAgentConfigListener(new ThreadPoolBulkheadMetricsCollector());
		Config config = NewRelic.getAgent().getConfig();
		setValues(config, ThreadPoolBulkheadMetricsCollector.class);
		setupCollector();
		initialized = true;
	}

	private static class Collector implements Runnable {

		@Override
		public void run() {
			for (ThreadPoolBulkhead threadpoolbulkhead : threadpoolbulkheads) {
				String name = threadpoolbulkhead.getName();
				Metrics metrics = threadpoolbulkhead.getMetrics();
				HashMap<String, Object> attributes = new HashMap<>();
				attributes.put("Name", name);
				attributes.put("CoreThreadPoolSize", metrics.getCoreThreadPoolSize());
				attributes.put("ThreadPoolSize", metrics.getThreadPoolSize());
				attributes.put("MaximumThreadPoolSize", metrics.getMaximumThreadPoolSize());
				attributes.put("QueueDepth", metrics.getQueueDepth());
				attributes.put("RemainingQueueCapacity", metrics.getRemainingQueueCapacity());
				attributes.put("QueueCapacity", metrics.getQueueCapacity());

				NewRelic.getAgent().getInsights().recordCustomEvent("ThreadPoolBulkheadMetrics", attributes);
			}
		}
	}

	private static boolean setValues(Config config, Class<?> clazz) {
		Object obj = config.getValue(THREADPOOLBULKHEADMETRICSENABLED);
		NewRelic.getAgent().getLogger().log(Level.FINE, "Call to {0}.configChanged, got value for {1} of {2}", clazz,
				THREADPOOLBULKHEADMETRICSENABLED, obj);
		boolean configPresent = false;
		if (obj != null) {
			configPresent = true;
			if (obj instanceof Boolean) {
				enabled = (Boolean) obj;
			} else {
				try {
					String tmp = obj.toString();
					interval = Long.parseLong(tmp);
				} catch (Exception e) {
					NewRelic.getAgent().getLogger().log(Level.FINER, e,
							"Failed to get interval value for Bulkhead from {0}", obj);
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