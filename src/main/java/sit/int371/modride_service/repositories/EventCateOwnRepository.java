package sit.int371.modride_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import sit.int371.modride_service.entities.Event;
import sit.int371.modride_service.entities.EventCategoryOwner;

import java.util.List;

public interface EventCateOwnRepository extends JpaRepository<EventCategoryOwner, Integer> {
    @Query
            (
                    value = "SELECT * FROM event_category_owner eo where eo.userId = :userId", nativeQuery = true)
    List<EventCategoryOwner> findByUserid(@Param("userId") int userId);

//    @Query(
//            value = "SELECT * FROM event e where e.eventCategoryId= :c ORDER BY eventStartTime DESC ", nativeQuery = true
//    )
//    List<Event> getByEventCategory(@Param("c") Integer eventCategoryId);
}
