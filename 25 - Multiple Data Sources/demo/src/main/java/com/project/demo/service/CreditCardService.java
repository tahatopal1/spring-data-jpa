package com.project.demo.service;

import com.project.demo.model.creditcard.CreditCard;

public interface CreditCardService {
    CreditCard getCreditCardById(Long id);

    CreditCard saveCreditCard(CreditCard cc);

}
