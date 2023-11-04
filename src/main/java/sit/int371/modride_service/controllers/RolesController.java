package sit.int371.modride_service.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sit.int371.modride_service.beans.APIResponseBean;
import sit.int371.modride_service.beans.RolesBean;
import sit.int371.modride_service.repositories.RolesRepository;

@RestController
@RequestMapping(value = "/api/roles")
public class RolesController extends BaseController{
    @Autowired
    private RolesRepository rolesRepository;

    // Get all-roles
    @GetMapping("/getAll")
    public APIResponseBean getAllRoles(HttpServletRequest request) {
        APIResponseBean res = new APIResponseBean();
        try {
            List<RolesBean> rolesList = rolesRepository.getAllRoles();
            res.setData(rolesList);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

}
