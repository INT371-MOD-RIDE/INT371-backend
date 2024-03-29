package sit.int371.modride_service.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import sit.int371.modride_service.dtos.ChangeDTO;
import sit.int371.modride_service.repositories.EventsRepository;
import sit.int371.modride_service.repositories.FriendsRepository;
import sit.int371.modride_service.repositories.ThreadsRepository;
import sit.int371.modride_service.repositories.UsersRepository;
import sit.int371.modride_service.repositories.VehiclesRepository;
import sit.int371.modride_service.services.SecureService;

@RestController
@Validated // ใช้ @Validated ในการ validate request body
@RequestMapping("/api/v1/events")
public class EventsController extends BaseController {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private VehiclesRepository vehiclesRepository;
    @Autowired
    private ThreadsRepository threadsRepository;

    @Autowired
    private SecureService secureService;

    // Get all-events
    @GetMapping("/get")
    public APIResponseBean getAllEvents(HttpServletRequest request,
            @RequestParam(name = "user_id", required = false) Integer user_id) {
        APIResponseBean res = new APIResponseBean();
        try {
            System.out.println("user_id: " + user_id);
            List<EventsBean> eventList = eventsRepository.getAllEvents(user_id);
            for (EventsBean eventsBean : eventList) {
                eventsBean.setEncrypt_id(secureService.encryptAES(String.valueOf(eventsBean.getUser_id()), SECRET_KEY));
            }
            // System.out.println("-----------------------");
            // System.out.println("events-bean: " + eventsBean);
            // FriendsBean friendsBean = new FriendsBean();
            // friendsBean.setUser_id(user_id);
            // friendsBean.setFriend_id(eventsBean.getUser_id());
            // List<FriendsBean> checkFriendShip =
            // friendsRepository.checkFriendshipForEvent(friendsBean);
            // System.out.println("checkFriendship: " + checkFriendShip);
            // if (!checkFriendShip.isEmpty()) {
            // System.out.println("เป็นเพื่อนกัน");
            // eventsBean.setIsThisFriend(true);
            // eventsBean.setFriendShip(checkFriendShip);

            // } else {
            // // ไม่ได้เป็นเพื่อนกัน ให้หา mutual friend
            // eventsBean.setIsThisFriend(false);
            // List<MutualFriendBean> checkMutualFriend =
            // friendsRepository.getMutualFriend(friendsBean);
            // if (!checkMutualFriend.isEmpty()) {
            // eventsBean.setMutualFriend(checkMutualFriend);
            // } else {
            // eventsBean.setMutualFriend(null);
            // }

            // }
            // }
            res.setData(eventList);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @GetMapping("/get/{id}")
    public APIResponseBean getEventsById(HttpServletRequest request,
            @PathVariable Integer id, @RequestParam(name = "user_id", required = false) Integer user_id) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        params.put("event_id", id);
        params.put("user_id", user_id);
        try {
            EventDetailBean eventsBean = eventsRepository.getEventsById(params);
            List<EventMemberBean> members = eventsRepository.getEventMembers(params);
            for (EventMemberBean memberBean : members) {
                memberBean.setEncrypt_id(secureService.encryptAES(String.valueOf(memberBean.getUser_id()), SECRET_KEY));
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
                    List<MutualFriendBean> checkMutualFriend = friendsRepository.getMutualFriend(friendsBean);
                    if (!checkMutualFriend.isEmpty()) {
                        memberBean.setMutualFriend(checkMutualFriend);
                    } else {
                        memberBean.setMutualFriend(null);
                    }

                }
            }
            Integer status = eventsRepository.getMemberStatus(params);
            eventsBean.setStatus(status);
            eventsBean.setMembers(members);
            res.setData(eventsBean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // check events-close for driver
    @GetMapping("/checkNotClose/{user_id}")
    public APIResponseBean checkNotClose(HttpServletRequest request,
            @PathVariable Integer user_id) {
        APIResponseBean res = new APIResponseBean();
        try {
            List<EventsBean> eventList = eventsRepository.getEventNotClose(user_id);
            res.setData(eventList);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PostMapping("/post")
    public APIResponseBean createEvents(HttpServletRequest request, HttpServletResponse response,
            @Valid @RequestBody EventDetailBean bean) throws Exception {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            System.out.println("vehicle_id: " + bean);

            // ⚠️ validate: user_id is exists ?
            List<UsersBean> userList = eventsRepository.CheckUser(bean.getUser_id());
            if (userList.size() == 0) {
                response.setStatus(UnprocessableContentStatus);
                res.setResponse_code(UnprocessableContentStatus);
                res.setResponse_desc("Cannot find this user_id.");
                return res;
            }

            // ⚠️ validate: This user_id is driver (ดักเผื่อ admin ด้วย)?
            List<UsersBean> isDriver = eventsRepository.CheckDriver(bean.getUser_id());
            if (isDriver.size() == 0) {
                response.setStatus(UnprocessableContentStatus);
                res.setResponse_code(UnprocessableContentStatus);
                res.setResponse_desc("This user_id is not driver.");
                return res;
            }

            List<EventsBean> eventList = eventsRepository.getEventNotClose(bean.getUser_id());
            if (eventList.size() > 0) {
                response.setStatus(UnprocessableContentStatus);
                res.setResponse_code(UnprocessableContentStatus);
                res.setResponse_desc("Cannot create new event due to you're still having unclosed event.");
                return res;
            }

            // ⚠️ validate: vehicle id is exists ?
            List<VehiclesBean> vehicleList = eventsRepository.CheckVehicle(bean.getVehicle_id());
            if (vehicleList.size() == 0) {
                response.setStatus(UnprocessableContentStatus);
                res.setResponse_code(UnprocessableContentStatus);
                res.setResponse_desc("Cannot find this vehicle_id.");
                return res;
            }

            // ⚠️ validate: ถ้า user เอารถคนอื่นมา: This vehicle_id is belong to other user.
            List<VehiclesBean> checkVehicleOwner = eventsRepository.CheckVehicleOwner(bean);
            if (checkVehicleOwner.size() == 0) {
                response.setStatus(UnprocessableContentStatus);
                res.setResponse_code(UnprocessableContentStatus);
                res.setResponse_desc("This vehicle_id is belong to other user.");
                return res;
            }

            if (bean.getSeats() == 1) {
                response.setStatus(UnprocessableContentStatus);
                res.setResponse_code(UnprocessableContentStatus);
                res.setResponse_desc(
                        "ไม่สามารถกรอกที่นั่งเท่ากับ 1 ได้ เนื่องจากจำนวนที่นั่งจะถูกลบออกเป็นที่นั่งของผู้ขับขี่");
                return res;
            }

            // bean.setVehicle_id(57);
            // bean.setLicense_plate("กก-1111");
            // params.put("brand", bean.getBrand());
            // params.put("model", bean.getModel());
            // params.put("vehicle_type", bean.getVehicle_type());
            // params.put("vehicle_color", bean.getVehicle_color());
            // params.put("license_plate", bean.getLicense_plate());
            // params.put("seats", bean.getSeats());
            // params.put("car_img_path", bean.getCar_img_path());
            // params.put("vehicle_id", bean.getVehicle_id());
            // System.out.println("getVehiclesByLicense" + params.get("license_plate"));
            // Integer vehicle_id = vehiclesRepository.getVehiclesByLicense(params);
            // bean.setVehicle_id(vehicle_id);
            // if(vehicle_id == null){
            // System.out.println("vehicle_id is null");
            // vehiclesRepository.createVehicles(params);
            // params.put("vehicle_id", params.get("vehicle_id"));
            // // bean.setVehicle_id(params.get("vehicle_id"));
            // bean.setVehicle_id(Integer.parseInt(params.get("vehicle_id").toString()));
            // // bean.setCar_img_path("/images/car/" + params.get("vehicle_id").toString()
            // + ".jpg");
            // }else{
            // System.out.println("vehicle_id not null");
            // bean.setVehicle_id(vehicle_id);
            // // bean.setCar_img_path("/images/car/" + vehicle_id + ".jpg");
            // }
            // vehiclesRepository.createVehicles(params);
            // params.put("vehicle_id", params.get("vehicle_id"));
            // bean.setVehicle_id(params.get("vehicle_id"));
            bean.setSeats(bean.getSeats() - 1);
            eventsRepository.createEvents(bean);
            eventsRepository.createEventLocation(bean);
            params.put("user_id", bean.getUser_id());
            params.put("event_id", bean.getEvent_id());
            params.put("status", 1);
            eventsRepository.joinEvent(params);

            if (bean.getIsEventThread()) {
                // #1 thread_id(ถูกส่งจากหน้าบ้าน) และ event_id(เพิ่งมาจากการสร้าง)
                eventsRepository.createEventWithThread(bean);

                // #2 update thread status เป็น ongoing(1) ของ thread ที่คู่กับ event
                // [อาจพิจารณาใช้เป็น ⚠️trigger ค่อยว่ากันอีกที]
                params.put("thread_id", bean.getThread_id());
                eventsRepository.updateThreadStatus(params);

                // #3 member ของ thread'owner ต้อง join ด้วย
                params.put("user_id", bean.getThreadOwnerId());
                params.put("is_review", 0);
                eventsRepository.joinEvent(params);

                // eventWithThread จะ set ลบ seats 2 ที่นั่ง (driver & thread'owner)
                params.put("join_seat", bean.getSeats() - 1);
                eventsRepository.editSeats(params);
            }

            res.setData(bean);
        } catch (Exception e) {
            if (params.get("vehicle_id") != null) {
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
            eventsRepository.editEvents(bean);
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
            eventsRepository.editEventsStatus(params);
            res.setData(params);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @DeleteMapping("/delete/{id}")
    public APIResponseBean deleteEvents(HttpServletRequest request, @PathVariable Integer id) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            params.put("event_id", id);
            List<Integer> members = eventsRepository.getMembersId(params);
            for (Integer member : members) {
                params.put("members_id", member);
                eventsRepository.deleteMembers(params);
            }
            eventsRepository.deleteLocation(params);

            // check event_with_thread ถ้ามีให้ set status ของ thread_id = 0
            ThreadsBean threadsBean = eventsRepository.checkEventThread(params);
            if (threadsBean != null) {
                params.put("thread_id", threadsBean.getThread_id());
                params.put("status", 0);
                eventsRepository.updateThreadStatus(params);
            }

            eventsRepository.deleteEventThread(params);
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
    public APIResponseBean joinEvent(HttpServletRequest request, @RequestBody HashMap<String, Object> data) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            // params.put("user_id", request.getAttribute("user_id"));
            params.put("user_id", data.get("user_id"));
            params.put("event_id", data.get("event_id"));

            // หากส่ง status เข้ามา แล้วไม่เท่ากับ null จะเข้าเงื่อนไขนี้ เพื่อไป set:
            // seats-1
            System.out.println("data: " + data);
            if (data.get("status") != null) {
                params.put("seats", data.get("seats"));
            } else {
                params.put("status", 0);
            }
            Integer seatAvailable = eventsRepository.getSeats(params);
            Integer duplicateMember = eventsRepository.checkDuplicateMember(params);
            Integer joined = eventsRepository.checkJoined(params);
            if (seatAvailable == 0) {
                res.setResponse_code(UnprocessableContentStatus);
                res.setResponse_desc("Seat is full");
            } else {
                // if(joined == 0){
                // }
                if (duplicateMember == 0) {
                    if (joined == 0) {
                        if (params.get("seats") == null) {
                            params.put("join_seat", seatAvailable);
                        } else {
                            params.put("join_seat", data.get("seats"));
                        }

                        System.out.println("event/seats: " + params);
                        // ถ้า status เป็น 1 จะเข้า edit-seats
                        if (params.get("user_id") != null) {
                            params.put("is_review", 0);
                            eventsRepository.joinEvent(params);
                        } else {
                            eventsRepository.editSeats(params);
                        }
                        // res.setResponse_code(200); // default มัน 200 อยู่แล้ว ไม่จำเป็นต้อง set
                        res.setResponse_desc("success");
                        // if(joined == 0){
                    } else {
                        res.setResponse_code(UnprocessableContentStatus);
                        res.setResponse_desc("Already Joined Other Event");
                    }
                    // }
                } else {
                    res.setResponse_code(UnprocessableContentStatus);
                    res.setResponse_desc("Member Already Join Event");
                }
                // }else{
                // res.setResponse_code(UnprocessableContentStatus);
                // res.setResponse_desc("Member Can Join Only 1 Event");
                // }
            }
            res.setData(params);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @GetMapping("/getVehicle/{id}")
    public APIResponseBean getVehicle(HttpServletRequest request, @PathVariable Integer id) {
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

    // For chat
    @GetMapping("/getChatRoom/{id}")
    public APIResponseBean getChatRoom(HttpServletRequest request, @PathVariable Integer id) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            // params.put("user_id", request.getAttribute("user_id"));
            params.put("user_id", id);
            List<ChatBean> events = eventsRepository.getChatRoom(params);
            events.forEach(event -> {
                params.put("event_id", event.getEvent_id());
                try {
                    event.setEncrypt_id(secureService.encryptAES(String.valueOf(event.getUser_id()), SECRET_KEY));
                    Integer members = eventsRepository.getMemberCount(params);
                    event.setMember_count(members);
                } catch (Exception e) {
                    this.checkException(e, res);
                }
            });
            res.setData(events);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @GetMapping("/getChatRoomMember/{id}")
    public APIResponseBean getChatRoomMember(HttpServletRequest request, @PathVariable Integer id) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            // params.put("user_id", request.getAttribute("user_id"));
            params.put("event_id", id);
            List<ChatBean> events = eventsRepository.getChatRoomMember(params);
            for (ChatBean bean : events) {
                bean.setEncrypt_id(secureService.encryptAES(String.valueOf(bean.getUser_id()), SECRET_KEY));
            }
            res.setData(events);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @GetMapping("/getRequest")
    public APIResponseBean getRequest(HttpServletRequest request,
            @RequestParam(name = "user_id", required = false) Integer user_id,
            @RequestParam(name = "event_id", required = false) Integer event_id) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            // params.put("user_id", request.getAttribute("user_id"));
            params.put("event_id", event_id);
            List<EventMemberBean> members = eventsRepository.getRequestMembers(params);
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
                    List<MutualFriendBean> checkMutualFriend = friendsRepository.getMutualFriend(friendsBean);
                    if (!checkMutualFriend.isEmpty()) {
                        memberBean.setMutualFriend(checkMutualFriend);
                    } else {
                        memberBean.setMutualFriend(null);
                    }

                }
            }
            // List<ChatBean> events = eventsRepository.getChatRoomMember(params);
            res.setData(members);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PutMapping("/responseRequest/{id}")
    public APIResponseBean responseRequest(HttpServletRequest request, @PathVariable Integer id,
            @RequestBody HashMap<String, Object> data) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            params.put("members_id", id);
            params.put("user_id", data.get("user_id"));
            params.put("status", data.get("status"));
            params.put("detail", data.get("detail"));

            // รับ event_id และ seats
            params.put("event_id", data.get("event_id"));
            params.put("seats", data.get("seats"));

            System.out.println("params: " + params);

            // ถ้าเช็ค thread ด้วย userId แล้วพบ thread จะทำการ delete thread ตัวนั้น
            List<ThreadsBean> threadList = threadsRepository.getPassengerEvent2(params);
            if (!threadList.isEmpty()) {
                for (ThreadsBean threadsBean : threadList) {
                    threadsRepository.deleteEventThread(threadsBean.getThread_id());
                    threadsRepository.deleteThread(threadsBean.getThread_id());
                }
            }

            // จัดการ put data "isOwner" สำหรับเช็คว่าเป็นเจ้าของ event
            System.out.println("data.get(\"isOwner\"): " + data.get("isOwner"));
            if (data.get("isOwner") != null) {
                params.put("isOwner", data.get("isOwner"));
            } else {
                params.put("isOwner", false);
            }
            System.out.println("params.get(\"isOwner\"): " + params.get("isOwner"));
            // เช็คว่าเป็นเจ้าของหรือไม่, จะทำการ set all member status ของ event เป็น 4 และ
            // ++count_travel
            if (((Boolean) params.get("isOwner"))) {
                eventsRepository.increaseCountTravel(params);
                eventsRepository.updateWhenOwnerLeave(params);
            } else {
                // set แค่คนเดียว
                // จะ updateIsReview เมื่อ isTriggerBtn == true
                if (data.get("isTriggerBtn") != null) {
                    params.put("isTriggerBtn", data.get("isTriggerBtn"));
                } else {
                    params.put("isTriggerBtn", false);
                }
                if (((Boolean) params.get("isTriggerBtn"))) {
                    // เมื่อ normal-member จบ review-flow = count_travel + 1
                    eventsRepository.increaseCountTravel(params);
                    eventsRepository.updateIsReview(params);
                }

                eventsRepository.responseRequest(params);
            }

            // eventsRepository.responseRequest(params);
            res.setData(params);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @DeleteMapping("/cancelRequest/{id}")
    public APIResponseBean deleteMember(HttpServletRequest request, @PathVariable Integer id) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            System.out.println("id: " + id);
            params.put("members_id", id);
            eventsRepository.deleteMembers(params);
            res.setData(params);
            // res.setResponse_code("200");
            // res.setResponse_desc("Delete Success");
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @GetMapping("/getDeniedDetail")
    public APIResponseBean getDeniedDetail(HttpServletRequest request,
            @RequestParam(name = "user_id", required = false) Integer user_id,
            @RequestParam(name = "members_id", required = false) Integer members_id) {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            params.put("members_id", members_id);
            List<DeniedRequestBean> owner = eventsRepository.getDeniedDetail(params);
            for (DeniedRequestBean deniedRequestBeanBean : owner) {
                FriendsBean friendsBean = new FriendsBean();
                friendsBean.setUser_id(user_id);
                friendsBean.setFriend_id(deniedRequestBeanBean.getUser_id());
                List<FriendsBean> checkFriendShip = friendsRepository.checkFriendshipForEvent(friendsBean);
                System.out.println("checkFriendship: " + checkFriendShip);
                if (!checkFriendShip.isEmpty()) {
                    System.out.println("เป็นเพื่อนกัน");
                    deniedRequestBeanBean.setIsThisFriend(true);
                    deniedRequestBeanBean.setFriendShip(checkFriendShip);

                } else {
                    // ไม่ได้เป็นเพื่อนกัน ให้หา mutual friend
                    deniedRequestBeanBean.setIsThisFriend(false);
                    List<MutualFriendBean> checkMutualFriend = friendsRepository.getMutualFriend(friendsBean);
                    if (!checkMutualFriend.isEmpty()) {
                        deniedRequestBeanBean.setMutualFriend(checkMutualFriend);
                    } else {
                        deniedRequestBeanBean.setMutualFriend(null);
                    }

                }
            }
            res.setData(owner);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @GetMapping("/getEventDriver")
    public APIResponseBean getEventDriver(HttpServletRequest request,
            @RequestParam(name = "event_id", required = false) Integer event_id,
            @RequestParam(name = "user_id", required = false) Integer user_id) throws Exception {
        APIResponseBean res = new APIResponseBean();
        HashMap<String, Object> params = new HashMap<>();
        try {
            params.put("event_id", event_id);
            params.put("user_id", user_id);
            HashMap<String, Object> owner = eventsRepository.getEventDriver(params);

            // encrypt driverId
            Integer driverId = (Integer) owner.get("user_id");
            owner.put("encrypt_id",secureService.encryptAES(String.valueOf(driverId), SECRET_KEY));

            Integer getMemberId = eventsRepository.getMemberId(params);
            owner.put("member_id", getMemberId);
            res.setData(owner);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PostMapping("/ratingDriver")
    public APIResponseBean ratingDriver(HttpServletRequest request, @RequestBody RatingBean data) {
        APIResponseBean res = new APIResponseBean();
        // HashMap<String, Object> params = new HashMap<>();
        try {
            Integer countRating = eventsRepository.findRating(data);
            System.out.println("countRating: " + countRating);
            HashMap<String, Object> params = new HashMap<>();
            // user_id นี้ คือ id ของ driver คนที่เราจะ rate
            params.put("user_id", data.getUser_id());
            params.put("event_id", data.getEvent_id());

            // จะ updateIsReview เมื่อ isTriggerBtn == true
            if (data.getIsTriggerBtn()) {
                // ต้อง replace ด้วย id ของเรา เพื่อเพิ่ม countTravel และ updateIsReview
                // เป็นอันจบ flow ของ passenger
                params.put("user_id", data.getMy_id());
                eventsRepository.increaseCountTravel(params);
                eventsRepository.updateIsReview(params);
                // กลับไป set user_id ของ driver ที่เราจะ rate
                params.put("user_id", data.getUser_id());
            }

            if (countRating > 0) {
                HashMap<String, Object> rating = eventsRepository.getRating(data);
                data.setRating_point(Integer.parseInt(rating.get("rating_point").toString()) + data.getRating_point());
                data.setRating_amount(Integer.parseInt(rating.get("rating_amount").toString()) + 1);
                eventsRepository.updateRating(data);
            } else {
                data.setRating_amount(1);
                eventsRepository.ratingDriver(data);
            }
            params.put("members_id", data.getMember_id());
            params.put("status", 4);
            eventsRepository.responseRequest(params);
            res.setData(data);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
}