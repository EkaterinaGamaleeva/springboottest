package response;

public class OrderCreateException extends RuntimeException{
    public OrderCreateException(String message) {
        super(message);
    }
}
