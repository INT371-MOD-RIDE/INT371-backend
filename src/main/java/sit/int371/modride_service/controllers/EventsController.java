package sit.int371.modride_service.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.annotation.Delete;
import com.azure.core.annotation.Post;

import sit.int371.modride_service.beans.APIResponseBean;
import sit.int371.modride_service.beans.EventDetailBean;
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

    @GetMapping("/get/{id}")
    public APIResponseBean getEventsById(HttpServletRequest request, 
    @PathVariable Integer id) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        params.put("event_id", id);
        try {
            List<EventDetailBean> eventsBean = eventsRepository.getEventsById(params);
            res.setData(eventsBean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PostMapping("/post")
    public APIResponseBean createEvents(HttpServletRequest request, 
    @RequestBody EventsBean bean) {
        APIResponseBean res = new APIResponseBean();
        // HashMap<String, Object> params = new HashMap<>();
        try {
            // params.put("user_id", data.get("user_id"));
            // params.put("event_name", data.get("event_name"));
            // params.put("event_detail", data.get("event_detail"));
            // params.put("start_point", data.get("start_point"));
            // params.put("dest_point", data.get("dest_point"));
            // params.put("departure_time", data.get("departure_time"));
            // params.put("seats", data.get("seats"));
            // params.put("costs", data.get("costs"));
            eventsRepository.createEvents(bean);
            res.setData(bean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PutMapping("/edit/{id}")
    public APIResponseBean editEvents(HttpServletRequest request,@PathVariable Integer id,
    @RequestBody HashMap<String, Object> data)
    {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            params.put("event_id", id);
            params.put("event_name", data.get("event_name"));
            params.put("event_detail", data.get("event_detail"));
            params.put("start_point", data.get("start_point"));
            params.put("dest_point", data.get("dest_point"));
            params.put("departure_time", data.get("departure_time"));
            params.put("vehicle_id", data.get("vehicle_id"));
            params.put("seats", data.get("seats"));
            params.put("costs", data.get("costs"));
            eventsRepository.editEvents(params);
            res.setData(params);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
    @PutMapping("/updateStatus/{id}")
    public APIResponseBean closeEvents(HttpServletRequest request,@PathVariable Integer id,
    @RequestBody HashMap<String, Object> data)
    {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            params.put("event_id", id);
            params.put("status", data.get("status"));
            eventsRepository.editEventsStatus(params);
            res.setData(params);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @DeleteMapping("/delete/{id}")
    public APIResponseBean deleteEvents(HttpServletRequest request,@PathVariable Integer id)
    {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            params.put("event_id", id);
            eventsRepository.deleteEvents(params);
            // res.setData(params);
            res.setResponse_code("200");
            res.setResponse_desc("Delete Success");
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
}