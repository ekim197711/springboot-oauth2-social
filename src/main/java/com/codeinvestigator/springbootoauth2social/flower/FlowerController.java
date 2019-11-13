package com.codeinvestigator.springbootoauth2social.flower;

import com.codeinvestigator.springbootoauth2social.userdetails.MyUserDetails;
import com.codeinvestigator.springbootoauth2social.userdetails.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/flower")
@RequiredArgsConstructor
@Slf4j
public class FlowerController {
    private final UserDetailsService userDetailsService;

    List<Flower> flowerlist = List.of(
            new Flower("Rose", 10, BigDecimal.valueOf(1234.65)),
            new Flower("Bush", 3, BigDecimal.valueOf(444.625)),
            new Flower("Tree", 5, BigDecimal.valueOf(333.665)),
            new Flower("Tullip", 15, BigDecimal.valueOf(12334.65))
    );

    @GetMapping("/")
    @SneakyThrows
    public String flowerShop(Model model, OAuth2AuthenticationToken token){
        model.addAttribute("title", "Flower Shop");
        MyUserDetails userDetails = userDetailsService.getUserDetails(token);
        model.addAttribute("picture", userDetails.getPicture());
        model.addAttribute("fullname", userDetails.getName());
        model.addAttribute("email", userDetails.getEmail());
        model.addAttribute("flowers", flowerlist);
        log.info("Userdetails: \n{}", userDetails);
        return "flower";
    }

}
