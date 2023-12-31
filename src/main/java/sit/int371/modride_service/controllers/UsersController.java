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
import sit.int371.modride_service.repositories.OldUserRepository;
import sit.int371.modride_service.repositories.UsersRepository;
import sit.int371.modride_service.services.UserService;
import sit.int371.modride_service.utils.JwtUtility;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRepository usersRepository;

    private Integer passengerId = 1;
    private Integer driverId = 2;
    private Integer adminId = 3;

    // @Autowired
    // private JwtUtility jwtUtility;
    //
    // @Autowired
    // private AuthenticationManager authenticationManager;

    // Get all-users
    @GetMapping("/getAll")
    public APIResponseBean getAllUser(HttpServletRequest request) {
        APIResponseBean res = new APIResponseBean();
        try {
            System.out.println("getAll-users");
            List<UsersBean> usersList = usersRepository.getAllUsers();
            res.setData(usersList);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // Get user-by-id
    @GetMapping("/get/{user_id}")
    public APIResponseBean getUserById(HttpServletRequest request, @PathVariable Integer user_id) {
        APIResponseBean res = new APIResponseBean();
        try {
            UsersBean userBean = new UsersBean();
            userBean.setUser_id(user_id);
            UsersBean user = usersRepository.getUserById(userBean);
            res.setData(user);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // Get user-by-email (เป็นการให้ login เพื่อเข้าใช้แบบ mockup เฉยๆ)
    @GetMapping("/getByEmail")
    public APIResponseBean getUserByEmail(HttpServletRequest request,
            @RequestParam(name = "email", required = false) String email) {
        APIResponseBean res = new APIResponseBean();
        try {
            UsersBean usersBean = new UsersBean();
            usersBean.setEmail(email);
            UsersBean user = usersRepository.getUserByEmail(usersBean);
            res.setData(user);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // Get faculties
    @GetMapping("/getFaculties")
    public APIResponseBean getFaculties(HttpServletRequest request) {
        APIResponseBean res = new APIResponseBean();
        try {
            List<FacultiesBean> faculties = usersRepository.getFaculties();
            res.setData(faculties);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // Get branches
    @GetMapping("/getBranches")
    public APIResponseBean getBranches(HttpServletRequest request) {
        APIResponseBean res = new APIResponseBean();
        try {
            List<BranchesBean> branches = usersRepository.getBranches();
            res.setData(branches);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // createAccount
    @PostMapping("/sign-up")
    public APIResponseBean createAccount(HttpServletRequest request, @RequestBody UsersBean usersBean) {
        APIResponseBean res = new APIResponseBean();
        try {
            usersBean.setRole_id(passengerId);
            usersRepository.createAccount(usersBean);
            UsersBean userDetail = usersRepository.getUserById(usersBean);
            res.setData(userDetail);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // updateAccount
    @PutMapping("/update")
    public APIResponseBean updateUserAccount(HttpServletRequest request, @RequestBody UsersBean usersBean) {
        APIResponseBean res = new APIResponseBean();
        try {
            usersRepository.updateUserAccount(usersBean);
            res.setData(usersBean);
        } catch (Exception e) {
            this.checkException(e, res);
        }
        return res;
    }

    // edit user

    // delete-user

    // ของเก่า
    // แต่เก็บไว้ก่อน***************************************************************************************

    @PutMapping("/change-password")
    public User forgotPassword(@Valid @RequestBody ChangeDTO changeDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.changePassword(email, changeDTO);
    }

    @PutMapping("/forgot")
    public User forgot(@Valid @RequestBody NewPasswordDTO newPasswordDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        // System.out.println("email : " + email);
        // System.out.println("newPasswordDTO : " + newPasswordDTO);
        // User updateUserDetails = repository.findByEmail(email);
        // //encrypt password with argon2 before save to database
        // if (newPasswordDTO.getPassword().length() < 8 ||
        // newPasswordDTO.getPassword().length() > 14) {
        // System.out.println("invalid number of password : " +
        // newPasswordDTO.getPassword().length());
        //// return new ResponseEntity("The password must be between 8 and 14 characters
        // long", HttpStatus.BAD_REQUEST);
        // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password must
        // be between 8 and 14 characters long");
        // }
        // System.out.println("valid number of password (8-14): " +
        // newPasswordDTO.getPassword() + " --> (" +
        // newPasswordDTO.getPassword().length() + ")");
        // Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 16,
        // 16);
        // String hash = argon2.hash(2, 16, 1, newPasswordDTO.getPassword());
        // newPasswordDTO.setPassword(hash);
        // updateUserDetails.setPassword(newPasswordDTO.getPassword());
        // return repository.saveAndFlush(updateUserDetails);

        return userService.forgotPassword(email, newPasswordDTO);
    }

    @PostMapping("/mailForgot")
    public SendMailDTO mailForgot(@RequestBody SendMailDTO SendMailDTO) {
        System.out.println("การทำงาน mailForgot()");
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        System.out.println("user role: " + role);
        // find user in users entity
        String email = SendMailDTO.getEmail();
        // User getUser = repository.findByEmail(email);
        // System.out.println(getUser);
        // if (getUser == null) {
        // throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found
        // please try again");
        // }

        userService.sendMail(SendMailDTO);
        throw new ResponseStatusException(HttpStatus.OK, "Send email complete");
    }

}