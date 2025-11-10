package com.newrelic.instrumentation.labs.bulkhead;

import com.newrelic.api.agent.NewRelic;

import java.util.function.Consumer;

public class NRErrorConsumer  implements Consumer<Throwable> {

    private NRHolder holder;

    public NRErrorConsumer(NRHolder holder) {
        this.holder = holder;
    }

    @Override
    public void accept(Throwable throwable) {
        if(throwable != null) {
            NewRelic.noticeError(throwable);
        }
        if(holder != null) {
            holder.endSegment();
        }
    }
}
