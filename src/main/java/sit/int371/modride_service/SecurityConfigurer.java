package sit.int371.modride_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import sit.int371.modride_service.exceptions.CustomAuthenticationFailureHandler;
import sit.int371.modride_service.entities.User;
import sit.int371.modride_service.filters.JwtFilter;
import sit.int371.modride_service.repositories.OldUserRepository;
import sit.int371.modride_service.services.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private OldUserRepository OldUserRepository;

    @Autowired
    private UserService userService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    // PasswordEncoder
    // NoOpPasswordEncoder.getInstance()

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // ใช้ try-catch ดักได้
    // @Bean
    // public AuthenticationFailureHandler authenticationFailureHandler(){
    // return new CustomAuthenticationFailureHandler();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                // friends
                .antMatchers(HttpMethod.GET, "/api/v1/friends/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/friends/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/friends/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/friends/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/v1/friends/**").permitAll()
                // users
                .antMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "http://capstone23.sit.kmutt.ac.th/un2/api/v1/users/getBranches").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/getBranches").permitAll()
                .antMatchers(HttpMethod.GET, "/un2/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/un2/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/un2/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/un2/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/un2/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/v1/users/getBranches").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "http://capstone23.sit.kmutt.ac.th/un2/api/v1/users/getBranches").permitAll()

                // events
                .antMatchers(HttpMethod.GET, "/api/v1/events/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/events/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/events/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/events/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/v1/events/**").permitAll()

                // public endpoints
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/").permitAll()
                // ทดสอบ roles
                .antMatchers(HttpMethod.GET, "/api/roles/**").permitAll()

                // filter menu
                .antMatchers(HttpMethod.GET, "/api/events/getByEventCategories/{eventCategoryId}")
                .hasAnyAuthority("admin", "lecturer", "student")
                .antMatchers(HttpMethod.GET, "/api/events/getEventByUpcoming")
                .hasAnyAuthority("admin", "lecturer", "student")
                .antMatchers(HttpMethod.GET, "/api/events/getEventByPast")
                .hasAnyAuthority("admin", "lecturer", "student")
                .antMatchers(HttpMethod.GET, "/api/events/getEventsByEventStartTime/{eventStartTime}")
                .hasAnyAuthority("admin", "lecturer", "student")

                // .antMatchers("/api/events/**").permitAll()
                .antMatchers("/api/match").hasAuthority("admin")
                // ใช้ได้เฉพาะมี token ถึงจะเข้า /users ได้
                // .antMatchers(HttpMethod.GET,"/api/users").permitAll()

                // permit all ให้หมด เพื่อรับมือ azure-token
                .antMatchers(HttpMethod.POST, "/api/events").permitAll()
                .antMatchers(HttpMethod.GET, "/api/events/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/events/{bookingId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/events/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/files/upload").permitAll()
                .antMatchers(HttpMethod.GET, "/api/files/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/files/download/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/files/update/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/files/delete/**").permitAll()
                // forgot-password
                // .antMatchers(HttpMethod.POST,"/api/users/mailForgot").hasAnyAuthority("admin","student","lecturer")
                // /mailForgot ต้อง permitAll() เพราะขณะที่ user จะเข้า function
                // "forgot-password" user จะยังเป็น guest อยู่แล้ว
                .antMatchers(HttpMethod.POST, "/api/users/mailForgot").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users/forgot").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users/change-password").permitAll()
                // send email
                .antMatchers(HttpMethod.POST, "/api/email/sendMail").permitAll()
                // user-management
                .antMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/users/**").permitAll()


                // privilege endpoint
                // for post-user by admin
                // .antMatchers(HttpMethod.POST,"/api/users").hasAuthority("admin")
                // .antMatchers(HttpMethod.POST,"/api/users/**").hasAuthority("admin")
                // .antMatchers("/api/users").hasAuthority("admin")
                // admin สามารถจัดการ category ได้
                .antMatchers("/api/event-categories/").access("hasAuthority('admin')")
                .antMatchers(HttpMethod.PUT, "/api/event-categories/{id}").hasAuthority("admin")
                // lecturer สามารถดู detail event-categories (ที่ตนรับผิดชอบเท่านั้น)
                .antMatchers(HttpMethod.GET, "/api/event-categories/{id}").hasAuthority("lecturer")
                .antMatchers(HttpMethod.POST, "/api/refresh").hasAnyAuthority("admin", "lecturer", "student")

                // admin สามารถจัดการ event ได้
                // .antMatchers("/api/events/").access("hasAuthority('admin')")

                // admin สามารถ get user และ match passowrd ได้
                // .antMatchers(HttpMethod.GET,"/api/users").hasAnyAuthority("admin","lecturer","student")
                // .antMatchers(HttpMethod.GET,"/api/users").hasAnyAuthority("admin","student","lecturer")
                .antMatchers(HttpMethod.POST, "/api/match").hasAuthority("admin")

                // lecturer สามารถ get event ที่ category ของตัวเองได้
                // .antMatchers(HttpMethod.GET,"/api/events/{id}").hasAnyAuthority("admin","lecturer")

                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                // บังคับให้ request ที่ไม่ได้ authen เป็น 401 ให้หมด (เดิมเป็น 403)
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }
}