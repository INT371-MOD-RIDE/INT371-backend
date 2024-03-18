package sit.int371.modride_service.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sit.int371.modride_service.beans.APIResponseBean;
import sit.int371.modride_service.beans.ChatBean;
import sit.int371.modride_service.beans.DeniedRequestBean;
import sit.int371.modride_service.beans.EventDetailBean;
import sit.int371.modride_service.beans.EventMemberBean;
import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.FriendsBean;
import sit.int371.modride_service.beans.MutualFriendBean;
import sit.int371.modride_service.beans.RatingBean;
import sit.int371.modride_service.beans.ThreadsBean;
import sit.int371.modride_service.beans.UsersBean;
import sit.int371.modride_service.beans.VehiclesBean;
import sit.int371.modride_service.repositories.EventsRepository;
import sit.int371.modride_service.repositories.FriendsRepository;
import sit.int371.modride_service.repositories.ThreadsRepository;
import sit.int371.modride_service.repositories.VehiclesRepository;

@RestController
@Validated // ใช้ @Validated ในการ validate request body
@RequestMapping("/api/v1/threads")
public class ThreadsController extends BaseController {
    @Autowired
    private ThreadsRepository threadsRepository;
    // @Autowired
    // private FriendsRepository friendsRepository;
    // @Autowired
    // private VehiclesRepository vehiclesRepository;

    // getPassengerEvent
    @GetMapping("/getPassengerEvent/{user_id}")
    public APIResponseBean getPassengerEvent(HttpServletRequest request,
            @PathVariable Integer user_id
    // @ModelAttribute ThreadsBean bean
    ) {
        APIResponseBean res = new APIResponseBean();
        try {
            res.setData(threadsRepository.getPassengerEvent(user_id));
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @GetMapping("/pasEventDetail/{thread_id}")
    public APIResponseBean pasEventDetail(HttpServletRequest request,
            @PathVariable Integer thread_id) {
        APIResponseBean res = new APIResponseBean();
        // HashMap<String, Object> params = new HashMap<>();
        // params.put("event_id", id);
        // params.put("user_id", user_id);
        try {
            res.setData(threadsRepository.getPasEventDetail(thread_id));
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // // check events-close for driver
    // @GetMapping("/checkNotClose/{user_id}")
    // public APIResponseBean checkNotClose(HttpServletRequest request,
    // @PathVariable Integer user_id) {
    // APIResponseBean res = new APIResponseBean();
    // try {
    // List<EventsBean> eventList = eventsRepository.getEventNotClose(user_id);
    // res.setData(eventList);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }

    @PostMapping("/createThread")
    public APIResponseBean createThread(HttpServletRequest request, HttpServletResponse response,
            @RequestBody ThreadsBean bean) throws Exception {
        APIResponseBean res = new APIResponseBean();
        // HashMap<String, Object> params = new HashMap<>();
        try {
            threadsRepository.createThread(bean);
            res.setData(bean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @DeleteMapping("/delete/{id}")
    public APIResponseBean deleteThread(HttpServletRequest request, @PathVariable Integer id) {
        APIResponseBean res = new APIResponseBean();
        try {
            threadsRepository.deleteThread(id);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PutMapping("/edit/{id}")
    public APIResponseBean editEvents(HttpServletRequest request, @PathVariable Integer id,
            @Valid @RequestBody EventDetailBean bean) {
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
            // จะนับว่าเป็นการ select-vehicle process ใหม่ จะต้อง -1 เพราะนับเจ้าของด้วย
            if (bean.getOld_seats() != bean.getSeats()) {
                bean.setSeats(bean.getSeats() - 1);
            }
            System.out.println("editbean: " + bean);
            // eventsRepository.editEvents(bean);
            // params.put("vehicle_id", bean.getVehicle_id());
            // params.put("seats", bean.getSeats());
            // params.put("brand", bean.getBrand());
            // params.put("model", bean.getModel());
            // params.put("vehicle_type", bean.getVehicle_type());
            // params.put("vehicle_color", bean.getVehicle_color());
            // params.put("license_plate", bean.getLicense_plate());
            // vehiclesRepository.editVehicles(params);
            res.setData(bean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PutMapping("/updateStatus/{id}")
    public APIResponseBean closeEvents(HttpServletRequest request, @PathVariable Integer id,
            @RequestBody HashMap<String, Object> data) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            params.put("event_id", id);
            params.put("status", data.get("status"));
            // eventsRepository.editEventsStatus(params);
            res.setData(params);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // @PostMapping("/joinEvent")
    // public APIResponseBean joinEvent(HttpServletRequest request, @RequestBody
    // HashMap<String, Object> data) {
    // APIResponseBean res = new APIResponseBean();
    // HashMap<String, Object> params = new HashMap<>();
    // try {
    // // params.put("user_id", request.getAttribute("user_id"));
    // params.put("user_id", data.get("user_id"));
    // params.put("event_id", data.get("event_id"));
    //
    // // หากส่ง status เข้ามา แล้วไม่เท่ากับ null จะเข้าเงื่อนไขนี้ เพื่อไป set:
    // // seats-1
    // System.out.println("data: " + data);
    // if (data.get("status") != null) {
    // params.put("seats", data.get("seats"));
    // } else {
    // params.put("status", 0);
    // }
    // Integer seatAvailable = eventsRepository.getSeats(params);
    // Integer duplicateMember = eventsRepository.checkDuplicateMember(params);
    // if (seatAvailable == 0) {
    // res.setResponse_code(UnprocessableContentStatus);
    // res.setResponse_desc("Seat is full");
    // } else {
    // if (duplicateMember == 0) {
    // if (params.get("seats") == null) {
    // params.put("join_seat", seatAvailable);
    // } else {
    // params.put("join_seat", data.get("seats"));
    // }
    //
    // System.out.println("event/seats: " + params);
    // // ถ้า status เป็น 1 จะเข้า edit-seats
    // if (params.get("user_id") != null) {
    // eventsRepository.joinEvent(params);
    // } else {
    // eventsRepository.editSeats(params);
    // }
    // // res.setResponse_code(200); // default มัน 200 อยู่แล้ว ไม่จำเป็นต้อง set
    // res.setResponse_desc("success");
    // } else {
    // res.setResponse_code(UnprocessableContentStatus);
    // res.setResponse_desc("Member Already Join Event");
    // }
    // }
    // res.setData(params);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }

    // @GetMapping("/getVehicle/{id}")
    // public APIResponseBean getVehicle(HttpServletRequest request, @PathVariable
    // Integer id) {
    // APIResponseBean res = new APIResponseBean();
    // HashMap<String, Object> params = new HashMap<>();
    // try {
    // // params.put("user_id", request.getAttribute("user_id"));
    // params.put("user_id", id);
    // List<VehiclesBean> vehicles = eventsRepository.getVehicles(params);
    // res.setData(vehicles);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }

    // For chat
    // @GetMapping("/getChatRoom/{id}")
    // public APIResponseBean getChatRoom(HttpServletRequest request, @PathVariable
    // Integer id) {
    // APIResponseBean res = new APIResponseBean();
    // HashMap<String, Object> params = new HashMap<>();
    // try {
    // // params.put("user_id", request.getAttribute("user_id"));
    // params.put("user_id", id);
    // List<ChatBean> events = eventsRepository.getChatRoom(params);
    // events.forEach(event -> {
    // params.put("event_id", event.getEvent_id());
    // try {
    // Integer members = eventsRepository.getMemberCount(params);
    // event.setMember_count(members);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // });
    // res.setData(events);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }

    // @GetMapping("/getChatRoomMember/{id}")
    // public APIResponseBean getChatRoomMember(HttpServletRequest request,
    // @PathVariable Integer id) {
    // APIResponseBean res = new APIResponseBean();
    // HashMap<String, Object> params = new HashMap<>();
    // try {
    // // params.put("user_id", request.getAttribute("user_id"));
    // params.put("event_id", id);
    // List<ChatBean> events = eventsRepository.getChatRoomMember(params);
    // res.setData(events);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }

    // @GetMapping("/getRequest")
    // public APIResponseBean getRequest(HttpServletRequest request,
    // @RequestParam(name = "user_id", required = false) Integer user_id,
    // @RequestParam(name = "event_id", required = false) Integer event_id) {
    // APIResponseBean res = new APIResponseBean();
    // HashMap<String, Object> params = new HashMap<>();
    // try {
    // // params.put("user_id", request.getAttribute("user_id"));
    // params.put("event_id", event_id);
    // List<EventMemberBean> members = eventsRepository.getRequestMembers(params);
    // for (EventMemberBean memberBean : members) {
    // FriendsBean friendsBean = new FriendsBean();
    // friendsBean.setUser_id(user_id);
    // friendsBean.setFriend_id(memberBean.getUser_id());
    // List<FriendsBean> checkFriendShip =
    // friendsRepository.checkFriendshipForEvent(friendsBean);
    // System.out.println("checkFriendship: " + checkFriendShip);
    // if (!checkFriendShip.isEmpty()) {
    // System.out.println("เป็นเพื่อนกัน");
    // memberBean.setIsThisFriend(true);
    // memberBean.setFriendShip(checkFriendShip);

    // } else {
    // // ไม่ได้เป็นเพื่อนกัน ให้หา mutual friend
    // memberBean.setIsThisFriend(false);
    // List<MutualFriendBean> checkMutualFriend =
    // friendsRepository.getMutualFriend(friendsBean);
    // if (!checkMutualFriend.isEmpty()) {
    // memberBean.setMutualFriend(checkMutualFriend);
    // } else {
    // memberBean.setMutualFriend(null);
    // }

    // }
    // }
    // // List<ChatBean> events = eventsRepository.getChatRoomMember(params);
    // res.setData(members);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }

    // @PutMapping("/responseRequest/{id}")
    // public APIResponseBean responseRequest(HttpServletRequest request,
    // @PathVariable Integer id,
    // @RequestBody HashMap<String, Object> data) {
    // APIResponseBean res = new APIResponseBean();
    // HashMap<String, Object> params = new HashMap<>();
    // try {
    // params.put("members_id", id);
    // // params.put("user_id", data.get("user_id"));
    // params.put("status", data.get("status"));
    // params.put("detail", data.get("detail"));

    // // รับ event_id และ seats
    // params.put("event_id", data.get("event_id"));
    // params.put("seats", data.get("seats"));

    // System.out.println("params: " + params);
    // eventsRepository.responseRequest(params);
    // res.setData(params);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }

    // @DeleteMapping("/cancelRequest/{id}")
    // public APIResponseBean deleteMember(HttpServletRequest request, @PathVariable
    // Integer id) {
    // APIResponseBean res = new APIResponseBean();
    // HashMap<String, Object> params = new HashMap<>();
    // try {
    // System.out.println("id: " + id);
    // params.put("members_id", id);
    // eventsRepository.deleteMembers(params);
    // res.setData(params);
    // // res.setResponse_code("200");
    // // res.setResponse_desc("Delete Success");
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }

    // @GetMapping("/getDeniedDetail")
    // public APIResponseBean getDeniedDetail(HttpServletRequest request,
    // @RequestParam(name = "user_id", required = false) Integer user_id,
    // @RequestParam(name = "members_id", required = false) Integer members_id) {
    // APIResponseBean res = new APIResponseBean();
    // HashMap<String, Object> params = new HashMap<>();
    // try {
    // params.put("members_id", members_id);
    // List<DeniedRequestBean> owner = eventsRepository.getDeniedDetail(params);
    // for (DeniedRequestBean deniedRequestBeanBean : owner) {
    // FriendsBean friendsBean = new FriendsBean();
    // friendsBean.setUser_id(user_id);
    // friendsBean.setFriend_id(deniedRequestBeanBean.getUser_id());
    // List<FriendsBean> checkFriendShip =
    // friendsRepository.checkFriendshipForEvent(friendsBean);
    // System.out.println("checkFriendship: " + checkFriendShip);
    // if (!checkFriendShip.isEmpty()) {
    // System.out.println("เป็นเพื่อนกัน");
    // deniedRequestBeanBean.setIsThisFriend(true);
    // deniedRequestBeanBean.setFriendShip(checkFriendShip);

    // } else {
    // // ไม่ได้เป็นเพื่อนกัน ให้หา mutual friend
    // deniedRequestBeanBean.setIsThisFriend(false);
    // List<MutualFriendBean> checkMutualFriend =
    // friendsRepository.getMutualFriend(friendsBean);
    // if (!checkMutualFriend.isEmpty()) {
    // deniedRequestBeanBean.setMutualFriend(checkMutualFriend);
    // } else {
    // deniedRequestBeanBean.setMutualFriend(null);
    // }

    // }
    // }
    // res.setData(owner);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }

    // @GetMapping("/getEventDriver")
    // public APIResponseBean getEventDriver(HttpServletRequest request,
    // @RequestParam(name = "event_id", required = false) Integer event_id,
    // @RequestParam(name = "user_id", required = false) Integer user_id) throws
    // Exception {
    // APIResponseBean res = new APIResponseBean();
    // HashMap<String, Object> params = new HashMap<>();
    // try {
    // params.put("event_id", event_id);
    // params.put("user_id", user_id);
    // HashMap<String, Object> owner = eventsRepository.getEventDriver(params);
    // Integer getMemberId = eventsRepository.getMemberId(params);
    // owner.put("member_id", getMemberId);
    // res.setData(owner);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }

    // @PostMapping("/ratingDriver")
    // public APIResponseBean ratingDriver(HttpServletRequest request, @RequestBody
    // RatingBean data) {
    // APIResponseBean res = new APIResponseBean();
    // // HashMap<String, Object> params = new HashMap<>();
    // try {
    // Integer countRating = eventsRepository.findRating(data);
    // System.out.println("countRating: " + countRating);
    // HashMap<String, Object> params = new HashMap<>();
    // if (countRating > 0) {
    // HashMap<String, Object> rating = eventsRepository.getRating(data);
    // data.setRating_point(Integer.parseInt(rating.get("rating_point").toString())
    // + data.getRating_point());
    // data.setRating_amount(Integer.parseInt(rating.get("rating_amount").toString())
    // + 1);
    // eventsRepository.updateRating(data);
    // } else {
    // data.setRating_amount(1);
    // eventsRepository.ratingDriver(data);
    // }
    // params.put("members_id", data.getMember_id());
    // params.put("status", 4);
    // eventsRepository.responseRequest(params);
    // res.setData(data);
    // } catch (Exception e) {
    // this.checkException(e, res);
    // }
    // return res;
    // }
}