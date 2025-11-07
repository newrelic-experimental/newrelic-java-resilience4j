package io.github.resilience4j.bulkhead.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import com.newrelic.instrumentation.labs.bulkhead.NRBiConsumer;
import com.newrelic.instrumentation.labs.bulkhead.NRCallableWrapper;
import com.newrelic.instrumentation.labs.bulkhead.NRHolder;
import com.newrelic.instrumentation.labs.bulkhead.NRRunnableWrapper;
import com.newrelic.instrumentation.labs.bulkhead.Utils;
import io.github.resilience4j.bulkhead.event.BulkheadEvent;
import io.github.resilience4j.bulkhead.event.BulkheadEvent.Type;;import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Weave
public abstract class FixedThreadPoolBulkhead {

	public abstract String getName();

	@Weave
	private static class BulkheadEventProcessor {

		@SuppressWarnings("unused")
		public void consumeEvent(BulkheadEvent event) {
			String name = event.getBulkheadName();
			Type type = event.getEventType();
			String metricName = "Custom/Resilience4j/BulkHead/" + name + "/" + type.name();
			NewRelic.recordMetric(metricName, 1F);
			Weaver.callOriginal();
		}
	}
}
