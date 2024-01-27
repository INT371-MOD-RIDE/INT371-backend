package sit.int371.modride_service.repositories;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sit.int371.modride_service.beans.UsersBean;

@Mapper
public interface AdminRepository {
        @Select({
                        " select u.user_id, u.fullname, u.email, u.tel  ",
                        " , f.faculty_name, b.branch_name, u.other_contact ",
                        " , u.contact_info, r.role_id, r.role_name ",
                        " , case when r.role_name = 'passenger' then 'ผู้โดยสาร' ",
                        " when r.role_name = 'driver' then 'ผู้ขับขี่' ",
                        " else 'ผู้ดูแลระบบ' ",
                        " end as role_name_th ",
                        " from users u ",
                        " inner join branches b on b.branch_id = u.branch_id ",
                        " inner join faculties f on f.faculty_id = b.faculty_id ",
                        " inner join roles r on r.role_id = u.role_id ",
                        " order by f.faculty_name asc, b.branch_name asc, u.fullname asc ",
        })
        public List<UsersBean> getAllUsers() throws Exception;

        @Select({
                        " select u.user_id, u.fullname, u.email, u.tel  ",
                        " , f.faculty_id, f.faculty_name, b.branch_id, b.branch_name, u.other_contact ",
                        " , u.contact_info, r.role_id, r.role_name ",
                        " , case when r.role_name = 'passenger' then 'ผู้โดยสาร' ",
                        " when r.role_name = 'driver' then 'ผู้ขับขี่' ",
                        " else 'ผู้ดูแลระบบ' ",
                        " end as role_name_th ",
                        " from users u ",
                        " inner join branches b on b.branch_id = u.branch_id ",
                        " inner join faculties f on f.faculty_id = b.faculty_id ",
                        " inner join roles r on r.role_id = u.role_id ",
                        " where u.user_id = #{user_id} ",
        })
        public UsersBean getUserDetail(Integer userId) throws Exception;

        @Select({
                        " select u.user_id,r.role_id,r.role_name,u.email,u.fullname,COALESCE(u.tel, '') AS tel,  ",
                        " f.faculty_name,b.branch_name from users u  ",
                        " inner join branches b on u.branch_id = b.branch_id ",
                        " inner join faculties f on b.faculty_id = f.faculty_id ",
                        " inner join roles r on u.role_id = r.role_id ",
                        " where u.fullname = #{fullname} and u.password = #{password}  ",
        })
        public UsersBean getAdminUser(HashMap<String, String> param) throws Exception;

        // update user by admin
        @Update({
                        " update users ",
                        " set branch_id = #{branch_id} ",
                        " where user_id = #{user_id} ",
        })
        public void updateUserByAdmin(UsersBean bean) throws Exception;

        // delete-user-process (repo มันเยอะ เพราะต้องแยก delete)
        // ---------------------------------------------------------------------------
        @Delete({
                        " DELETE FROM ratings WHERE user_id = #{user_id}; ",
        })
        public void deleteRatingOfUserId(Integer user_id) throws Exception;

        @Delete({
                        " DELETE FROM members WHERE user_id = #{user_id}; ",
        })
        public void deleteMemberOfUserId(Integer user_id) throws Exception;

        @Delete({
                        " DELETE FROM events WHERE user_id = #{user_id}; ",
        })
        public void deleteEventsOfUserId(Integer user_id) throws Exception;

        @Delete({
                        " DELETE FROM friendships WHERE user_id = #{user_id} or friend_id = #{user_id}; ",
        })
        public void deleteFriendshipsOfUserId(Integer user_id) throws Exception;

        @Delete({
                        " DELETE FROM vehicles WHERE user_id = #{user_id}; ",
        })
        public void deleteVehiclesOfUserId(Integer user_id) throws Exception;

        @Delete({
                        " DELETE FROM user_report WHERE report_to = #{user_id} or reporter_id = #{user_id} "
        })
        public void deleteUserReport(Integer user_id) throws Exception;

        @Delete({
                        " DELETE FROM users WHERE user_id = #{user_id}; ",
        })
        public void deleteUser(Integer user_id) throws Exception;
        // ---------------------------------------------------------------------------

}
