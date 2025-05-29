package org.example.pocketdungeons.miniboss.service;

import org.example.pocketdungeons.boss.model.Boss;
import org.example.pocketdungeons.miniboss.model.MiniBoss;
import org.example.pocketdungeons.miniboss.repository.MiniBossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiniBossService {

    private final MiniBossRepository miniBossRepository;

    @Autowired
    public MiniBossService(MiniBossRepository miniBossRepository) {
        this.miniBossRepository = miniBossRepository;
    }

    public MiniBoss createBoss(String name, int hp, int strength){
        MiniBoss boss = new MiniBoss();
        boss.setHP(hp);
        boss.setName(name);
        boss.setStrength(strength);

        return miniBossRepository.save(boss);
    }
}
