package sit.int371.modride_service.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.netty.http.server.HttpServerResponse;
import sit.int371.modride_service.beans.APIResponseBean;
import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.FriendsBean;
import sit.int371.modride_service.beans.MutualFriendBean;
import sit.int371.modride_service.beans.UsersBean;
import sit.int371.modride_service.repositories.FriendsRepository;
import sit.int371.modride_service.repositories.UsersRepository;

@RestController
@RequestMapping("/api/v1/friends")
public class FriendsController extends BaseController {

    @Autowired
    private FriendsRepository friendsRepository;

    private final String pendingStatus = "pending";
    private final String acceptedStatus = "accepted";

    // Get all-users (not your friend)
    @GetMapping("/suggestionSearch")
    public APIResponseBean getAllSocialNetwork(HttpServletRequest request,
            @RequestParam(name = "faculty_name", required = false) String faculty_name,
            @RequestParam(name = "branch_name", required = false) String branch_name,
            @RequestParam(name = "user_id", required = false) Integer user_id,
            @RequestParam(name = "search_friend", required = false) Boolean search_friend) {
        APIResponseBean res = new APIResponseBean();
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("faculty_name", faculty_name);
            params.put("branch_name", branch_name);
            params.put("user_id", user_id);
            List<UsersBean> friendListSuggest = friendsRepository.friendListSuggestionSearch(params);
            // if นี้จะทำงานเมื่อมีการ request จากหน้า "ค้นหาเพื่อน"
            // ⚠️ ไม่ต้อง loop มันทำให้การทำงานช้า
            if (search_friend) {
                for (UsersBean usersBean : friendListSuggest) {
                    FriendsBean friendsBean = new FriendsBean();
                    friendsBean.setUser_id(user_id);
                    friendsBean.setFriend_id(usersBean.getUser_id());
                    List<MutualFriendBean> mutualFriend = friendsRepository.getMutualFriend(friendsBean);
                    usersBean.setMutualFriend(mutualFriend);
                }
            }
            res.setData(friendListSuggest);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // Get all-friend-list
    @GetMapping("/list")
    public APIResponseBean getFriendsList(HttpServletRequest request,
            @RequestParam(name = "faculty_name", required = false) String faculty_name,
            // @RequestParam(name = "branch_name", required = false) String branch_name,
            @RequestParam(name = "user_id", required = false) Integer user_id) {
        APIResponseBean res = new APIResponseBean();
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("faculty_name", faculty_name);
            // params.put("branch_name", branch_name);
            params.put("user_id", user_id);
            List<UsersBean> friendList = friendsRepository.friendsList(params);
            for (UsersBean usersBean : friendList) {
                FriendsBean friendsBean = new FriendsBean();
                friendsBean.setUser_id(user_id);
                friendsBean.setFriend_id(usersBean.getUser_id());
                List<MutualFriendBean> getMutualFriend = friendsRepository.getMutualFriend(friendsBean);
                usersBean.setMutualFriend(getMutualFriend);
            }
            res.setData(friendList);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // Get all-friend-request-list
    @GetMapping("/request")
    public APIResponseBean getFriendsRequest(HttpServletRequest request,
            @RequestParam(name = "faculty_name", required = false) String faculty_name,
            // @RequestParam(name = "branch_name", required = false) String branch_name,
            @RequestParam(name = "user_id", required = false) Integer user_id) {
        APIResponseBean res = new APIResponseBean();
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("faculty_name", faculty_name);
            // params.put("branch_name", branch_name);
            params.put("user_id", user_id);
            List<UsersBean> friendRequestList = friendsRepository.friendsRequest(params);
            for (UsersBean usersBean : friendRequestList) {
                FriendsBean friendsBean = new FriendsBean();
                friendsBean.setUser_id(user_id);
                friendsBean.setFriend_id(usersBean.getUser_id());
                List<MutualFriendBean> getMutualFriend = friendsRepository.getMutualFriend(friendsBean);
                usersBean.setMutualFriend(getMutualFriend);
            }
            res.setData(friendRequestList);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PostMapping("/insertUpdateFriendship")
    public APIResponseBean insertFriendship(HttpServletRequest request, HttpServletResponse response,
            @RequestBody FriendsBean bean) {
        APIResponseBean res = new APIResponseBean();
        // HashMap<String, Object> params = new HashMap<>();
        try {
            switch (bean.getFriend_status()) {
                case pendingStatus:
                    if (!friendsRepository.checkBeforeCreateFS(bean).isEmpty()) {
                        response.setStatus(UnprocessableContentStatus);
                        res.setResponse_code(UnprocessableContentStatus);
                        res.setResponse_desc("ข้อมูลมีการเปลี่ยนแปลง");
                        return res;
                    }
                    friendsRepository.createFriendship(bean);
                    break;

                case acceptedStatus:
                    if (friendsRepository.checkBeforeAccept(bean).isEmpty()) {
                        response.setStatus(UnprocessableContentStatus);
                        res.setResponse_code(UnprocessableContentStatus);
                        res.setResponse_desc("ข้อมูลมีการเปลี่ยนแปลง");
                        return res;
                    }
                    friendsRepository.updateFriendship(bean);
                    friendsRepository.createFriendship(bean);
                    break;
            }
            res.setData(bean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @DeleteMapping("/cancelFriend")
    public APIResponseBean deleteEvents(HttpServletRequest request, HttpServletResponse response,
            @RequestBody FriendsBean bean) {
        APIResponseBean res = new APIResponseBean();
        try {
            System.out.println("cancel by: " + bean);
            if (!friendsRepository.checkBeforeCancel(bean).isEmpty()) {
                response.setStatus(UnprocessableContentStatus);
                res.setResponse_code(UnprocessableContentStatus);
                res.setResponse_desc("ข้อมูลมีการเปลี่ยนแปลง");
                return res;
            }
            friendsRepository.cancelFriend(bean);
            // res.setData(params);
            res.setResponse_code(200);
            res.setResponse_desc("Cancel-Friend Success");
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
    
    @DeleteMapping("/deleteFriend")
    public APIResponseBean deleteFriend(HttpServletRequest request, HttpServletResponse response,
            @RequestBody FriendsBean bean) {
        APIResponseBean res = new APIResponseBean();
        try {
            
            friendsRepository.deleteFriend(bean);
            res.setResponse_code(200);
            res.setResponse_desc("Delete-Friend Success");
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
}
