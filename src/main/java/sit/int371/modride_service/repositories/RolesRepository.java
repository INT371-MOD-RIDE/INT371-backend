package sit.int371.modride_service.repositories;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sit.int371.modride_service.beans.RolesBean;
import sit.int371.modride_service.entities.User;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Mapper
public interface RolesRepository{

    @Select(
            "SELECT * FROM roles"
    )
    public List<RolesBean> getAllRoles() throws Exception;
    




}
