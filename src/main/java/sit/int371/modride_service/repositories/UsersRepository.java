package sit.int371.modride_service.repositories;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import sit.int371.modride_service.beans.FacultiesBean;
import sit.int371.modride_service.beans.UsersBean;

@Mapper
public interface UsersRepository {
        @Select("SELECT u.user_id,u.faculty_id,u.email,u.firstname,u.lastname FROM users u")
        public List<UsersBean> getAllUsers() throws Exception;

        @Select({
                        " select u.email,u.firstname,u.lastname,u.tel,u.profile_img_path, ",
                        " f.fac_name,f.branch from users u ",
                        " inner join faculties f on f.faculty_id = u.faculty_id ",
                        " where u.user_id = #{user_id} "
        })
        public HashMap<String, Object> getUserById(HashMap<String, Object> params) throws Exception;

        @Select({
                        " select r.role_name from users u ",
                        " inner join user_role ur on u.user_id = ur.user_id ",
                        " inner join roles r on ur.role_id = r.role_id ",
                        " where u.user_id = #{user_id} "
        })
        public List<String> getRolesById(HashMap<String, Object> params) throws Exception;
        
        @Select({
                " select faculty_id,fac_name,branch from faculties "
        })
        public List<FacultiesBean> getFaculties() throws Exception;

        // sign-up users account
        @Insert({
                " insert into users(faculty_id,email,firstname,lastname,tel,profile_img_path) ",
                " values(#{faculty_id},#{email},#{firstname},#{lastname},#{tel},#{profile_img_path}) ",
        })
        @Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "user_id")
        public void createAccount(UsersBean bean) throws Exception;
}
