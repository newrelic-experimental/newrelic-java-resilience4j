package io.github.resilience4j.circuitbreaker;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instruementation.labs.circuitbreaker.MetricsCollector;

import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Either;
import io.vavr.control.Try;

@Weave(type = MatchType.Interface)
public abstract class CircuitBreaker {
	
	@WeaveAllConstructors
	public CircuitBreaker() {
		MetricsCollector.addCircuitBreaker(this);
	}

	public abstract String getName();
	public abstract Metrics getMetrics();
	
	@Trace(dispatcher = true)
	public <T> T executeCallable(Callable<T> callable)  {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Resilience4j","CircuitBreaker",getName(),"executeCallable");
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public void executeCheckedRunnable(CheckedRunnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Resilience4j","CircuitBreaker",getName(),"executeCheckedRunnable");
		Weaver.callOriginal();
	}
	
	
	@Trace(dispatcher = true)
	public <T> T executeCheckedSupplier(CheckedFunction0<T> checkedSupplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Resilience4j","CircuitBreaker",getName(),"executeCheckedSupplier");
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public <T> CompletionStage<T> executeCompletionStage(Supplier<CompletionStage<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Resilience4j","CircuitBreaker",getName(),"executeCompletionStage");
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public <T> Either<Exception, T> executeEitherSupplier(Supplier<Either<? extends Exception, T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Resilience4j","CircuitBreaker",getName(),"executeEitherSupplier");
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public void executeRunnable(Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Resilience4j","CircuitBreaker",getName(),"executeCheckedRunnable");
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public <T> T executeSupplier(Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Resilience4j","CircuitBreaker",getName(),"executeEitherSupplier");
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public <T> Try<T> executeTrySupplier(Supplier<Try<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Resilience4j","CircuitBreaker",getName(),"executeTrySupplier");
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public void onError(long duration, TimeUnit durationUnit, Throwable throwable) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","Resilience4j","CircuitBreaker",getName(),"onError");
		NewRelic.noticeError(throwable);
		traced.addCustomAttribute("duration", duration);
		traced.addCustomAttribute("durationUnit", durationUnit.name());
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public void onSuccess(long duration, TimeUnit durationUnit) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName("Custom","Resilience4j","CircuitBreaker",getName(),"onSuccess");
		traced.addCustomAttribute("duration", duration);
		traced.addCustomAttribute("durationUnit", durationUnit.name());
		Weaver.callOriginal();
	}
	
	@Weave(type=MatchType.Interface)
	public static abstract class Metrics {
		
        public abstract float getFailureRate();

        public abstract float getSlowCallRate();

        public abstract int getNumberOfSlowCalls();

        public abstract int getNumberOfBufferedCalls();

        public abstract int getNumberOfFailedCalls();

        public abstract long getNumberOfNotPermittedCalls();

        public abstract int getNumberOfSuccessfulCalls();
	}
}
