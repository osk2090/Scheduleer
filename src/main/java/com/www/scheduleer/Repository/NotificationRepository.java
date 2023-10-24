package com.www.scheduleer.Repository;

import com.www.scheduleer.domain.Notification;
import com.www.scheduleer.domain.enums.YN;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByReceiver_IdAndIsRead(Long id, YN yn);

    Optional<Notification> findById(Long id);
}
