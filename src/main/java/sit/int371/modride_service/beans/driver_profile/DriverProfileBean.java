package sit.int371.modride_service.beans.driver_profile;


import java.util.List;

import lombok.Data;
import sit.int371.modride_service.beans.VehiclesBean;

@Data
public class DriverProfileBean {
    private LicensesBean licenseDetail;
    private List<VehiclesBean> vehicleList;



}
