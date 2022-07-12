package io.github.coronado.api;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class JavaOfferDisplayIT {

    @Test
    void testOfferDisplaySearch() {
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
                //.scope(Client.OAuth2Scope.PARTNER_PUBLISHERS) // permission errors with this scope
                //.scope(Client.OAuth2Scope.PARTNER_PORTFOLIOS) // permission errors with this scope
                //.scope(Client.OAuth2Scope.PARTNER_CONTENT_PROVIDERS) // permission errors with this scope
                .scope(Client.OAuth2Scope.PARTNER_VIEW_OFFERS) // permission errors with this scope
                .build();
        OfferDisplay offerDisplay = new OfferDisplay(partnerPublishersClient);
        GeoTarget geoTarget = new GeoTarget("15213", "US", 30000);
        CardAccountIdentifier cardAccountIdentifier = new CardAccountIdentifier(null, UUID.randomUUID().toString(), null, null);
        offerDisplay.search(geoTarget, cardAccountIdentifier, "italian", null, null);
        
    }

}
