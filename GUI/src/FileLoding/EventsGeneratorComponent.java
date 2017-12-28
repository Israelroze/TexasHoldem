package FileLoding;

import FileLoding.EventObject;
import FileLoding.EventsHandler;

import java.util.ArrayList;
import java.util.List;

public class EventsGeneratorComponent {

    private List<EventsHandler> handlers;

    public EventsGeneratorComponent() {
        handlers = new ArrayList<>();
    }

    public void addHandler (EventsHandler handler) {
        if (handler != null && !handlers.contains(handler)) {
            handlers.add(handler);
        }
    }

    public void removeHandler(EventsHandler handler) {
        handlers.remove(handler);
    }

    private void fireEvent (String message) {
        EventObject myEvent = new EventObject(this, message);
        List<EventsHandler> handlersToInvoke = new ArrayList<>(handlers);
        for (EventsHandler handler : handlers) {
            handler.eventHappened(myEvent);
        }
    }


}