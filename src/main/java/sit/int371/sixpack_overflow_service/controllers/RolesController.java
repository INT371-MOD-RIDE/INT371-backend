@RestController
@RequestMapping(value = "/api/roles")
public class UserController {
    @Autowired
    private UserService userService;
    private final UserRepository repository;

    // @Autowired
    // private JwtUtility jwtUtility;
    //
    // @Autowired
    // private AuthenticationManager authenticationManager;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    // Get all-users
    @GetMapping("/getAll")
    public List<UserDTO> getAllRoles(HttpServletRequest request) {
        
        return userService.getAllUserByDTO();
        
    }

}