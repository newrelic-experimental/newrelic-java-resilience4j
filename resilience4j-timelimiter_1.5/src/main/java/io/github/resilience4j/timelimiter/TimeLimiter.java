package io.github.resilience4j.timelimiter;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.Interface)
public abstract class TimeLimiter {

	public abstract String getName();

	@Trace(dispatcher = true)
	public <T, F extends Future<T>> T executeFutureSupplier(Supplier<F> futureSupplier) throws Exception {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "TimeLimiter", getName(),
				"executeFutureSupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T, F extends CompletionStage<T>> CompletionStage<T> executeCompletionStage(
			ScheduledExecutorService scheduler, Supplier<F> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "TimeLimiter", getName(),
				"executeCompletionStage");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T, F extends Future<T>> Callable<T> decorateFutureSupplier(Supplier<F> futureSupplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "TimeLimiter", getName(),
				"decorateFutureSupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public <T, F extends CompletionStage<T>> Supplier<CompletionStage<T>> decorateCompletionStage(
			ScheduledExecutorService scheduler, Supplier<F> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "TimeLimiter", getName(),
				"decorateCompletionStage");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void onSuccess() {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "TimeLimiter", getName(),
				"onSuccess");
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void onError(Throwable throwable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "TimeLimiter", getName(),
				"onError");
		NewRelic.noticeError(throwable);
		Weaver.callOriginal();
	}

	// Static methods

	@Trace(dispatcher = true)
	public static <T, F extends Future<T>> Callable<T> decorateFutureSupplier(TimeLimiter timeLimiter,
			Supplier<F> futureSupplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "TimeLimiter",
				timeLimiter.getName(), "decorateFutureSupplier");
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static <T, F extends CompletionStage<T>> Supplier<CompletionStage<T>> decorateCompletionStage(
			TimeLimiter timeLimiter, ScheduledExecutorService scheduler, Supplier<F> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "TimeLimiter",
				timeLimiter.getName(), "decorateCompletionStage");
		return Weaver.callOriginal();
	}

}