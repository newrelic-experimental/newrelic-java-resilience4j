package io.github.resilience4j.retry.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.github.resilience4j.retry.event.RetryEvent;
import io.github.resilience4j.retry.event.RetryEvent.Type;;
@Weave
public class RetryImpl<T> {

	@Weave
	private static class RetryEventProcessor {

		@SuppressWarnings("unused")
		public void consumeEvent(RetryEvent event) {
			String name = event.getName();
			Type type = event.getEventType();
			String metricName = "Custom/Resilience4j/Retry/" + name + "/" + type.name();
			NewRelic.recordMetric(metricName, 1F);
			Weaver.callOriginal();
		}
	}
}
