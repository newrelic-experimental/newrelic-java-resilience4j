package com.newrelic.instrumentation.labs.bulkhead;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

import java.util.concurrent.Callable;

public class Utils {

    public static <T> NRCallableWrapper<T> getWrapper(Callable<T> task) {
        if(task instanceof NRCallableWrapper) {
            return null;
        }
        Token token = NewRelic.getAgent().getTransaction().getToken();
        if(token != null) {
            if(token.isActive()) {
                return new NRCallableWrapper<>(task, token);
            }
        }
        return null;
    }

    public static NRRunnableWrapper getWrapper(Runnable task) {
        if(task instanceof NRRunnableWrapper) {
            return null;
        }
        Token token = NewRelic.getAgent().getTransaction().getToken();
        if(token != null) {
            if(token.isActive()) {
                return new NRRunnableWrapper(task, token);
            }
        }
        return null;
    }
}
