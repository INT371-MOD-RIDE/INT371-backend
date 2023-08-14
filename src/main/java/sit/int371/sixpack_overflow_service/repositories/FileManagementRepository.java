package sit.int371.sixpack_overflow_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int371.sixpack_overflow_service.entities.FilesManagement;

public interface FileManagementRepository extends JpaRepository <FilesManagement,Integer> {
}
