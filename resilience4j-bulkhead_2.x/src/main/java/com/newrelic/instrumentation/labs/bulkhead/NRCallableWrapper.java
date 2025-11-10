package com.newrelic.instrumentation.labs.bulkhead;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

import java.util.concurrent.Callable;

public class NRCallableWrapper<T> implements Callable<T> {

    private static boolean isTransformed = false;
    private final Callable<T> delegate;
    private Token token;

    public NRCallableWrapper(Callable<T> delegate, Token token) {
        this.delegate = delegate;
        this.token = token;
        if(!isTransformed) {
            isTransformed = true;
            AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
        }
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
