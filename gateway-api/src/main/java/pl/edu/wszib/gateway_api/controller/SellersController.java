package pl.edu.wszib.gateway_api.controller;

import jakarta.ws.rs.GET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.gateway_api.client.AccountClient;
import pl.edu.wszib.gateway_api.client.CampaignClient;
import pl.edu.wszib.gateway_api.dto.AccountDTO;
import pl.edu.wszib.gateway_api.service.AccountClientService;

import java.util.UUID;

@Controller
public class SellersController {

    private final AccountClientService accountClient;
    private final CampaignClient campaignClient;

    public SellersController(AccountClientService accountClientService, CampaignClient campaignClient) {
        this.accountClient  = accountClientService;
        this.campaignClient = campaignClient;
    }

    @GetMapping
    public String home(Model model){
        model.addAttribute("title", "Sellers Managment API");
        model.addAttribute("accounts", accountClient.accounts());
        return "home";
    }

    @PostMapping
    public String handleUserSelection(@RequestParam UUID id, Model model){
//        AccountDTO accountDTO = accountClient.getAccountById(id)

        return "redirect:/campaigns?accountId=" + id;
    }



    @GetMapping("/campaigns")
    public String campaigns(@RequestParam("accountId") UUID accountId, Model model){
        AccountDTO accountDTO = accountClient.getAccountById(accountId);
        model.addAttribute("account", accountDTO);
        model.addAttribute("campaigns", campaignClient.getAllCampaigns());
        return "campaigns";
    }


}
