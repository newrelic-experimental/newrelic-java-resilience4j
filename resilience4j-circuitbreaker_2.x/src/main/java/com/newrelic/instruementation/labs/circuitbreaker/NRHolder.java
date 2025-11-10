package com.newrelic.instruementation.labs.circuitbreaker;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;

public class NRHolder {

    private Segment segment;
    private String segmentName;

    public NRHolder(String name) {
        segmentName = name;
    }

    public void startSegment() {
        segment = NewRelic.getAgent().getTransaction().startSegment(segmentName);
    }

    public void endSegment() {
        if(segment != null) {
            segment.end();
        }
    }

    public void ignoreSegment() {
        if(segment != null) {
            segment.ignore();
        }
    }
}
