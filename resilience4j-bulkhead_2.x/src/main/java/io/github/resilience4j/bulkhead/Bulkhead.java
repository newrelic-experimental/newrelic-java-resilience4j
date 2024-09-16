package io.github.resilience4j.bulkhead;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.github.resilience4j.core.functions.CheckedConsumer;
import io.github.resilience4j.core.functions.CheckedFunction;
import io.github.resilience4j.core.functions.CheckedRunnable;
import io.github.resilience4j.core.functions.CheckedSupplier;
import io.github.resilience4j.core.functions.Either;

@Weave(type = MatchType.Interface)
public abstract class Bulkhead {

	public abstract String getName();

	@Trace
	public <T> T executeCallable(Callable<T> callable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", getName(),
				"executeCallable");
		return Weaver.callOriginal();
	}

	@Trace
	public <T> T executeCheckedSupplier(CheckedSupplier<T> checkedSupplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", getName(),
				"executeCheckedSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public <T> CompletionStage<T> executeCompletionStage(Supplier<CompletionStage<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", getName(),
				"executeCompletionStage");
		return Weaver.callOriginal();
	}

	@Trace
	public void executeRunnable(Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", getName(),
				"executeRunnable");
		Weaver.callOriginal();
	}

	@Trace
	public <T> T executeSupplier(Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", getName(),
				"executeSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public <T> Either<Exception, T> executeEitherSupplier(Supplier<Either<? extends Exception, T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", getName(),
				"executeEitherSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public void onComplete() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", getName(),
				"onComplete");
		Weaver.callOriginal();
	}

	@Trace
	public void changeConfig(BulkheadConfig newConfig) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", getName(),
				"changeConfig");
		Weaver.callOriginal();
	}

	@Trace
	public BulkheadConfig getBulkheadConfig() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", getName(),
				"getBulkheadConfig");
		return Weaver.callOriginal();
	}

	// Static methods
	@Trace
	static <T> CheckedSupplier<T> decorateCheckedSupplier(Bulkhead bulkhead, CheckedSupplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateCheckedSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Supplier<CompletionStage<T>> decorateCompletionStage(Bulkhead bulkhead,
			Supplier<CompletionStage<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateCompletionStage");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Supplier<Future<T>> decorateFuture(Bulkhead bulkhead, Supplier<Future<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateFuture");
		return Weaver.callOriginal();
	}

	@Trace
	public static CheckedRunnable decorateCheckedRunnable(Bulkhead bulkhead, CheckedRunnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateCheckedRunnable");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Callable<T> decorateCallable(Bulkhead bulkhead, Callable<T> callable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateCallable");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Supplier<T> decorateSupplier(Bulkhead bulkhead, Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Supplier<Either<Exception, T>> decorateEitherSupplier(Bulkhead bulkhead,
			Supplier<Either<? extends Exception, T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateEitherSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Consumer<T> decorateConsumer(Bulkhead bulkhead, Consumer<T> consumer) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateConsumer");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> CheckedConsumer<T> decorateCheckedConsumer(Bulkhead bulkhead, CheckedConsumer<T> consumer) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateCheckedConsumer");
		return Weaver.callOriginal();
	}

	@Trace
	public static Runnable decorateRunnable(Bulkhead bulkhead, Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateRunnable");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T, R> Function<T, R> decorateFunction(Bulkhead bulkhead, Function<T, R> function) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateFunction");
		return Weaver.callOriginal();
	}

	@Trace
	static <T, R> CheckedFunction<T, R> decorateCheckedFunction(Bulkhead bulkhead, CheckedFunction<T, R> function) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Bulkhead", bulkhead.getName(),
				"decorateCheckedFunction");
		return Weaver.callOriginal();
	}

}