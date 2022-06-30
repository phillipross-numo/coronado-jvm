package io.github.coronado.api;

import kotlin.test.AssertionsKt;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class JavaPublisherMgrIT {

    @Test
    void testPublishersBasicCRUD() {
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
                .scope(Client.OAuth2Scope.PARTNER_PUBLISHERS)
                .build();
        PublisherMgr publisherMgr = new PublisherMgr(partnerPublishersClient);

        Publisher newPublisher = publisherMgr.create(
                "0_812_" + Long.toString(System.currentTimeMillis()), // (OU812 didn't work :( )
                        "BenevolentFI, Ltd_" + Long.toString(System.currentTimeMillis()),
                        new Address(
                                "123 Sesame Street, 2nd Floor, New York, NY 10003, US",
                                "123 Sesame Street",
                                "2nd Floor",
                                "New York",
                                "NY",
                                "10003",
                                "US",
                                new BigDecimal("40.737139"),
                                new BigDecimal("-73.990400")
                        ),
                        new BigDecimal("0.21")
        );
        AssertionsKt.assertTrue(publisherMgr.list()
                .stream()
                .map(PublisherReference::getId)
                .anyMatch(v -> v.equals(newPublisher.getId())), "created publisher should be in list");
        Publisher publisher = publisherMgr.byId(newPublisher.getId());
        AssertionsKt.assertEquals(publisher, newPublisher, "new publisher should be equal to returned publisher");

        publisher.getAddress().setLine1(publisher.getAddress().getLine1() + "_Updated");
        publisher.setAssumedName(publisher.getAssumedName() + "_Updated");
        publisher = publisherMgr.updateWith(publisher);
        System.out.println("updated publisher:\n" + publisher);
    }
}
