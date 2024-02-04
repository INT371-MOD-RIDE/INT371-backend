package sit.int371.modride_service.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sit.int371.modride_service.beans.APIResponseBean;
import sit.int371.modride_service.beans.EventDetailBean;
import sit.int371.modride_service.beans.VehiclesBean;
import sit.int371.modride_service.beans.licenses.LicenseApprovalStatusBean;
import sit.int371.modride_service.beans.licenses.LicensesBean;
import sit.int371.modride_service.repositories.DriverRegisterRepository;
import sit.int371.modride_service.repositories.VehiclesRepository;

@RestController
@RequestMapping("/api/v1/driverRegister")
public class DriverRegisterController extends BaseController {

    @Autowired
    private DriverRegisterRepository driverRegisterRepository;

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

    @PutMapping("/updateLicenseStatus/{license_id}")
    public APIResponseBean editEvents(HttpServletRequest request,@PathVariable Integer license_id,
    @RequestBody LicenseApprovalStatusBean approvalStatusBean)
    {
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
