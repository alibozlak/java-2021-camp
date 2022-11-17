package bozlak.java2021.core.utilities.results;

public class DataResult<T> extends Result {

    private T data;

    public DataResult(boolean success, String message, T data) {
        super(success, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }
    
}
