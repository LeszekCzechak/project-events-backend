package pl.sdacademy.projecteventsbackend.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> findByName(String name);

    Optional<List<EventEntity>>findAllByAddressContains(String name);
}
