package flapkap.demo.controller;

import flapkap.demo.model.Product;
import flapkap.demo.model.PurchaseDto;
import flapkap.demo.model.PurchaseResponseDto;
import flapkap.demo.model.User;
import flapkap.demo.service.PurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class PurchaseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;



    @Test
    @WithMockUser(username = "testuser", roles = {"BUYER"})
    public void testProcessPurchase() throws Exception {
        // Arrange: Prepare the JSON payload for the POST request
        String jsonPayload = "[{\"productId\": 1, \"amount\": 2}, {\"productId\": 2, \"amount\": 3}]";

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/buy")
                .content(jsonPayload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalSpent").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.change").value(2));
        // Add more assertions as needed
    }
}