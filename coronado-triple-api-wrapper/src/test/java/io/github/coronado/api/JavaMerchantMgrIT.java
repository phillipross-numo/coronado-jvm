package io.github.coronado.api;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class JavaMerchantMgrIT {

    @Test
    void testMerchantBasicCRUD() {
        String cfgNameTripleApiServiceDomain = "TRIPLE_API_SERVICE_DOMAIN";
        String cfgNameTripleApiClientId = "TRIPLE_API_CLIENT_ID";
        String cfgNameTripleApiClientSecret = "TRIPLE_API_CLIENT_SECRET";
        Map<String, String> config = new HashMap<>();
        config.put(cfgNameTripleApiServiceDomain, System.getenv(cfgNameTripleApiServiceDomain));
        config.put(cfgNameTripleApiClientId, System.getenv(cfgNameTripleApiClientId));
        config.put(cfgNameTripleApiClientSecret, System.getenv(cfgNameTripleApiClientSecret));
        Client partnerPublishersClient = new Client.Builder()
                .serviceDomain(config.get(cfgNameTripleApiServiceDomain))
                .clientId(config.get(cfgNameTripleApiClientId))
                .clientSecret(config.get(cfgNameTripleApiClientSecret))
                //.scope(Client.OAuth2Scope.PARTNER_PUBLISHERS)
                .scope(Client.OAuth2Scope.PARTNER_CONTENT_PROVIDERS)
                .build();
        MerchantMgr merchantMgr = new MerchantMgr(partnerPublishersClient);
        merchantMgr.list("").forEach(
                m -> System.out.println("merchant: " + m)
        );
    }

}
