package io.github.coronado.api;

import kotlin.test.AssertionsKt;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class JavaCardProgramMgrIT {

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
                .scope(Client.OAuth2Scope.PARTNER_PUBLISHERS)
                .build();
        PublisherMgr publisherMgr = new PublisherMgr(partnerPublishersClient);
        CardProgramMgr cardProgramMgr = new CardProgramMgr(partnerPublishersClient);

        Publisher newPublisher = publisherMgr.create(
                "0_812_" + System.currentTimeMillis(), // (OU812 didn't work :( )
                "BenevolentFI, Ltd_" + System.currentTimeMillis(),
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
        //CardProgram newCardProgram1 = cardProgramMgr.create("1_812_" + System.currentTimeMillis(), "1_812_CPname" + System.currentTimeMillis(), "USD");
        //System.out.println("new card program:\n" + newCardProgram1);

        CardProgram newCardProgram = cardProgramMgr.create(
                "2_812_" + System.currentTimeMillis(),
                "2_812_CPname" + System.currentTimeMillis(),
                "USD",
                newPublisher.getExternalId(), List.of("123456", "654321")
        );
        System.out.println("new card program:\n" + newCardProgram);
        AssertionsKt.assertTrue(cardProgramMgr.list().stream()
                .map(CardProgramReference::getId)
                .anyMatch(v -> v.equals(newCardProgram.getId())), "created card program should be in list");
        CardProgram cardProgram = cardProgramMgr.byId(newCardProgram.getId());
        AssertionsKt.assertEquals(cardProgram, newCardProgram,
                "new card program should be equal to returned card program");

        cardProgram.setProgramCurrency("MXN"); // updated to program currency seem not to work?
        cardProgram.setCardBINs(Set.of("112233", "665544"));
        CardProgram updatedCardProgram = cardProgramMgr.updateWith(cardProgram);
        AssertionsKt.assertNotEquals(cardProgram, updatedCardProgram,
                "updated card program should not be equal to card account");
    }

}
