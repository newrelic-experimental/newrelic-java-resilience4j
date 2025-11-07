package com.newrelic.instrumentation.labs.bulkhead;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRRunnableWrapper implements Runnable {

    private final Runnable delegate;
    private Token token;
    public NRRunnableWrapper(Runnable delegate, Token token) {
        this.delegate = delegate;
        this.token = token;
    }

    @Override
    @Trace(async=true)
    public void run() {
        if(token != null) {
            token.linkAndExpire();
            token = null;
        }
        if (this.delegate != null) {
            this.delegate.run();
        }
    }
}
