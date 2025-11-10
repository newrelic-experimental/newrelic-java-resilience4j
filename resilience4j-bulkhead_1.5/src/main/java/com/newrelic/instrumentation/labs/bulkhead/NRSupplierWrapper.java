package com.newrelic.instrumentation.labs.bulkhead;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

public class NRSupplierWrapper<T> implements Supplier<CompletionStage<T>> {

    private final Supplier<CompletionStage<T>> supplier;
    private final NRHolder holder;

    public NRSupplierWrapper(Supplier<CompletionStage<T>> supplier, NRHolder holder) {
        this.supplier = supplier;
        this.holder = holder;
    }

    @Override
    public CompletionStage<T> get() {
        CompletionStage<T> future = supplier.get();
        if(future instanceof CompletableFuture) {
            NRBiConsumer<T> consumer = new NRBiConsumer<T>(holder);
            return ((CompletableFuture<T>)future).whenComplete(consumer);
        }
        return future;
    }
}
