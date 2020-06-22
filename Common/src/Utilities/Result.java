package Utilities;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private State _state;
    private String _message;
    private T _object;

    public Result(State state, String message, T object) {
        _state = state;
        _message = message;
        _object = object;
    }

    public State getState() {
        return _state;
    }

    public void setState(State state) {
        _state = state;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public T getObject() {
        return _object;
    }

    public void setObject(T object) {
        _object = object;
    }

    public enum State {
        SUCCESSFULL,
        FAILED
    }
}