package com.nadeem.changejar.kiranaregister.service.auth;

import com.nadeem.changejar.kiranaregister.dto.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CurrencyService
{

    private final WebClient webClient;
    private final StringRedisTemplate redisTemplate;

    private static final String hashKeyPrefix = "fx:rates:";
    private static final Duration RATE_TTL = Duration.ofHours(1);

    public CurrencyService(WebClient.Builder webClientBuilder,
                         StringRedisTemplate redisTemplate) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.fxratesapi.com")
                .build();
        this.redisTemplate = redisTemplate;
    }

    public BigDecimal getRate(String base, String target) {
        String hashKey = hashKeyPrefix + base;

        Object cachedValue = redisTemplate.opsForHash().get(hashKey, target);
        if (cachedValue != null) {
            log.debug("Cache HIT for {}:{}", base, target);
            return new BigDecimal(cachedValue.toString());
        }

        log.info("Cache MISS for {}:{}, fetching from API...", base, target);
        Map<String, BigDecimal> rates = fetchAndCacheRates(base);

        return rates.get(target);
    }

    private Map<String, BigDecimal> fetchAndCacheRates(String base) {
        ApiResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest")
                        .queryParam("base", base)
                        .build())
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block();

        String hashKey = hashKeyPrefix + base;
        Map<String, String> rateEntries = new HashMap<>();
        response.getRates().forEach((currency, rate) ->
                rateEntries.put(currency, rate.toPlainString()));

        redisTemplate.opsForHash().putAll(hashKey, rateEntries);
        redisTemplate.expire(hashKey, RATE_TTL);

        log.info("Cached {} rates under key {}", rateEntries.size(), hashKey);

        return response.getRates();
    }
}