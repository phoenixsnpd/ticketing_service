package payment.system.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import payment.system.PaymentStatus;

import java.util.UUID;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payment_identifiers")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "identifier")
    UUID identifier;

    @Column(name = "name")
    String name;

    @Column(name = "surname")
    String surname;

    @Column(name = "paymentSum")
    int paymentSum;

    @Column(name = "status")
    PaymentStatus status;

    public Payment(String name, String surname, int paymentSum) {
        identifier = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.paymentSum = paymentSum;
        status = PaymentStatus.getRandomStatus();
    }
    public Payment() {
    }
}
