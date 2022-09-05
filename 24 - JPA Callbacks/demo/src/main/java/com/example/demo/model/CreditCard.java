package com.example.demo.model;

import com.example.demo.annotation.EncryptedString;
import com.example.demo.callback.CreditCardJPACallback;
import com.example.demo.converter.CreditCardConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@EntityListeners(CreditCardJPACallback.class)
// If you want to use jpa callbacks, remove the comment and remove @Converter annotation from CreditCardConverter.java
// and @Convert annotaiton below
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CreditCardConverter.class)
    private String creditCardNumber;
    private String cvv;
    private String expirationDate;

}
