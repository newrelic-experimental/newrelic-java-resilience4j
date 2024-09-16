package io.github.resilience4j.circuitbreaker.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent.Type;

@Weave
public class CircuitBreakerStateMachine {
	
	@Weave
	private static class CircuitBreakerEventProcessor {
		
		@SuppressWarnings("unused")
		public void consumeEvent(CircuitBreakerEvent event) {
			String name = event.getCircuitBreakerName();
			Type type = event.getEventType();
			String metricName = "Custom/Resilience4j/CircuitBreaker/" + name + "/" + type.name();
			NewRelic.recordMetric(metricName, 1F);
			Weaver.callOriginal();
		}
	}

}
