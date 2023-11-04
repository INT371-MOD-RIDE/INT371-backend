package sit.int371.modride_service.repositories;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sit.int371.modride_service.beans.UsersBean;
import sit.int371.modride_service.entities.User;

import java.util.List;

@Mapper
public interface OldUserRepository extends JpaRepository<User, Integer> {

    // ของเก่า
    // แต่เก็บไว้ก่อน***************************************************************************************
    @Query(value = "SELECT * FROM user where email = :e", nativeQuery = true)
    User findByEmail(@Param("e") String email);

}
