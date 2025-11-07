package com.newrelic.instrumentation.labs.bulkhead;

import java.util.function.Consumer;

public class NRResultConsumer<T> implements Consumer<T> {

    private final NRHolder holder;

    public NRResultConsumer(NRHolder holder) {
        this.holder = holder;
    }

    @Override
    public void accept(T t) {
        if(holder != null) {
            holder.endSegment();
        }
    }
}
