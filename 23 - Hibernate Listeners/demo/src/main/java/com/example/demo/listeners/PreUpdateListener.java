package com.example.demo.listeners;

import com.example.demo.services.EncryptionService;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Component;

@Component
public class PreUpdateListener extends AbstractEncryptionListener implements PreUpdateEventListener {

    public PreUpdateListener(EncryptionService encryptionService) {
        super(encryptionService);
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent preUpdateEvent) {
        System.out.println("In Pre Update");
        this.encrypt(preUpdateEvent.getState(), preUpdateEvent.getPersister().getPropertyNames(), preUpdateEvent.getEntity());
        return false;
    }
}
