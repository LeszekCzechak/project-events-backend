package pl.sdacademy.projecteventsbackend.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> findByName(String name);

    Optional<List<EventEntity>> findAllByAddress_City(String city);

    Optional<List<EventEntity>> findAllByAddressContains(String name);

    @Query(value = "SELECT\n" +
            "    *\n" +
            "FROM\n" +
            "    event_entity ee\n" +
            "    INNER JOIN\n" +
            "    address_entity ae on ee.address_id = ae.id\n" +
            "WHERE\n" +
            "    ST_Distance_Sphere(\n" +
            "            point(ae.lat, ae.lng),\n" +
            "            point(:lat, :lng)\n" +
            "        ) < :distance",
            nativeQuery = true
    )
    List<EventEntity> findAllInDistance(@Param("lat") double lat, @Param("lng") double lng, @Param("distance") int distance);
}
