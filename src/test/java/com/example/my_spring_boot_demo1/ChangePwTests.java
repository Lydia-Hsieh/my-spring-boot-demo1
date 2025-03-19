package com.example.my_spring_boot_demo1;

import com.example.my_spring_boot_demo1.controller.changePwController.pojo.ChangePwRequest;
import com.example.my_spring_boot_demo1.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
public class ChangePwTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 成功
     * @throws Exception
     */
    @Test
    @Transactional
    public void testChangePw_success() throws Exception {
        ChangePwRequest request = new ChangePwRequest();
        String userId = "user_1";
        request.setUserId(userId);
        request.setPwToChange("User#111");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/change-password")
                .contentType("application/json")
                .header("Authorization", "Bearer " + JwtUtil.generateToken(userId))
                .content(objectMapper.writeValueAsString(request));

        mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 帳戶不存在
     * @throws Exception
     */
    @Test
    public void testChangePw_accountNotExists() throws Exception {
        ChangePwRequest request = new ChangePwRequest();
        String userId = "user1";
        request.setUserId(userId);
        request.setPwToChange("User#111");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/change-password")
                .contentType("application/json")
                .header("Authorization", "Bearer " + JwtUtil.generateToken(userId))
                .content(objectMapper.writeValueAsString(request));

        mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * 密碼不符合規則
     * @throws Exception
     */
    @Test
    public void testChangePw_pwInvalid() throws Exception {
        ChangePwRequest request = new ChangePwRequest();
        String userId = "user_1";
        request.setUserId(userId);
        request.setPwToChange("111");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/change-password")
                .contentType("application/json")
                .header("Authorization", "Bearer " + JwtUtil.generateToken(userId))
                .content(objectMapper.writeValueAsString(request));

        mockMvc
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
