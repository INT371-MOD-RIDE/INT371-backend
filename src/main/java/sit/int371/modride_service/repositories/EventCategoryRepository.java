package sit.int371.modride_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int371.modride_service.entities.EventCategory;

public interface EventCategoryRepository extends JpaRepository<EventCategory,Integer> {

}
