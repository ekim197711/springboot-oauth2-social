package com.codeinvestigator.springbootoauth2social.flower;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/flower")
@RequiredArgsConstructor
@Slf4j
public class FlowerController {
    private final OAuth2AuthorizedClientService authorizedClientService;

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
        OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient(
                        token.getAuthorizedClientRegistrationId(), token.getPrincipal().getName());
        URI userDetailsEndpoint = URI.create(
                client.getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUri()
        );

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", client.getAccessToken().getTokenValue()));
        RequestEntity<String> request = new RequestEntity<>("", headers, HttpMethod.GET, userDetailsEndpoint);
        ResponseEntity<Map> response = restTemplate.exchange(request, Map.class);
        Map details = response.getBody();
        model.addAttribute("userdetails", new ObjectMapper().writeValueAsString(details));
        model.addAttribute("picture", details.get("picture"));
        model.addAttribute("fullname", details.get("name"));
        model.addAttribute("email", details.get("email"));

        log.info("Response: \n{}", details);


        return "flower";
    }

}
