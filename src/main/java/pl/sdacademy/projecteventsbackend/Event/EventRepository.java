package pl.sdacademy.projecteventsbackend.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> findByName(String name);
}
