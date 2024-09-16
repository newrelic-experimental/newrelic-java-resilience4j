package io.github.resilience4j.bulkhead;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class ThreadPoolBulkhead implements AutoCloseable {

	public abstract String getName();

	@Trace
	public <T> CompletionStage<T> submit(Callable<T> task) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(),
				"submitCallable");
		return Weaver.callOriginal();
	}

	@Trace
	public CompletionStage<Void> submit(Runnable task) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(),
				"submitRunnable");
		return Weaver.callOriginal();
	}

	@Trace
	public ThreadPoolBulkheadConfig getBulkheadConfig() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(),
				"getBulkheadConfig");
		return Weaver.callOriginal();
	}

	@Trace
	public <T> Supplier<CompletionStage<T>> decorateSupplier(Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(),
				"decorateSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public <T> Supplier<CompletionStage<T>> decorateCallable(Callable<T> callable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(),
				"decorateCallable");
		return Weaver.callOriginal();
	}

	@Trace
	public Supplier<CompletionStage<Void>> decorateRunnable(Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(),
				"decorateRunnable");
		return Weaver.callOriginal();
	}

	@Trace
	public <T> CompletionStage<T> executeSupplier(Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(),
				"executeSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public <T> CompletionStage<T> executeCallable(Callable<T> callable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(),
				"executeCallable");
		return Weaver.callOriginal();
	}

	@Trace
	public CompletionStage<Void> executeRunnable(Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(),
				"executeRunnable");
		return Weaver.callOriginal();
	}

	// Static methods
	@Trace
	public static <T> Supplier<CompletionStage<T>> decorateCallable(ThreadPoolBulkhead bulkhead, Callable<T> callable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead",
				bulkhead.getName(), "decorateCallable");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Supplier<CompletionStage<T>> decorateSupplier(ThreadPoolBulkhead bulkhead, Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead",
				bulkhead.getName(), "decorateSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public static Supplier<CompletionStage<Void>> decorateRunnable(ThreadPoolBulkhead bulkhead, Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead",
				bulkhead.getName(), "decorateRunnable");
		return Weaver.callOriginal();
	}

}
