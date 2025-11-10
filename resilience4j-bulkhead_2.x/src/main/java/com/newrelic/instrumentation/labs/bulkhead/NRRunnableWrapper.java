package com.newrelic.instrumentation.labs.bulkhead;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRRunnableWrapper implements Runnable {

    private static boolean isTransformed = false;

    private final Runnable delegate;
    private Token token;

    public NRRunnableWrapper(Runnable delegate, Token token) {
        this.delegate = delegate;
        this.token = token;
        if(!isTransformed) {
            AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
            isTransformed = true;
        }
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
