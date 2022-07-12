package io.github.coronado.api;

import io.github.coronado.tripleapi.components.schemas.Offer;
import io.github.coronado.tripleapi.components.schemas.OfferRewardType;
import io.github.coronado.tripleapi.components.schemas.OfferType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class JavaOfferMgrIT {

    @Test
    void testCardProgramBasicCRUD() {
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
                //.scope(Client.OAuth2Scope.PARTNER_CONTENT_PROVIDERS)
                .scope(Client.OAuth2Scope.PARTNER_PORTFOLIOS)
                .build();
        OfferMgr offerMgr = new OfferMgr(partnerPublishersClient);
        OfferCreationRequest offerCreationRequest = new OfferCreationRequest(
                UUID.randomUUID().toString(),
                OfferType.CARD_LINKED,
                "USD",
                "some headline",
                OfferRewardType.PERCENTAGE,
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                new BigDecimal("9.99"),
                true,
                Set.of(new MerchantCategoryCode("1234", "A test MCC")),
                null, null, null, null, null,
                null, null, null, null,
                null, null, null, null,
                null, null);
        Offer offer = offerMgr.create(offerCreationRequest);
        System.out.println("new offer: " + offer);
    }

}
