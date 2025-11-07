package com.newrelic.instrumentation.labs.bulkhead;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

import java.util.concurrent.Callable;

public class NRCallableWrapper<T> implements Callable<T> {

    private final Callable<T> delegate;
    private Token token;

    public NRCallableWrapper(Callable<T> delegate, Token token) {
        this.delegate = delegate;
        this.token = token;
    }
    @Override
    @Trace(async=true)
    public T call() throws Exception {
        if(token != null) {
            token.linkAndExpire();
            token = null;
        }
        return delegate.call();
    }
}
