package com.example.demo.listeners;

import com.example.demo.services.EncryptionService;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.stereotype.Component;

@Component
public class PreInsertListener extends AbstractEncryptionListener implements PreInsertEventListener {
    public PreInsertListener(EncryptionService encryptionService) {
        super(encryptionService);
    }

    @Override
    public boolean onPreInsert(PreInsertEvent preInsertEvent) {
        System.out.println("In Pre Insert");
        this.encrypt(preInsertEvent.getState(), preInsertEvent.getPersister().getPropertyNames(), preInsertEvent.getEntity());
        return false;
    }
}
