package com.project.demo;

import com.project.demo.model.creditcard.CreditCard;
import com.project.demo.service.CreditCardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SdjpaMultiDbApplicationTests {

    @Autowired
    CreditCardService creditCardService;

    @Test
    void testSaveAndGetCreditCard(){

        CreditCard creditCard = CreditCard.builder()
                .firstName("John")
                .lastName("Smith")
                .zipCode("12345")
                .creditCardNumber("123456")
                .cvv("123")
                .expirationDate("12/28")
                .build();

        CreditCard savedCreditCard = creditCardService.saveCreditCard(creditCard);
        
        assertThat(savedCreditCard).isNotNull();
        assertThat(savedCreditCard.getId()).isNotNull();
        assertThat(savedCreditCard.getCreditCardNumber()).isNotNull();

        CreditCard fetchedCreditCard = creditCardService.getCreditCardById(savedCreditCard.getId());

        assertThat(fetchedCreditCard).isNotNull();
        assertThat(fetchedCreditCard.getId()).isNotNull();
        assertThat(fetchedCreditCard.getCreditCardNumber()).isNotNull();
        

    }

}
