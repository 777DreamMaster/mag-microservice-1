package org.chumakov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public Notification sendNotification(Notification notification) {
        notification.setStatus("PENDING");
        Notification savedNotification = notificationRepository.save(notification);

        executorService.submit(() -> {
            try {
                Thread.sleep(2000);
                savedNotification.setStatus("SENT");
            } catch (InterruptedException e) {
                savedNotification.setStatus("FAILED");
            }
            log.info("sendNotification: {}, {}", savedNotification.getId(), savedNotification.getStatus());
            notificationRepository.save(savedNotification);
        });

        return savedNotification;
    }

    public Optional<Notification> getNotification(Long id) {
        log.info("getNotification {}", id);
        return notificationRepository.findById(id);
    }
}

