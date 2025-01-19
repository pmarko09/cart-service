package com.pmarko09.cart_service.retry;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.pmarko09.cart_service.model.dto.CartDto;
import com.pmarko09.cart_service.model.entity.Cart;
import com.pmarko09.cart_service.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import org.junit.jupiter.api.Assertions;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8069)
public class RetryTest {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CartRepository cartRepository;

    @AfterEach
    public void tearDown() {
        wireMockServer.resetAll();
    }

    @Test
    void onceError500TriggerRetry() {
        log.info("Test start: onceError500TriggerRetry");
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotalPrice(0.0);
        cart.setCreatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        wireMockServer.stubFor(WireMock.get("/products/id/1")
                .willReturn(WireMock.aResponse()
                        .withStatus(500)
                        .withHeader(HttpHeaders.RETRY_AFTER, "3")));

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8060")
                .path("/cart/1/addProduct/1")
                .queryParam("quantity", 1)
                .toUriString();

        Assertions.assertThrows(Exception.class,
                () -> restTemplate.postForEntity(url, null, CartDto.class));

        wireMockServer.verify(3, getRequestedFor(urlEqualTo("/products/id/1")));
        log.info("Test finish: onceError500TriggerRetry");
    }
}