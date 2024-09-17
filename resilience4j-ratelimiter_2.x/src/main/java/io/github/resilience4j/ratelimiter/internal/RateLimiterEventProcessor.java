package io.github.resilience4j.ratelimiter.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.github.resilience4j.ratelimiter.event.RateLimiterEvent;
import io.github.resilience4j.ratelimiter.event.RateLimiterEvent.Type;;

@Weave
public class RateLimiterEventProcessor {
	@SuppressWarnings("unused")
	public void consumeEvent(RateLimiterEvent event) {
		String name = event.getRateLimiterName();
		Type type = event.getEventType();
		String metricName = "Custom/Resilience4j/RateLimiter/" + name + "/" + type.name();
		NewRelic.recordMetric(metricName, 1F);
		Weaver.callOriginal();
	}

}
