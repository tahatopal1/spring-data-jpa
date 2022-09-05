package com.example.demo.listeners;

import com.example.demo.interceptor.EncryptedString;
import com.example.demo.services.EncryptionService;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.IntStream;

public abstract class AbstractEncryptionListener {

    private final EncryptionService encryptionService;

    public AbstractEncryptionListener(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public void encrypt(Object[] state, String[] propertyNames, Object entity){
        ReflectionUtils.doWithFields(entity.getClass(), field -> encryptField(field, state, propertyNames), this::isFieldEncrypted);
    }

    private void encryptField(Field field, Object[] state, String[] propertyNames){
        int idx = getPropertyIndex(field.getName(), propertyNames);
        Object currentValue = state[idx];
        state[idx] = encryptionService.encrypt(currentValue.toString());
    }

    public void decrypt(Object entity){
        ReflectionUtils.doWithFields(entity.getClass(), field -> decryptField(field, entity), this::isFieldEncrypted);
    }

    private void decryptField(Field field, Object entity){
        field.setAccessible(true);
        Object value = ReflectionUtils.getField(field, entity);
        ReflectionUtils.setField(field, entity, encryptionService.decrypt(value.toString()));
    }

    public boolean isFieldEncrypted(Field field){
        return AnnotationUtils.findAnnotation(field, EncryptedString.class) != null;
    }

    private int getPropertyIndex(String name, String[] properties) {
        return IntStream.range(0, properties.length)
                .filter(value -> name.equals(properties[value]))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Property not found: " + name));
    }

}
