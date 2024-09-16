package io.github.resilience4j.bulkhead.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.github.resilience4j.bulkhead.event.BulkheadEvent;
import io.github.resilience4j.bulkhead.event.BulkheadEvent.Type;

@Weave
public class SemaphoreBulkhead {
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
