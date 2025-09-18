package exceptions;

public class InitException extends RuntimeException {
    public InitException(String message) {
        super(message);
        System.out.println(message);
        System.exit(1);
    }
}
