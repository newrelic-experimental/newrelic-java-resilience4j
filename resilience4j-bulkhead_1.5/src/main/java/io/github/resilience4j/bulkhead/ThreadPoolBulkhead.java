package io.github.resilience4j.bulkhead;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.labs.bulkhead.NRBiConsumer;
import com.newrelic.instrumentation.labs.bulkhead.NRCallableWrapper;
import com.newrelic.instrumentation.labs.bulkhead.NRHolder;
import com.newrelic.instrumentation.labs.bulkhead.NRRunnableWrapper;
import com.newrelic.instrumentation.labs.bulkhead.NRSupplierWrapper;
import com.newrelic.instrumentation.labs.bulkhead.ThreadPoolBulkheadMetricsCollector;
import com.newrelic.instrumentation.labs.bulkhead.Utils;

@Weave(type = MatchType.Interface)
public abstract class ThreadPoolBulkhead implements AutoCloseable {

	@WeaveAllConstructors
	public ThreadPoolBulkhead() {
		ThreadPoolBulkheadMetricsCollector.addThreadPoolBulkhead(this);
	}

	public abstract String getName();

	public abstract Metrics getMetrics();

	@Trace
	public <T> CompletionStage<T> submit(Callable<T> task) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(),"submit");
		NRCallableWrapper<T> wrapper = Utils.getWrapper(task);
		if(wrapper != null) {
			task = wrapper;
		}
		NRHolder holder = new NRHolder("ThreadPoolBulkhead/"+getName()+"/submitRunnable");
		holder.startSegment();

		CompletionStage<T> completionStage = Weaver.callOriginal();
		if(completionStage instanceof CompletableFuture) {
			CompletableFuture<T> future = (CompletableFuture<T>) completionStage;
			return future.whenComplete(new NRBiConsumer<>(holder));
		}
		holder.ignoreSegment();
		return completionStage;
	}

	@Trace
	public CompletionStage<Void> submit(Runnable task) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(), "submitRunnable");
		NRRunnableWrapper wrapper = Utils.getWrapper(task);
		if(wrapper != null) {
			task = wrapper;
		}
		NRHolder holder = new NRHolder("ThreadPoolBulkhead/submit");
		holder.startSegment();

		CompletionStage<Void> completionStage = Weaver.callOriginal();
		if(completionStage instanceof CompletableFuture) {
			CompletableFuture<Void> future = (CompletableFuture<Void>) completionStage;
			return future.whenComplete(new NRBiConsumer<>(holder));
		}
		holder.ignoreSegment();
		return completionStage;
	}

	@Trace
	public <T> Supplier<CompletionStage<T>> decorateSupplier(Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(), "decorateSupplier");
        return Weaver.callOriginal();
	}

	@Trace
	public <T> Supplier<CompletionStage<T>> decorateCallable(Callable<T> callable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(), "decorateCallable");
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
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(), "executeSupplier");
		NRHolder holder = new NRHolder("ThreadPoolBulkhead/executeSupplier");
		holder.startSegment();
		CompletionStage<T> returnValue =  Weaver.callOriginal();
		if(returnValue instanceof CompletableFuture) {
			CompletableFuture<T> future = (CompletableFuture<T>) returnValue;
			return future.whenComplete(new NRBiConsumer<>(holder));
		}
		holder.ignoreSegment();
		return returnValue;
	}

	@Trace
	public <T> CompletionStage<T> executeCallable(Callable<T> callable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(), "executeCallable");
		NRCallableWrapper<T> wrapper = Utils.getWrapper(callable);
		if(wrapper != null) {
			callable = wrapper;
		}
		NRHolder holder = new NRHolder("ThreadPoolBulkhead/executeCallable");
		holder.startSegment();
		CompletionStage<T> returnValue = Weaver.callOriginal();
		if((returnValue instanceof CompletableFuture) && holder != null) {
			CompletableFuture<T> future = (CompletableFuture<T>) returnValue;
			NRBiConsumer<T> consumer = new NRBiConsumer<T>(holder);
			return future.whenComplete(consumer);
		}
		holder.ignoreSegment();
		return returnValue;
	}

	@Trace
	public CompletionStage<Void> executeRunnable(Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", getName(), "executeRunnable");
		NRRunnableWrapper wrapper = Utils.getWrapper(runnable);
		if(wrapper != null) {
			runnable = wrapper;
		}
		NRHolder holder = new NRHolder("ThreadPoolBulkhead/executeRunnable");
		holder.startSegment();
		CompletionStage<Void> returnValue = Weaver.callOriginal();
		if((returnValue instanceof CompletableFuture) && holder != null) {
			CompletableFuture<Void> future = (CompletableFuture<Void>) returnValue;
			NRBiConsumer<Void> consumer = new NRBiConsumer<Void>(holder);
			return future.whenComplete(consumer);
		}
		holder.ignoreSegment();
		return returnValue;
	}

	// Static methods
	@Trace
	public static <T> Supplier<CompletionStage<T>> decorateCallable(ThreadPoolBulkhead bulkhead, Callable<T> callable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", bulkhead.getName(), "decorateCallable");
		return Weaver.callOriginal();
	}

	@Trace
	public static <T> Supplier<CompletionStage<T>> decorateSupplier(ThreadPoolBulkhead bulkhead, Supplier<T> supplier) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead", bulkhead.getName(), "decorateSupplier");
		return Weaver.callOriginal();
	}

	@Trace
	public static Supplier<CompletionStage<Void>> decorateRunnable(ThreadPoolBulkhead bulkhead, Runnable runnable) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Resilience4j", "ThreadPoolBulkhead",bulkhead.getName(), "decorateRunnable");
		return Weaver.callOriginal();
	}

	@Weave(type = MatchType.Interface)
	public static abstract class Metrics {

		public abstract int getCoreThreadPoolSize();

		public abstract int getThreadPoolSize();

		public abstract int getMaximumThreadPoolSize();

		public abstract int getQueueDepth();

		public abstract int getRemainingQueueCapacity();

		public abstract int getQueueCapacity();

	}

}
