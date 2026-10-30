import org.springframework.stereotype.Service;

import com.ims.users.dto.*;
import com.ims.users.entity.User;
import com.ims.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
            .findByUsername(request.username())
            .orElseThrow();

        if(!encoder.matches(
                request.password(),
                user.getPasswordHash())) {

            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);

        LoginResponse response = new LoginResponse(
        		token,
        		user.getUsername(),
        		user.getRole()
        	);

        return response;
    }
}