package sit.int371.modride_service.repositories;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import sit.int371.modride_service.beans.EventDetailBean;
import sit.int371.modride_service.beans.VehiclesBean;
import sit.int371.modride_service.beans.licenses.LicenseApprovalStatusBean;
import sit.int371.modride_service.beans.licenses.LicensesBean;

@Mapper
public interface DriverRegisterRepository {
    // 🪪
    @Insert({
            " insert into licenses(user_id,license_fn,license_ln)",
            " values(#{user_id},#{license_fn},#{license_ln}); ",
    })
    @Options(useGeneratedKeys = true, keyColumn = "license_id", keyProperty = "license_id")
    public void insertLicense(LicensesBean licensesBean) throws Exception;

    @Insert({
            " insert into license_approval_status(license_id,approval_status,timestamp) ",
            " values(#{license_id},0,sysdate()); ",
    })
    public void createLicenseAppStatus(Integer license_id) throws Exception;

    @Update({
        " update license_approval_status ",
        " set approval_status = #{approval_status}, ",
        " denied_detail = #{denied_detail}, ",
        " timestamp = sysdate() ",
        " where license_id = #{license_id} ",
    })
    public void updateLicenseAppStatus(LicenseApprovalStatusBean bean) throws Exception;

    // 🚕
    @Insert({
            " insert into vehicles (license_id,brand,model,vehicle_type,vehicle_color,license_plate,seats,create_date) ",
            " values (#{license_id},#{brand},#{model},#{vehicle_type},#{vehicle_color},#{license_plate},#{seats},sysdate()) ",
    })
    @Options(useGeneratedKeys = true, keyColumn = "vehicle_id", keyProperty = "vehicle_id")
    public void insertVehicle(VehiclesBean vehiclesBean) throws Exception;
}
