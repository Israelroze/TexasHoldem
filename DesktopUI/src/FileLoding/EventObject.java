package FileLoding;

public class EventObject {

    private Object source;
    private String message;

    public EventObject(Object source, String message) {
        this.source = source;
        this.message = message;
    }

    public Object getSource() {
        return source;
    }

    public String getMessage() {
        return message;
    }
}