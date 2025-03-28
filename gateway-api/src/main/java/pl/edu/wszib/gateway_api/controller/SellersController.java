package pl.edu.wszib.gateway_api.controller;

import feign.FeignException;
import jakarta.ws.rs.GET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.wszib.gateway_api.client.AccountClient;
import pl.edu.wszib.gateway_api.client.CampaignClient;
import pl.edu.wszib.gateway_api.dto.*;
import pl.edu.wszib.gateway_api.service.AccountClientService;

import java.util.List;
import java.util.UUID;

@Controller
public class SellersController {

    private final AccountClientService accountClient;
    private final CampaignClient campaignClient;

    public SellersController(AccountClientService accountClientService, CampaignClient campaignClient) {
        this.accountClient = accountClientService;
        this.campaignClient = campaignClient;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("title", "Sellers Managment API");
        model.addAttribute("accounts", accountClient.accounts());
        return "home";
    }

    @PostMapping
    public String handleUserSelection(@RequestParam UUID id, Model model) {
        return "redirect:/campaigns?accountId=" + id;
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/campaigns")
    public String campaigns(@RequestParam("accountId") UUID accountId, Model model) {
        AccountDTO accountDTO = accountClient.getAccountById(accountId);
        model.addAttribute("account", accountDTO);

        List<CampaignDTO> accountCampaigns = campaignClient.getAccountCampaigns(accountId);
        System.out.println(accountCampaigns);
        model.addAttribute("accountCampaigns", accountCampaigns);


        List<CampaignDTO> notAccountCampaigns = campaignClient.getNotAccountCampaigns(accountId);
        System.out.println(notAccountCampaigns);
        model.addAttribute("campaigns", notAccountCampaigns);
        return "campaigns";
    }

    @PostMapping("/campaign/change-status/{campaignId}")
    public String changeStatus(@RequestParam("accountId") UUID accountId, @PathVariable UUID campaignId, Model model) {
        campaignClient.changeStatus(campaignId);
        return "redirect:/campaigns?accountId=" + accountId;
    }

    @GetMapping("/campaign/edit/{campaignId}")
    public String editCampaign(@RequestParam("accountId") UUID accountId,
                               @PathVariable UUID campaignId,
                               Model model) {

        CampaignDTO campaign = campaignClient.getCampaignById(campaignId); // ← zakładam, że masz taką metodę
        model.addAttribute("campaign", campaign);
        model.addAttribute("accountId", accountId);
        model.addAttribute("keywords", Keywords.values());
        model.addAttribute("towns", Town.values());
        model.addAttribute("statuses", Status.values());
        return "campaign-form";
    }


    @PostMapping("/campaign/edit/{campaignId}")
    public String editCampaign(@PathVariable UUID campaignId,@RequestParam("accountId") UUID accountId,
                               @ModelAttribute CampaignDTO campaign, Model model){
        campaignClient.editCampaign(campaignId, campaign);
        return "redirect:/campaigns?accountId=" + accountId;
    }

    @GetMapping("/campaign/new")
    public String newCampaign(@RequestParam("accountId") UUID accountId,
                               Model model) {
        CampaignDTO campaign = new CampaignDTO();
        campaign.setAccountId(accountId);

        model.addAttribute("campaign", campaign);
        model.addAttribute("accountId", accountId);
        model.addAttribute("keywords", Keywords.values());
        model.addAttribute("towns", Town.values());
        model.addAttribute("statuses", Status.values());
        return "campaign-form";
    }

    @PostMapping("/campaign/new")
    public String createCampaign(@ModelAttribute CampaignDTO campaign, Model model, RedirectAttributes redirectAttributes) {
        try {
            campaignClient.newCampaign(campaign);
            return "redirect:/campaigns?accountId=" + campaign.getAccountId();
        } catch (FeignException.BadRequest ex) {
            String errorMessage = ex.contentUTF8(); // wyjątek z tekstem błędu (np. "Insufficient funds")

            redirectAttributes.addFlashAttribute("error", errorMessage);
            redirectAttributes.addFlashAttribute("campaign", campaign);
            return "redirect:/campaign/new?accountId=" + campaign.getAccountId();
        }
    }

    @PostMapping("/campaign/delete/{campaignId}")
    public String deleteCampaign(@PathVariable UUID campaignId, @RequestParam UUID accountId){
        campaignClient.deleteCampaign(campaignId);
        return "redirect:/campaigns?accountId=" + accountId;
    }
}