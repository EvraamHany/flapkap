package flapkap.demo.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;



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
        String jsonPayload = "[{\"productId\": 1, \"amount\": 2}, {\"productId\": 2, \"amount\": 3}]";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/buy")
                .content(jsonPayload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalSpent").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.change").value(2));
    }
}