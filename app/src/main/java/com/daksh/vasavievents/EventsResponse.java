package com.daksh.vasavievents;

import java.util.List;

public class EventsResponse {
    private Embedded _embedded;

    public Embedded getEmbedded() {
        return _embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this._embedded = embedded;
    }

    public class Embedded {
        private List<Event> events;

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }
    }
}
