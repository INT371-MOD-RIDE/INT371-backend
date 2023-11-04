// เก่าแล้ว อันนี้ไม่ใช้
package sit.int371.modride_service.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sit.int371.modride_service.beans.APIResponseBean;
import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.FriendsBean;
import sit.int371.modride_service.beans.MutualFriendBean;
import sit.int371.modride_service.beans.UsersBean;
import sit.int371.modride_service.dtos.ChangeDTO;
import sit.int371.modride_service.repositories.EventsRepository;
import sit.int371.modride_service.repositories.FriendsRepository;
import sit.int371.modride_service.repositories.UsersRepository;

@RestController
@RequestMapping("/api/v1/events")
public class EventsController extends BaseController {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private FriendsRepository friendsRepository;

    // Get all-users
    @GetMapping("/get")
    public APIResponseBean getAllEvents(HttpServletRequest request,
            @RequestParam(name = "user_id", required = false) Integer user_id) {
        APIResponseBean res = new APIResponseBean();
        try {
            System.out.println("user_id: " + user_id);
            List<EventsBean> eventList = eventsRepository.getAllEvents();

            for (EventsBean eventsBean : eventList) {
                System.out.println("-----------------------");
                System.out.println("events-bean: " + eventsBean);
                FriendsBean friendsBean = new FriendsBean();
                friendsBean.setUser_id(user_id);
                friendsBean.setFriend_id(eventsBean.getUser_id());
                List<FriendsBean> checkFriendShip = friendsRepository.checkFriendshipForEvent(friendsBean);
                System.out.println("checkFriendship: " + checkFriendShip);
                if (!checkFriendShip.isEmpty()) {
                    System.out.println("เป็นเพื่อนกัน");
                    eventsBean.setIsThisFriend(true);
                    eventsBean.setFriendShip(checkFriendShip);

                } else {
                    // ไม่ได้เป็นเพื่อนกัน ให้หา mutual friend
                    eventsBean.setIsThisFriend(false);
                    List<MutualFriendBean> checkMutualFriend = friendsRepository.checkMutualFriend(friendsBean);
                    if (!checkMutualFriend.isEmpty()) {
                        eventsBean.setMutualFriend(checkMutualFriend);
                    } else {
                        eventsBean.setMutualFriend(null);
                    }

                }
            }
            res.setData(eventList);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
}