package com.oauth.salesforce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OAuthLogin {

    @Autowired
    private LoginConfigure loginConfigure;

    @GetMapping("/login")
    @ResponseBody
    public String login() {
        String result = "";
        ResponseEntity<String> responseEntity;
        try {
            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> httpMessageConverter = new ArrayList<>();
            httpMessageConverter.add(
                    new StringHttpMessageConverter(StandardCharsets.UTF_8)
            );
            restTemplate.setMessageConverters(httpMessageConverter);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(loginConfigure.getEndpoint())
                    .queryParam("response_type", "token")
                    .queryParam("client_id", loginConfigure.getClientId())
                    .queryParam("redirect_uri", loginConfigure.getRedirectUri());
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            responseEntity = restTemplate.exchange(
                    builder.toUriString(), HttpMethod.GET, entity, String.class
            );
            result = responseEntity.getBody();
        } catch (HttpClientErrorException httpClientErrorException) {
            result = httpClientErrorException.getMessage();
        }
        return result;
    }

    @GetMapping("/_callback")
    @ResponseBody
    public String callback(@RequestParam Map<String, String> queryMap) {
        return queryMap.toString();
    }
}
