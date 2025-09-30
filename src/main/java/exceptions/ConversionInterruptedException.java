package exceptions;

import core.service.Service;

public class ConversionInterruptedException extends RuntimeException {
    public ConversionInterruptedException(String message) {
        super(message);
        System.out.println(message);
        Service.ExitWrapper.exit(1);
    }
}
