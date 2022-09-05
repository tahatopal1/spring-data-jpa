package com.example.demo.bootstrap;

import com.example.demo.model.ElectricGuitar;
import com.example.demo.repository.ElectricGuitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    ElectricGuitarRepository electricGuitarRepository;

    @Override
    public void run(String... args) throws Exception {
        ElectricGuitar electricGuitar = new ElectricGuitar();
        electricGuitar.setNumberOfStrings(6);
        electricGuitar.setNumberOfPickups(2);
        electricGuitar = electricGuitarRepository.saveAndFlush(electricGuitar);

        electricGuitarRepository.findById(electricGuitar.getId())
                .ifPresent(System.out::println);
    }

}
