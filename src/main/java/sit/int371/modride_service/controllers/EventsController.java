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
import sit.int371.modride_service.beans.ChatBean;
import sit.int371.modride_service.beans.EventDetailBean;
import sit.int371.modride_service.beans.EventMemberBean;
import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.FriendsBean;
import sit.int371.modride_service.beans.MutualFriendBean;
import sit.int371.modride_service.beans.UsersBean;
import sit.int371.modride_service.beans.VehiclesBean;
import sit.int371.modride_service.dtos.ChangeDTO;
import sit.int371.modride_service.repositories.EventsRepository;
import sit.int371.modride_service.repositories.FriendsRepository;
import sit.int371.modride_service.repositories.UsersRepository;
import sit.int371.modride_service.repositories.VehiclesRepository;

@RestController
@RequestMapping("/api/v1/events")
public class EventsController extends BaseController {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired  
    private VehiclesRepository vehiclesRepository;

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
    @PathVariable Integer id,@RequestParam(name = "user_id", required = false) Integer user_id) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        params.put("event_id", id);
        // params.put("user_id", user_id);
        try {
            EventDetailBean eventsBean = eventsRepository.getEventsById(params);
            List<EventMemberBean> members = eventsRepository.getEventMembers(params);
            for (EventMemberBean memberBean : members) {
            FriendsBean friendsBean = new FriendsBean();
            friendsBean.setUser_id(user_id);
                friendsBean.setFriend_id(memberBean.getUser_id());
                List<FriendsBean> checkFriendShip = friendsRepository.checkFriendshipForEvent(friendsBean);
                System.out.println("checkFriendship: " + checkFriendShip);
                if (!checkFriendShip.isEmpty()) {
                    System.out.println("เป็นเพื่อนกัน");
                    memberBean.setIsThisFriend(true);
                    memberBean.setFriendShip(checkFriendShip);

                } else {
                    // ไม่ได้เป็นเพื่อนกัน ให้หา mutual friend
                    memberBean.setIsThisFriend(false);
                    List<MutualFriendBean> checkMutualFriend = friendsRepository.checkMutualFriend(friendsBean);
                    if (!checkMutualFriend.isEmpty()) {
                        memberBean.setMutualFriend(checkMutualFriend);
                    } else {
                        memberBean.setMutualFriend(null);
                    }

                }
            }
            eventsBean.setMembers(members);
            res.setData(eventsBean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PostMapping("/post")
    public APIResponseBean createEvents(HttpServletRequest request, 
    @RequestBody EventDetailBean bean) throws Exception {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            params.put("user_id", bean.getUser_id());
            params.put("brand", bean.getBrand());
            params.put("model", bean.getModel());
            params.put("vehicle_type", bean.getVehicle_type());
            params.put("vehicle_color", bean.getVehicle_color());
            params.put("license", bean.getLicense());
            params.put("car_img_path", bean.getCar_img_path());
            System.out.println("getVehiclesByLicense" + params.get("license"));
            Integer vehicle_id = vehiclesRepository.getVehiclesByLicense(params);
            if(vehicle_id == null){
                System.out.println("vehicle_id is null");
                vehiclesRepository.createVehicles(params);
                params.put("vehicle_id", params.get("vehicle_id"));
                // bean.setVehicle_id(params.get("vehicle_id"));
                bean.setVehicle_id(Integer.parseInt(params.get("vehicle_id").toString()));
                // bean.setCar_img_path("/images/car/" + params.get("vehicle_id").toString() + ".jpg");
            }else{
                System.out.println("vehicle_id not null");
                bean.setVehicle_id(vehicle_id);
                // bean.setCar_img_path("/images/car/" + vehicle_id + ".jpg");
            }
            // vehiclesRepository.createVehicles(params);
            // params.put("vehicle_id", params.get("vehicle_id"));
            // bean.setVehicle_id(params.get("vehicle_id"));
            eventsRepository.createEvents(bean);
            params.put("event_id", bean.getEvent_id());
            eventsRepository.joinEvent(params);
            res.setData(bean);
        } catch (Exception e) {
            if(params.get("vehicle_id") != null){
                params.put("vehicle_id", params.get("vehicle_id"));
                vehiclesRepository.deleteVehicles(params);
            }
            // bean.setVehicle_id(Integer.parseInt(params.get("vehicle_id").toString()));
            // vehiclesRepository.deleteVehicles(params);
            // System.out.println("deleting vehicle_id: " + params.get("vehicle_id"));
            this.checkException(e, res);
        }
        return res;
    }

    @PutMapping("/edit/{id}")
    public APIResponseBean editEvents(HttpServletRequest request,@PathVariable Integer id,
    @RequestBody EventDetailBean bean)
    {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            // params.put("event_id", id);
            // params.put("event_name", data.get("event_name"));
            // params.put("event_detail", data.get("event_detail"));
            // params.put("start_point", data.get("start_point"));
            // params.put("dest_point", data.get("dest_point"));
            // params.put("departure_time", data.get("departure_time"));
            // params.put("vehicle_id", data.get("vehicle_id"));
            // params.put("seats", data.get("seats"));
            // params.put("costs", data.get("costs"));
            bean.setEvent_id(id);
            System.out.println("editbean: " + bean);
            eventsRepository.editEvents(bean);
            params.put("vehicle_id", bean.getVehicle_id());
            params.put("seats", bean.getSeats());
            params.put("brand", bean.getBrand());
            params.put("model", bean.getModel());
            params.put("vehicle_type", bean.getVehicle_type());
            params.put("vehicle_color", bean.getVehicle_color());
            params.put("license", bean.getLicense());
            params.put("car_img_path", bean.getCar_img_path());
            vehiclesRepository.editVehicles(params);
            res.setData(bean);
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
            List<Integer> members = eventsRepository.getMembersId(params);
            for (Integer member : members) {
                params.put("members_id", member);
                eventsRepository.deleteMembers(params);
            }
            eventsRepository.deleteEvents(params);
            res.setData(params);
            // res.setResponse_code("200");
            // res.setResponse_desc("Delete Success");
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
    @PostMapping("/joinEvent")
    public APIResponseBean joinEvent(HttpServletRequest request,@RequestBody HashMap<String, Object> data)
    {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            // params.put("user_id", request.getAttribute("user_id"));
            params.put("user_id", data.get("user_id"));
            params.put("event_id", data.get("event_id"));
            Integer seatAvailable = eventsRepository.getSeats(params);
            Integer duplicateMember = eventsRepository.checkDuplicateMember(params);
            if(seatAvailable == 0){
                res.setResponse_code("400");
                res.setResponse_desc("Seat is full");
            }else{
                if(duplicateMember == 0){
                    params.put("seats", seatAvailable-1);
                    eventsRepository.joinEvent(params);
                    eventsRepository.editSeats(params);
                    res.setResponse_code("200");
                    res.setResponse_desc("success");
                }else{
                    res.setResponse_code("400");
                    res.setResponse_desc("Member Already Join Event");
                }
            }
            res.setData(params);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
    @GetMapping("/getVehicle/{id}")
    public APIResponseBean getVehicle(HttpServletRequest request,@PathVariable Integer id){
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            // params.put("user_id", request.getAttribute("user_id"));
            params.put("user_id", id);
            List<VehiclesBean> vehicles = eventsRepository.getVehicles(params);
            res.setData(vehicles);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
    //For chat
    @GetMapping("/getChatRoom/{id}")
    public APIResponseBean getChatRoom(HttpServletRequest request,@PathVariable Integer id){
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            // params.put("user_id", request.getAttribute("user_id"));
            params.put("user_id", id);
            List<ChatBean> events = eventsRepository.getChatRoom(params);
            res.setData(events);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
}