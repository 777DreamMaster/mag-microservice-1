package org.chumakov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private final Random random = new Random();

    public Payment processPayment(Payment payment) {
        List<String> statuses = List.of("COMPLETED", "FAILED", "PENDING");
        payment.setStatus(statuses.get(random.nextInt(statuses.size())));
        log.info("processPayment: {}, {}", payment.getId(), payment.getStatus());
        return paymentRepository.save(payment);
    }

    public Optional<Payment> getPayment(Long id) {
        log.info("getPayment {}", id);
        return paymentRepository.findById(id);
    }

    public List<Payment> getAllPayments() {
        log.info("getAllPayments");
        return paymentRepository.findAll();
    }
}

