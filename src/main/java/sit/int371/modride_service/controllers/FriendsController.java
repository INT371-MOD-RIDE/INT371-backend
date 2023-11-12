package sit.int371.modride_service.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sit.int371.modride_service.beans.APIResponseBean;
import sit.int371.modride_service.beans.UsersBean;
import sit.int371.modride_service.repositories.FriendsRepository;
import sit.int371.modride_service.repositories.UsersRepository;

@RestController
@RequestMapping(value = "/api/v1/friends")
public class FriendsController extends BaseController {

    @Autowired
    private FriendsRepository friendsRepository;

    // Get all-users
    @GetMapping("/getAll")
    public APIResponseBean getAllUser(HttpServletRequest request,
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
}
