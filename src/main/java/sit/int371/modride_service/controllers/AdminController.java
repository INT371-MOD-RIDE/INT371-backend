package sit.int371.modride_service.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import sit.int371.modride_service.beans.APIResponseBean;
import sit.int371.modride_service.beans.BranchesBean;
import sit.int371.modride_service.beans.FacultiesBean;
import sit.int371.modride_service.beans.RolesBean;
import sit.int371.modride_service.beans.UsersBean;
//import sit.int371.modride_service.dtos.NewUserDTO;
import sit.int371.modride_service.dtos.*;
import sit.int371.modride_service.entities.User;
import sit.int371.modride_service.repositories.AdminRepository;
import sit.int371.modride_service.repositories.OldUserRepository;
import sit.int371.modride_service.repositories.UsersRepository;
import sit.int371.modride_service.services.UserService;
import sit.int371.modride_service.utils.JwtUtility;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController extends BaseController {
    @Autowired
    private AdminRepository adminRepository;

    private Integer passengerId = 1;
    private Integer driverId = 2;
    private Integer adminId = 3;

    // Get all-users
    @GetMapping("/getAllUser")
    public APIResponseBean getAllUser(HttpServletRequest request) {
        APIResponseBean res = new APIResponseBean();
        try {
            System.out.println("getAll-users");
            List<UsersBean> usersList = adminRepository.getAllUsers();
            res.setData(usersList);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    @GetMapping("/adminLogin")
    public APIResponseBean adminLogin(HttpServletRequest request,
            @RequestParam(name = "fullname", required = false) String fullname,
            @RequestParam(name = "password", required = false) String password
            ) {
        APIResponseBean res = new APIResponseBean();
        try {
            // UsersBean usersBean = new UsersBean();
            // usersBean.setFullname(fullname);
            HashMap<String,String> userParam = new HashMap<>();
            userParam.put("fullname", fullname);
            userParam.put("password", password);
            UsersBean user = adminRepository.getAdminUser(userParam);
            res.setData(user);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }
}