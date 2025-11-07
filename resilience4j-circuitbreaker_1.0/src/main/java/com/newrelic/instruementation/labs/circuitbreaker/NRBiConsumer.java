package com.newrelic.instruementation.labs.circuitbreaker;

import com.newrelic.api.agent.NewRelic;

import java.util.function.BiConsumer;

public class NRBiConsumer<T> implements BiConsumer<T, Throwable> {

    private NRHolder holder;

    public NRBiConsumer(NRHolder holder) {
        this.holder = holder;
    }

    @Override
    public void accept(T t, Throwable throwable) {
        if(throwable != null) {
            NewRelic.noticeError(throwable);
        }
        if(holder != null) {
            holder.endSegment();
        }
    }
}
