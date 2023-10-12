package com.projet.buyback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.buyback.model.User;
import com.projet.buyback.model.security.RefreshToken;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.schema.request.security.LoginRequest;
import com.projet.buyback.schema.request.security.SignupRequest;
import com.projet.buyback.service.security.RefreshTokenService;
import com.projet.buyback.service.security.UserDetailsImpl;
import com.projet.buyback.utils.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @MockBean
    private JwtUtils jwtUtils;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        UserDetails userDetails = new UserDetailsImpl(1L, "test@example.com", "password", Collections.emptySet());

        Authentication authentication = mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(authenticationManager.authenticate(any())).thenReturn(authentication);

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        roles.add("ADMIN");
        roles.add("SUPER");

        UserDetails userDetails2 = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails2.getAuthorities().stream().map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList())).thenReturn(roles);


        User user = new User(); // Create a User object with appropriate user details
        user.setId(1L);
        user.setEmail("test@example.com");
        RefreshToken refreshToken = new RefreshToken(); // Create RefreshToken with User
        refreshToken.setToken("token");
        refreshToken.setUser(user);
        Mockito.when(refreshTokenService.createRefreshToken(any())).thenReturn(refreshToken);


        Mockito.when(userRepository.findById(Mockito.anyLong()))
            .thenReturn(java.util.Optional.of(new User("John", "Doe", "test@example.com", "password")));
        Mockito.when(jwtUtils.generateJwtToken(any()))
            .thenReturn("fakeToken");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/login")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("fakeToken"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setFirstname("John");
        signUpRequest.setLastname("Doe");
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setConfirmPassword("password");

        Set<String> roles = new HashSet<>();
        roles.add("USER");
        signUpRequest.setRole(roles);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/register")
                .content(objectMapper.writeValueAsString(signUpRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
