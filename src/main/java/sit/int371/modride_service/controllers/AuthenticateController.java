package sit.int371.modride_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int371.modride_service.models.JwtRequest;
import sit.int371.modride_service.models.JwtResponse;
import sit.int371.modride_service.services.UserService;
import sit.int371.modride_service.utils.JwtUtility;

@RestController
@RequestMapping("/api/login")
public class AuthenticateController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("")
    public ResponseEntity authenticate(@RequestBody JwtRequest jwtRequestz) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequestz.getEmail(),
                            jwtRequestz.getPassword()
                    )
            );
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        final UserDetails userDetail = userService.loadUserByUsername(jwtRequestz.getEmail());

        final String token = jwtUtility.generateToken(userDetail);
        final String refreshToken = jwtUtility.generateRefreshToken(userDetail);
//        return new JwtResponse(token);
        return ResponseEntity.ok(new JwtResponse(token, refreshToken));
    }


}