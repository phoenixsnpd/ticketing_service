package payment.system.util;

public class PaymentNotCreatedException extends RuntimeException {
    public PaymentNotCreatedException(String msg) {
        super(msg);
    }
}
