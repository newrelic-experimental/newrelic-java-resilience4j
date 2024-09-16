package io.github.resilience4j.timelimiter.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.github.resilience4j.timelimiter.event.TimeLimiterEvent;
import io.github.resilience4j.timelimiter.event.TimeLimiterEvent.Type;;

@Weave
public class TimeLimiterImpl {

	@Weave
	private static class TimeLimiterEventProcessor {

		@SuppressWarnings("unused")
		public void consumeEvent(TimeLimiterEvent event) {
			String name = event.getTimeLimiterName();
			Type type = event.getEventType();
			String metricName = "Custom/Resilience4j/TimeLimiter/" + name + "/" + type.name();
			NewRelic.recordMetric(metricName, 1F);
			Weaver.callOriginal();
		}
	}
}
