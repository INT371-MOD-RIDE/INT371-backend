package sit.int371.modride_service.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sit.int371.modride_service.beans.APIResponseBean;
import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.FriendsBean;
import sit.int371.modride_service.beans.UsersBean;
import sit.int371.modride_service.repositories.FriendsRepository;
import sit.int371.modride_service.repositories.UsersRepository;

@RestController
@RequestMapping("/api/v1/socialNetwork")
public class FriendsController extends BaseController {

    @Autowired
    private FriendsRepository friendsRepository;

    // Get all-users
    @GetMapping("/getAll")
    public APIResponseBean getAllSocialNetwork(HttpServletRequest request,
            @RequestParam(name = "fac_name", required = false) String fac_name,
            @RequestParam(name = "branch", required = false) String branch) {
        APIResponseBean res = new APIResponseBean();
        try {
            HashMap<String, Object> params = new HashMap<>();
            List<UsersBean> friendListSuggest = friendsRepository.friendListSuggestion(params);
            res.setData(friendListSuggest);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PostMapping("/insertFriendship")
    public APIResponseBean insertFriendship(HttpServletRequest request, 
    @RequestBody FriendsBean bean) {
        APIResponseBean res = new APIResponseBean();
        // HashMap<String, Object> params = new HashMap<>();
        try {
            friendsRepository.createFriendship(bean);
            res.setData(bean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
}
