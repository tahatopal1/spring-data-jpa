package com.example.demo.callback;

import com.example.demo.helper.SpringContextHelper;
import com.example.demo.model.CreditCard;
import com.example.demo.services.EncryptionService;
import jakarta.persistence.*;

public class CreditCardJPACallback {

    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate(CreditCard creditCard){
        System.out.println("Before update was called...");
        creditCard.setCreditCardNumber(getEncryptionService().encrypt(creditCard.getCreditCardNumber()));
    }

    @PostPersist
    @PostLoad
    @PostUpdate
    public void postLoad(CreditCard creditCard){
        System.out.println("Post load was called...");
        creditCard.setCreditCardNumber(getEncryptionService().decrypt(creditCard.getCreditCardNumber()));
    }

    private EncryptionService getEncryptionService(){
        return SpringContextHelper.applicationContext().getBean(EncryptionService.class);
    }

}
