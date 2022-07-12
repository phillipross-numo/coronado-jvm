package io.github.coronado.api;

import kotlin.test.AssertionsKt;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JavaCardAccountMgrIT {

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
        CardAccountMgr cardAccountMgr = new CardAccountMgr(partnerPublishersClient);

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
        CardProgram newCardProgram = cardProgramMgr.create(
                "2_812_" + System.currentTimeMillis(),
                "2_812_CPname" + System.currentTimeMillis(),
                "USD",
                newPublisher.getExternalId(), List.of("123456", "654321")
        );
        CardAccount newCardAccount = cardAccountMgr.create(
                newCardProgram.getExternalIid(),
                "2_812_" + System.currentTimeMillis(),
                CardAccountStatus.ENROLLED,
                newPublisher.getExternalId()
        );
        System.out.println("newCardAccount:\n" + newCardAccount);
        // card program external id was specified during creation but doesn't come back in the response
        // creation fails if a status is not specified

        AssertionsKt.assertTrue(cardAccountMgr.list().stream()
                .map(CardAccountReference::getId)
                .anyMatch(v -> v.equals(newCardAccount.getId())), "created card account should be in list");
        CardAccount cardAccount = cardAccountMgr.byId(newCardAccount.getId());
        AssertionsKt.assertEquals(cardAccount, newCardAccount,
                "new card account should be equal to returned card program");

        cardAccount.setStatus(CardAccountStatus.CLOSED);
        CardAccount updatedCardAccount = cardAccountMgr.updateWith(cardAccount);
        AssertionsKt.assertNotEquals(cardAccount, updatedCardAccount,
                "updated card account should not be equal to card account");
    }

}
