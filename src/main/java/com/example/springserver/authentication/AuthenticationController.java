package com.example.springserver.authentication;


import com.example.springserver.authentication.dto.LoginRequest;
import com.example.springserver.authentication.dto.LoginResponse;
import com.example.springserver.authentication.dto.UserDto;
import com.example.springserver.authentication.jwt.JwtService;
import com.example.springserver.user.User;
import com.example.springserver.user.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResponse loginResponse;
        try {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            String accessToken = jwtService.getAccessToken(userDetails);
            String refreshToken = jwtService.getRefreshToken(userDetails);

            Cookie cookie = new Cookie("REFRESH_TOKEN", refreshToken);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            response.addCookie(cookie);


            User user = userRepository.getUsersBySchoolEmail(username);

            assert user != null;
            UserDto userDto =  UserDto.builder()
                    .regNo(user.getRegNo())
                    .userId(user.getUserId())
                    .schoolEmail(user.getSchoolEmail())
                    .roles(user.getRoles())
                    .annonumousName(user.getAnnonumousName())
                    .courses(user.getCourses())
                    .build();

            loginResponse =  LoginResponse.builder()
                    .accessToken(accessToken)
                    .user(userDto)
                    .message("success")
                    .build();

           return  ResponseEntity.ok(loginResponse);


        }catch (AuthenticationException e) {
            log.error("Login failed for user {}", loginRequest.getUsername(), e);


            LoginResponse errorResponse = LoginResponse.builder()
                    .message("Invalid username or password")
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("Refresh Token endpoint called");
            logger.info("Refresh Token endpoint called");
            // Extract refresh token from cookies
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No refresh token provided");
            }

            String refreshToken = Arrays.stream(cookies)
                    .filter(cookie -> "REFRESH_TOKEN".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);

            String username = jwtService.extractUserName(refreshToken);

            if (refreshToken == null || !jwtService.isTokenValid(refreshToken, username)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
            }

            // Extract username from refresh token and generate a new access token
            String newAccessToken = jwtService.getAccessToken(customUserDetailsService.loadUserByUsername(username));

            System.out.println("Access Token Has been refreshed successfully");
            // Return new access token
            return ResponseEntity.ok(LoginResponse.builder()
                    .accessToken(newAccessToken)
                    .message("Token refreshed successfully")
                    .build());

        } catch (Exception e) {
            log.error("Error refreshing token", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not refresh token");
        }
    }
}
