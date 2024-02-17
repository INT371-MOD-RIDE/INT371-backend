package sit.int371.modride_service.repositories;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import sit.int371.modride_service.beans.EventDetailBean;
import sit.int371.modride_service.beans.EventMemberBean;
import sit.int371.modride_service.beans.VehiclesBean;
import sit.int371.modride_service.beans.driver_profile.DriverProfileBean;
import sit.int371.modride_service.beans.driver_profile.LicensesBean;

@Mapper
public interface DriverRegisterRepository {

        // driver-profile
        @Select({
                        " select l.user_id,l.license_id,l.license_fn,l.license_ln ",
                        " ,lf.license_file_name,lf.license_download,lf.license_size ",
                        " ,ls.approval_status,ls.denied_detail,ls.timestamp ",
                        " from licenses l  ",
                        " inner join users u on l.user_id = u.user_id ",
                        " inner join license_files lf on l.license_id = lf.license_id ",
                        " inner join license_approval_status ls on l.license_id = ls.license_id ",
                        " where l.user_id = #{user_id} ",
        })
        public LicensesBean getLicenseDetail(Integer user_id) throws Exception;

        @Select({
                        " select v.license_id,v.vehicle_id,v.brand,v.model ",
                        " ,v.vehicle_type,v.vehicle_color,v.seats,v.create_date,v.license_plate ",
                        " ,vf.vehicle_file_name,vf.vehicle_download ",
                        " from vehicles v ",
                        " inner join licenses l on v.license_id = l.license_id ",
                        " inner join vehicle_files vf on v.vehicle_id = vf.vehicle_id ",
                        " where l.user_id = #{user_id} ",
        })
        public List<VehiclesBean> getVehicleList(Integer user_id) throws Exception;

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
        public void updateLicenseAppStatus(LicensesBean bean) throws Exception;

        // 🚕
        @Insert({
                        " insert into vehicles (license_id,brand,model,vehicle_type,vehicle_color,license_plate,seats,create_date) ",
                        " values (#{license_id},#{brand},#{model},#{vehicle_type},#{vehicle_color},#{license_plate},#{seats},sysdate()) ",
        })
        @Options(useGeneratedKeys = true, keyColumn = "vehicle_id", keyProperty = "vehicle_id")
        public void insertVehicle(VehiclesBean vehiclesBean) throws Exception;
}
