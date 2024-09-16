package io.github.resilience4j.retry;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;
import java.util.function.Supplier;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.vavr.CheckedFunction0;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedRunnable;
import io.vavr.control.Either;
import io.vavr.control.Try;

@Weave(type = MatchType.Interface)
public abstract class Retry {

	public abstract String getName();

	@Trace(dispatcher = true)
	public <T> T executeCallable(Callable<T> callable) throws Exception {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", getName(),
				"executeCallable");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> T executeCheckedSupplier(CheckedFunction0<T> checkedSupplier) throws Throwable {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", getName(),
				"executeCheckedSupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> CompletionStage<T> executeCompletionStage(ScheduledExecutorService scheduler,
			Supplier<CompletionStage<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", getName(),
				"executeCompletionStage");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void executeRunnable(Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", getName(),
				"executeRunnable");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> T executeSupplier(Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", getName(),
				"executeSupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> Try<T> executeTrySupplier(Supplier<Try<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", getName(),
				"executeTrySupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T> Either<Exception, T> executeEitherSupplier(Supplier<Either<Exception, T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", getName(),
				"executeEitherSupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public RetryConfig getRetryConfig() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", getName(),
				"getRetryConfig");
		return Weaver.callOriginal();
	}

	// Static methods
	@Trace(dispatcher = true)
	public static <T> CheckedFunction0<T> decorateCheckedSupplier(Retry retry, CheckedFunction0<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", retry.getName(),
				"decorateCheckedSupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static <T> Supplier<CompletionStage<T>> decorateCompletionStage(Retry retry,
			ScheduledExecutorService scheduler, Supplier<CompletionStage<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", retry.getName(),
				"decorateCompletionStage");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static CheckedRunnable decorateCheckedRunnable(Retry retry, CheckedRunnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", retry.getName(),
				"decorateCheckedRunnable");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static <T, R> CheckedFunction1<T, R> decorateCheckedFunction(Retry retry, CheckedFunction1<T, R> function) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", retry.getName(),
				"decorateCheckedFunction");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static <T> Supplier<T> decorateSupplier(Retry retry, Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", retry.getName(),
				"decorateSupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static <E extends Exception, T> Supplier<Either<E, T>> decorateEitherSupplier(Retry retry,
			Supplier<Either<E, T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", retry.getName(),
				"decorateEitherSupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static <T> Supplier<Try<T>> decorateTrySupplier(Retry retry, Supplier<Try<T>> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", retry.getName(),
				"decorateTrySupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static <T> Callable<T> decorateCallable(Retry retry, Callable<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", retry.getName(),
				"decorateCallable");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static Runnable decorateRunnable(Retry retry, Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", retry.getName(),
				"decorateRunnable");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static <T, R> Function<T, R> decorateFunction(Retry retry, Function<T, R> function) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "Retry", retry.getName(),
				"decorateFunction");
		return Weaver.callOriginal();
	}

}