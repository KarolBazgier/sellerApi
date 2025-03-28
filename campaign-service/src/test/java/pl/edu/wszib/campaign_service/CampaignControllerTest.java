package pl.edu.wszib.campaign_service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.wszib.campaign_service.client.AccountClient;
import pl.edu.wszib.campaign_service.repository.CampaignRepository;
import pl.edu.wszib.dto.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8091)
@AutoConfigureMockMvc
public class CampaignControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CampaignRepository campaignRepository;

    @MockBean
    private AccountClient accountClient;

    private UUID account_id = UUID.fromString("11111111-1111-1111-1111-222222222222");


    @Test
    void shouldCreateCampaign() throws Exception {
        CreateCampaignRequest createCampaignRequest = new CreateCampaignRequest();
        createCampaignRequest.setAccountId(account_id);
        createCampaignRequest.setCampaignName("SPORT = HEALTH");
        createCampaignRequest.setCampaignFund(new BigDecimal("1500.00"));
        createCampaignRequest.setTown(Town.KRAKÓW);
        createCampaignRequest.setStatus(Status.ON);
        createCampaignRequest.setBidAmount(new BigDecimal("1.00"));
        List<Keywords> keywords = new ArrayList<>();
        keywords.add(Keywords.SPORT);
        keywords.add(Keywords.FOOTBALL);
        keywords.add(Keywords.FISHING);
        createCampaignRequest.setKeywords(keywords);

        mockMvc.perform(post("/campaign/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCampaignRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.campaignName").value("SPORT = HEALTH"))
                .andExpect(jsonPath("$.accountId").value(account_id.toString()))
                .andExpect(jsonPath("$.status").value("ON"))
                .andExpect(jsonPath("$.town").value("KRAKÓW"))
                .andExpect(jsonPath("$.keywords").isArray())
                .andExpect(jsonPath("$.keywords.length()").value(3));
    }

    @Test
    void shouldNotCreateCampaignInvalidRequest() throws Exception {
        CreateCampaignRequest createCampaignRequest = new CreateCampaignRequest();
        createCampaignRequest.setAccountId(account_id);
        createCampaignRequest.setCampaignName("SPORT = HEALTH");
        createCampaignRequest.setCampaignFund(new BigDecimal("1500.00"));
        createCampaignRequest.setRadiusKM(100);
        createCampaignRequest.setBidAmount(new BigDecimal("1.00"));
        List<Keywords> keywords = new ArrayList<>();
        keywords.add(Keywords.SPORT);
        keywords.add(Keywords.FOOTBALL);
        keywords.add(Keywords.FISHING);
        createCampaignRequest.setKeywords(keywords);


        mockMvc.perform(post("/campaign/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCampaignRequest)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldNotCreateCampaignInsufficientFunds() throws Exception{
        CreateCampaignRequest createCampaignRequest = new CreateCampaignRequest();
        createCampaignRequest.setAccountId(account_id);
        createCampaignRequest.setCampaignName("SPORT = HEALTH");
        createCampaignRequest.setCampaignFund(new BigDecimal("9900.00"));
        createCampaignRequest.setTown(Town.KRAKÓW);
        createCampaignRequest.setStatus(Status.ON);
        createCampaignRequest.setRadiusKM(100);
        createCampaignRequest.setBidAmount(new BigDecimal("1.00"));
        List<Keywords> keywords = new ArrayList<>();
        keywords.add(Keywords.SPORT);
        keywords.add(Keywords.FOOTBALL);
        keywords.add(Keywords.FISHING);
        createCampaignRequest.setKeywords(keywords);


        Mockito.doThrow(new IllegalArgumentException("Insufficient funds on account: " + account_id.toString()))
                        .when(accountClient)
                                .deductFunds(Mockito.eq(account_id), Mockito.any(DeductRequest.class));

        mockMvc.perform(post("/campaign/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCampaignRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient funds on account: " + account_id.toString()));
    }


}