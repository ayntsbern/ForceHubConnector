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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(), HttpMethod.GET, entity, String.class
            );
            // return page with Salesforce authorization
            return responseEntity.getBody();
        } catch (HttpClientErrorException httpClientErrorException) {
            return httpClientErrorException.getMessage();
        }
    }

    @GetMapping("/_callback")
    @ResponseBody
    public void callback(HttpServletResponse response, HttpServletRequest request) throws IOException {
        //response.setHeader("s", "s1");
        response.sendRedirect("/callback");
    }
}
