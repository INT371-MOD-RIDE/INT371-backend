package sit.int371.modride_service.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sit.int371.modride_service.beans.APIResponseBean;
import sit.int371.modride_service.beans.EventDetailBean;
import sit.int371.modride_service.beans.EventMemberBean;
import sit.int371.modride_service.beans.FriendsBean;
import sit.int371.modride_service.beans.MutualFriendBean;
import sit.int371.modride_service.beans.VehiclesBean;
import sit.int371.modride_service.beans.driver_profile.DriverProfileBean;
import sit.int371.modride_service.beans.driver_profile.LicensesBean;
import sit.int371.modride_service.repositories.DriverRegisterRepository;
import sit.int371.modride_service.repositories.VehiclesRepository;

@RestController
@RequestMapping("/api/v1/driverRegister")
public class DriverRegisterController extends BaseController {

    @Autowired
    private DriverRegisterRepository driverRegisterRepository;

    @GetMapping("/getDriverProfile/{user_id}")
    public APIResponseBean getEventsById(HttpServletRequest request,
            @PathVariable Integer user_id) {
        APIResponseBean res = new APIResponseBean();
        DriverProfileBean driverProfileBean = new DriverProfileBean();
        try {
            driverProfileBean.setLicenseDetail(driverRegisterRepository.getLicenseDetail(user_id));
            driverProfileBean.setVehicleList(driverRegisterRepository.getVehicleList(user_id));
            res.setData(driverProfileBean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PostMapping("/uploadLicense")
    public APIResponseBean uploadLicense(HttpServletRequest request,
            @RequestBody LicensesBean bean) throws Exception {
        APIResponseBean res = new APIResponseBean();
        try {
            System.out.println(bean);
            driverRegisterRepository.insertLicense(bean);
            driverRegisterRepository.createLicenseAppStatus(bean.getLicense_id());
            res.setData(bean);
        } catch (Exception e) {

            this.checkException(e, res);
        }
        return res;
    }

    // สำหรับให้ admin เรียกใช้งาน
    @PutMapping("/updateLicenseStatus/{license_id}")
    public APIResponseBean editEvents(HttpServletRequest request, @PathVariable Integer license_id,
            @RequestBody LicensesBean approvalStatusBean) {
        APIResponseBean res = new APIResponseBean();
        // HashMap<String, Object> params = new HashMap<>();
        try {
            approvalStatusBean.setLicense_id(license_id);
            driverRegisterRepository.updateLicenseAppStatus(approvalStatusBean);
            res.setData(approvalStatusBean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @PostMapping("/uploadVehicle")
    public APIResponseBean uploadVehicle(HttpServletRequest request,
            @RequestBody VehiclesBean bean) throws Exception {
        APIResponseBean res = new APIResponseBean();
        try {
            System.out.println(bean);
            driverRegisterRepository.insertVehicle(bean);
            res.setData(bean);
        } catch (Exception e) {

            this.checkException(e, res);
        }
        return res;
    }

}
