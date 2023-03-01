package payment.system.controlers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment.system.services.PaymentService;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentsControler {

    private final PaymentService paymentService;

    public PaymentsControler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{name}/{surname}/{sum}")
    public UUID pay(@PathVariable String name, @PathVariable String surname, @PathVariable int sum) {
        return paymentService.createPayment(name, surname, sum);
    }
}
