package sit.int371.modride_service.repositories;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sit.int371.modride_service.beans.FriendsBean;
import sit.int371.modride_service.beans.MutualFriendBean;
import sit.int371.modride_service.beans.UsersBean;

@Mapper
public interface FriendsRepository {

        // หน้าการแนะนำเพื่อน -- query clear ✅
        @Select({
                        " WITH FriendListSuggestion AS ( ",
                        "         SELECT ",
                        "             u.user_id,f.faculty_id,u.email,u.fullname,u.tel, ",
                        "             f.faculty_name,b.branch_name,fs.user_id AS my_id, ",
                        "             fs.friend_id,fs.friend_status,uf.profile_img_name,uf.download_url, ",
                        "             CASE  ",
                        "                 WHEN EXISTS ( ",
                        "                     SELECT 1 ",
                        "                     FROM friendships ",
                        "                     WHERE user_id = u.user_id ",
                        "                       AND friend_id = #{user_id} ",
                        "                       AND friend_status = 'pending' ",
                        "                 ) THEN 1 ",
                        "                 ELSE 0 ",
                        "             END AS check_friend ",
                        "         FROM ",
                        "             users u ",
                        "         INNER JOIN branches b ON u.branch_id = b.branch_id ",
                        "         INNER JOIN faculties f ON b.faculty_id = f.faculty_id ",
                        "         LEFT JOIN friendships fs ON (u.user_id = fs.friend_id AND fs.user_id = #{user_id}) ",
                        "         LEFT JOIN users_files uf ON uf.owner_id = u.user_id ",
                        "         WHERE u.user_id != #{user_id} AND fs.user_id IS NULL AND fs.friend_status IS NULL ",
                        "     ) ",
                        "     SELECT ",
                        "         user_id,faculty_id,email,fullname,tel,faculty_name,branch_name,my_id, ",
                        "         friend_id,friend_status,profile_img_name,download_url,check_friend ",
                        "     FROM FriendListSuggestion ",
                        "     WHERE check_friend = 0 ",
                        "     order by faculty_name = #{faculty_name} desc, faculty_name asc ",
                        "     ,branch_name = #{branch_name} desc, branch_name asc,fullname asc ",
        })
        public List<UsersBean> friendListSuggestionSearch(HashMap<String, Object> params) throws Exception;

        // หน้ารายการเพื่อน -- friend-list section ✅
        @Select({
                        " select u.user_id,f.faculty_id,u.email,u.fullname, ",
                        " u.tel,f.faculty_name,b.branch_name, ",
                        " fs.friend_id,fs.friend_status ",
                        " ,uf.profile_img_name,uf.download_url ",
                        " from users u  ",
                        " inner join branches b on u.branch_id = b.branch_id ",
                        " inner join faculties f on b.faculty_id = f.faculty_id ",
                        " inner join friendships fs on fs.user_id = u.user_id ",
                        " left join users_files uf on uf.owner_id = u.user_id ",
                        " where u.user_id != #{user_id} and fs.friend_id = #{user_id} and fs.friend_status = 'accepted' ",
                        " order by f.faculty_name = #{faculty_name} desc, f.faculty_name asc, u.fullname asc ",
        })
        public List<UsersBean> friendsList(HashMap<String, Object> params) throws Exception;

        // คำขอเป็นเพื่อน -- friend-request ✅
        @Select({
                        " select u.user_id,f.faculty_id,u.email,u.fullname, ",
                        " u.tel,f.faculty_name,b.branch_name, ",
                        " fs.friend_id,fs.friend_status ",
                        " ,uf.profile_img_name,uf.download_url ",
                        " from users u  ",
                        " inner join branches b on u.branch_id = b.branch_id ",
                        " inner join faculties f on b.faculty_id = f.faculty_id ",
                        " inner join friendships fs on fs.user_id = u.user_id ",
                        " left join users_files uf on uf.owner_id = u.user_id ",
                        " where u.user_id != #{user_id} and fs.friend_id = #{user_id} and fs.friend_status = 'pending' ",
                        " order by f.faculty_name = #{faculty_name} desc, f.faculty_name asc, u.fullname asc ",
        })
        public List<UsersBean> friendsRequest(HashMap<String, Object> params) throws Exception;

        // -- ✅ get list of friend (order by เป็นเพื่อนกับเรา first !!!)
        // -- #{user_id} คือ user ตั้งต้นของ other-profile
        // -- #{my_id} คือ ตัวเรา (join for เป็นเพื่อนกับเรา: หาก my_id แสดง = user
        // คนนั้น
        // เป็นเพื่อนกับเรา)
        // -- เรียงเราก่อน (หากเป็นเพื่อนกัน) > คนที่เขาเป็นเพื่อนกับเรา > คนที่เขามี
        // mutual friend กับเรา (จำนวนมากที่สุด มาก่อน)
        // -- ปล. ในที่นี้ #{my_id} คือ id สมมติว่ามันคือตัวเรา | id #{user_id} คือ user
        // ที่เป็น
        // otherProfile
        @Select({

                        " SELECT u.user_id, u.email, u.fullname, f.faculty_name, b.branch_name,  ",
                        " uf.download_url, f2.user_id as my_id ",
                        " , (SELECT count(users.user_id) ",
                        "         FROM users  ",
                        "         INNER JOIN friendships f1 ON users.user_id = f1.friend_id ",
                        "         INNER JOIN friendships f2 ON users.user_id = f2.friend_id ",
                        "         WHERE (f1.user_id = #{my_id} AND f1.friend_status = 'accepted') ",
                        "         AND (f2.user_id = u.user_id AND f2.friend_status = 'accepted') ",
                        "     and u.user_id != #{my_id} ",
                        "     ) as count_mutual ",
                        " , case  ",
                        "         when exists( ",
                        "                 select * from friendships ",
                        "                 where user_id = #{my_id} and friend_id = u.user_id ",
                        "                 and friend_status = 'accepted' ",
                        " ) then 2 ",
                        " when exists( ",
                        "                 select * from friendships ",
                        "                 where user_id = #{my_id} and friend_id = u.user_id ",
                        "                 and friend_status = 'pending' ",
                        " ) then 1 ",
                        " else 0 ",
                        "         end as check_friend ",
                        " FROM users u ",
                        " inner join branches b on u.branch_id = b.branch_id ",
                        " inner join faculties f on b.faculty_id = f.faculty_id ",
                        " left join users_files uf on uf.owner_id = u.user_id ",
                        " INNER JOIN friendships f1 ON u.user_id = f1.friend_id AND f1.friend_status = 'accepted' ",
                        " LEFT JOIN friendships f2 ON u.user_id = f2.friend_id AND f2.user_id = #{my_id} and f2.friend_status = 'accepted' ",
                        " WHERE f1.user_id = #{user_id} ",
                        " ORDER BY u.user_id = #{my_id} desc ",
                        " , my_id desc ",
                        " , count_mutual desc ",
                        " ,(f2.user_id IS NOT NULL) DESC ",
                        " , b.branch_name = 'สาขาวิชาเทคโนโลยีสารสนเทศ' desc ",
                        " , u.fullname asc ",
        })
        public List<UsersBean> otherUserFriendList(UsersBean usersBean) throws Exception;

        // friendships-in-event
        // -- check ว่า driver เป็นเพื่อนกับเราหรือไม่
        // -- user_id: ตัวเรา, friend_id: id ของ driver
        @Select({
                        " select user_id,friend_id,friend_status from friendships ",
                        " where user_id = #{user_id} and friend_id = #{friend_id} ",
                        " and friend_status = 'accepted' ",
        })
        public List<FriendsBean> checkFriendshipForEvent(FriendsBean bean) throws Exception;

        // เมื่อ driver ไม่ได้เป็นเพื่อนกับเรา query นี้จะถูกเรียกใช้งาน
        // ✅ query ใหม่ สำหรับ mutual friend only
        @Select({
                        " SELECT u.user_id, u.email, u.fullname ",
                        " ,uf.profile_img_name,uf.download_url ",
                        " FROM users u ",
                        " INNER JOIN friendships f1 ON u.user_id = f1.friend_id ",
                        " INNER JOIN friendships f2 ON u.user_id = f2.friend_id ",
                        " left join users_files uf on uf.owner_id = u.user_id ",
                        " WHERE (f1.user_id = #{user_id} AND f1.friend_status = 'accepted') ",
                        " AND (f2.user_id = #{friend_id} AND f2.friend_status = 'accepted') ",
        })
        public List<MutualFriendBean> getMutualFriend(FriendsBean bean) throws Exception;

        // --------------------------------------------
        @Select({
                        " select u.user_id,u.fullname,f.friend_id,f.friend_status ",
                        " from friendships f inner join users u on f.user_id = u.user_id ",
                        " where u.user_id = #{user_id} ",
                        " order by u.user_id "
        })
        public List<FriendsBean> getFriendshipByUserId(FriendsBean bean) throws Exception;

        // เมื่อทำการขอเป็นเพื่อนจะ insert friendship
        // เช่น will ขอ sam จะ insert เกิด cases ดังนี้...
        // will(user_id)-sam(friend_id)
        @Insert({
                        " insert into friendships(user_id,friend_id,friend_status) ",
                        " values(#{user_id},#{friend_id},#{friend_status}) "
        })
        public void createFriendship(FriendsBean bean) throws Exception;

        // update friendships to "accepted"
        @Update({
                        " update friendships set friend_status = 'accepted' ",
                        " where (user_id = #{friend_id} and friend_id = #{user_id}) "
        })
        public void updateFriendship(FriendsBean bean) throws Exception;

        // delete friendships when "rejected"
        @Delete({
                        " delete from friendships ",
                        " where user_id = #{user_id} and friend_id = #{friend_id} "
        })
        public void cancelFriend(FriendsBean bean) throws Exception;

        // --------queries for exception----------------------
        @Select({
                        " select * from friendships ",
                        " where user_id = #{user_id} and friend_id = #{friend_id} ",
        })
        public List<FriendsBean> checkBeforeCreateFS(FriendsBean bean) throws Exception;

        // -- exception เมื่อ ฝั่ง A ยกเลิกคำขอ แต่่ ฝั่ง B ยังเห็น req ของ A (ยังไม่
        // refresh)
        // -- user_id คือ id ของผู้ที่ขอ
        // -- friend_id คือ id ของเราที่ถูกขอ
        @Select({
                        " select * from friendships ",
                        " where user_id = #{friend_id} and friend_id = #{user_id} ",
        })
        public List<FriendsBean> checkBeforeAccept(FriendsBean bean) throws Exception;

        @Select({
                        " select * from friendships ",
                        " where user_id = #{user_id} and friend_id = #{friend_id} and friend_status = 'accepted' ",
        })
        public List<FriendsBean> checkBeforeCancel(FriendsBean bean) throws Exception;

}
