package io.github.resilience4j.ratelimiter;

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
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.ratelimiter.RateLimiterMetricsCollector;

import io.github.resilience4j.core.functions.CheckedFunction;
import io.github.resilience4j.core.functions.CheckedRunnable;
import io.github.resilience4j.core.functions.CheckedSupplier;

@Weave(type = MatchType.Interface)
public abstract class RateLimiter {

	public abstract String getName();

	public abstract Metrics getMetrics();

	@WeaveAllConstructors
	public RateLimiter() {
		RateLimiterMetricsCollector.addRateLimiter(this);
	}

	@Trace
	public <T> T executeCallable(Callable<T> callable) throws Exception {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter", getName(),
				"executeCallable");
		return Weaver.callOriginal();
	}

	@Trace
	public <T> T executeCheckedSupplier(CheckedSupplier<T> checkedSupplier) throws Throwable {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter", getName(),
				"executeCheckedSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public <T> CompletionStage<T> executeCompletionStage(Supplier<CompletionStage<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter", getName(),
				"executeCompletionStage");
		return Weaver.callOriginal();
	}

	@Trace
	public void executeRunnable(Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter", getName(),
				"executeRunnable");
		Weaver.callOriginal();
	}

	@Trace
	public <T> T executeSupplier(Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter", getName(),
				"executeSupplier");
		return Weaver.callOriginal();
	}

	// Static methods
	@Trace
	public static <T> CheckedSupplier<T> decorateCheckedSupplier(RateLimiter rateLimiter, CheckedSupplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateCheckedSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Supplier<CompletionStage<T>> decorateCompletionStage(RateLimiter rateLimiter,
			Supplier<CompletionStage<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateCompletionStage");
		return Weaver.callOriginal();
	}

	@Trace
	public static CheckedRunnable decorateCheckedRunnable(RateLimiter rateLimiter, CheckedRunnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateCheckedRunnable");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Callable<T> decorateCallable(RateLimiter rateLimiter, Callable<T> callable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateCallable");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Supplier<T> decorateSupplier(RateLimiter rateLimiter, Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Consumer<T> decorateConsumer(RateLimiter rateLimiter, Consumer<T> consumer) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateConsumer");
		return Weaver.callOriginal();
	}

	@Trace
	public static Runnable decorateRunnable(RateLimiter rateLimiter, Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateRunnable");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T, R> Function<T, R> decorateFunction(RateLimiter rateLimiter, Function<T, R> function) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateFunction");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T, R> CheckedFunction<T, R> decorateCheckedFunction(RateLimiter rateLimiter,
			CheckedFunction<T, R> function) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateCheckedFunction");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T, F extends Future<T>> Supplier<F> decorateFuture(RateLimiter rateLimiter,
			Supplier<? extends F> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateFuture");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T, F extends Future<T>> Supplier<F> decorateFuture(RateLimiter rateLimiter, int permits,
			Supplier<? extends F> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateFuture");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T, R> Function<T, R> decorateFunction(RateLimiter rateLimiter, int permits,
			Function<T, R> function) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateFunction");
		return Weaver.callOriginal();
	}

	@Trace
	public static Runnable decorateRunnable(RateLimiter rateLimiter, int permits, Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateRunnable");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> CheckedSupplier<T> decorateCheckedSupplier(RateLimiter rateLimiter, int permits,
			CheckedSupplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateCheckedSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public static CheckedRunnable decorateCheckedRunnable(RateLimiter rateLimiter, int permits,
			CheckedRunnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateCheckedRunnable");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T, R> CheckedFunction<T, R> decorateCheckedFunction(RateLimiter rateLimiter, int permits,
			CheckedFunction<T, R> function) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateCheckedFunction");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T, R> CheckedFunction<T, R> decorateCheckedFunction(RateLimiter rateLimiter,
			Function<T, Integer> permitsCalculator, CheckedFunction<T, R> function) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateCheckedFunction");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Supplier<T> decorateSupplier(RateLimiter rateLimiter, int permits, Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Consumer<T> decorateConsumer(RateLimiter rateLimiter, int permits, Consumer<T> consumer) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateConsumer");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T, R> Function<T, R> decorateFunction(RateLimiter rateLimiter,
			Function<T, Integer> permitsCalculator, Function<T, R> function) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateFunction");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Callable<T> decorateCallable(RateLimiter rateLimiter, int permits, Callable<T> callable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "RateLimiter",
				rateLimiter.getName(), "decorateCallable");
		return Weaver.callOriginal();
	}

	@Weave(type = MatchType.Interface)
	public static abstract class Metrics {

		public abstract int getNumberOfWaitingThreads();

		public abstract int getAvailablePermissions();
	}
}