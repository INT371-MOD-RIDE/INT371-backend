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

@Mapper
public interface FriendsRepository {

        // ---ใช้งานสำหรับ events-----------------------------------------

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
        // -- sub queries ชุดแรก user_id คือ id ของตัวเรา
        // -- sub queries ชุดที่สอง user_id ของ driver
        // -- สุดท้ายจะได้ออกมาเป็น mutual friend ของกันและกัน (หากมี)
        @Select({
                        " select u.user_id,u.email,u.firstname,u.lastname from users u ",
                        " inner join  ",
                        "         (select friend_id from friendships  ",
                        "     where user_id = #{user_id} and friend_status = 'accepted') as a on u.user_id = a.friend_id ",
                        " inner join  ",
                        "         (select friend_id from friendships  ",
                        "     where user_id = #{friend_id} and friend_status = 'accepted') as b on u.user_id = b.friend_id "
        })
        public List<MutualFriendBean> checkMutualFriend(FriendsBean bean) throws Exception;

        // --------------------------------------------
        @Select({
                        " select u.user_id,u.firstname,f.friend_id,f.friend_status ",
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
        public void createFriendship(HashMap<String, Object> params) throws Exception;

        // update friendships to "accepted"
        @Update({
                        " update friendships set friend_status = 'accepted' ",
                        " where (user_id = #{user_id} and friend_id = #{friend_id}) or (user_id = #{friend_id} and friend_id = #{user_id}) "
        })
        public void updateFriendship(HashMap<String, Object> params) throws Exception;

        // delete friendships when "rejected"
        @Delete({
                        " delete from friendships ",
                        " where (user_id = #{user_id} and friend_id = #{friend_id}) or (user_id = #{friend_id} and friend_id = #{user_id}) "
        })
        public void deleteFriendship(HashMap<String, Object> params) throws Exception;

}
