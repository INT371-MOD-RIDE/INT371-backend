package sit.int371.modride_service.repositories;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import sit.int371.modride_service.beans.BranchesBean;
import sit.int371.modride_service.beans.FacultiesBean;
import sit.int371.modride_service.beans.UsersBean;

@Mapper
public interface UsersRepository {
        @Select("SELECT u.user_id,u.branch_id,u.email,u.firstname,u.lastname FROM users u")
        public List<UsersBean> getAllUsers() throws Exception;

        @Select({
                " select u.user_id,u.email,u.firstname,u.lastname,concat(u.firstname, ' ', u.lastname) as fullname,u.tel,u.profile_img_path,  ",
                " f.faculty_name,b.branch_name from users u  ",
                " inner join branches b on u.branch_id = b.branch_id ",
                " inner join faculties f on b.faculty_id = f.faculty_id  ",
                " where u.user_id = #{user_id} ",
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
                " select faculty_id,faculty_name from faculties; "
        })
        public List<FacultiesBean> getFaculties() throws Exception;

        @Select({
                " select branch_id,faculty_id,branch_name from branches "
        })
        public List<BranchesBean> getBranches() throws Exception;

        // sign-up users account
        @Insert({
                " insert into users(branch_id,email,firstname,lastname,tel,profile_img_path) ",
                " values(#{branch_id},#{email},#{firstname},#{lastname},#{tel},#{profile_img_path}) ",
        })
        @Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "user_id")
        public void createAccount(UsersBean bean) throws Exception;

        // add role of user from create-account
        @Insert({
                " insert into user_role(user_id,role_id) ",
                " values(#{user_id},#{role_id}) ",
        })
        public void addRoleForUser(HashMap<String, Object> params) throws Exception;
}
