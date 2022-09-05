package com.example.demo.repositories;

import com.example.demo.model.CreditCard;
import com.example.demo.repository.CreditCardRepository;
import com.example.demo.services.EncryptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CreditCardRepositoryTest {

    final String CREDIT_CARD = "123456788900000";

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void testSaveAndStoreCreditCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardNumber(CREDIT_CARD);
        creditCard.setCvv("123");
        creditCard.setExpirationDate("12/2028");

        CreditCard saved = creditCardRepository.saveAndFlush(creditCard);
        System.out.println("Getting CC from database: " + saved.getCreditCardNumber());

//        System.out.println("CC At Rest");
//        System.out.println("CC Encrypted: " + encryptionService.encrypt(CREDIT_CARD));
//
//        Map<String, Object> dbRow = jdbcTemplate.queryForMap("SELECT * FROM credit_card WHERE id = " + saved.getId());
//        String dbCardValue = (String) dbRow.get("credit_card_number");
//
//        assertThat(saved.getCreditCardNumber()).isNotEqualTo(dbCardValue);
//        assertThat(dbCardValue).isEqualTo(encryptionService.encrypt(CREDIT_CARD));
//
//        CreditCard fetched = creditCardRepository.findById(saved.getId()).get();
//        assertThat(saved.getCreditCardNumber()).isEqualTo(fetched.getCreditCardNumber());

    }
}
