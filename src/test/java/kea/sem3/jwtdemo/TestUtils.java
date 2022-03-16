package kea.sem3.jwtdemo;


import com.fasterxml.jackson.databind.ObjectMapper;
import kea.sem3.jwtdemo.entity.BaseUser;
import kea.sem3.jwtdemo.entity.Member;
import kea.sem3.jwtdemo.entity.Role;
import kea.sem3.jwtdemo.security.UserDetailsImp;
import kea.sem3.jwtdemo.security.dto.LoginRequest;
import kea.sem3.jwtdemo.security.dto.LoginResponse;
import kea.sem3.jwtdemo.security.jwt.JwtTokenUtil;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TestUtils {
    public static String login(String username, String pw, MockMvc mockMvc) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult response = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new LoginRequest(username, pw))))
                .andReturn();
        return objectMapper.readValue(response.getResponse().getContentAsString(), LoginResponse.class).getToken();
    }
}
