package com.project.demo.service;

import com.project.demo.model.cardholder.CreditCardHolder;
import com.project.demo.model.creditcard.CreditCard;
import com.project.demo.model.pan.CreditCardPAN;
import com.project.demo.repository.creditcard.CreditCardRepository;
import com.project.demo.repository.creditcardholder.CreditCardHolderRepository;
import com.project.demo.repository.creditcardpan.CreditCardPANRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardHolderRepository creditCardHolderRepository;
    private final CreditCardRepository creditCardRepository;
    private final CreditCardPANRepository creditCardPANRepository;

    @Override
    @Transactional
    public CreditCard getCreditCardById(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id).orElseThrow();
        CreditCardHolder creditCardHolder = creditCardHolderRepository.findByCreditCardId(id).orElseThrow();
        CreditCardPAN creditCardPAN = creditCardPANRepository.findByCreditCardId(id).orElseThrow();

        creditCard.setFirstName(creditCardHolder.getFirstName());
        creditCard.setLastName(creditCardHolder.getLastName());
        creditCard.setZipCode(creditCardHolder.getZipCode());
        creditCard.setCreditCardNumber(creditCardPAN.getCreditCardNumber());

        return creditCard;
    }

    @Override
    @Transactional
    public CreditCard saveCreditCard(CreditCard cc) {
        CreditCard savedCC = creditCardRepository.save(cc);
        savedCC.setFirstName(cc.getFirstName());
        savedCC.setLastName(cc.getLastName());
        savedCC.setZipCode(cc.getZipCode());
        savedCC.setCreditCardNumber(cc.getCreditCardNumber());

        creditCardHolderRepository.save(CreditCardHolder.builder()
                .creditCardId(savedCC.getId())
                .firstName(savedCC.getFirstName())
                .lastName(savedCC.getLastName())
                .zipCode(savedCC.getZipCode())
                .build());

        creditCardPANRepository.save(CreditCardPAN.builder()
                .creditCardNumber(savedCC.getCreditCardNumber())
                .creditCardId(savedCC.getId())
                .build());

        return savedCC;
    }
}
