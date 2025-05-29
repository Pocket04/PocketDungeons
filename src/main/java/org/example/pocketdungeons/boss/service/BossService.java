package org.example.pocketdungeons.boss.service;

import org.example.pocketdungeons.boss.model.Boss;
import org.example.pocketdungeons.boss.repository.BossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BossService {

    private final BossRepository bossRepository;

    @Autowired
    public BossService(BossRepository bossRepository) {
        this.bossRepository = bossRepository;
    }

    public Boss createBoss(String name, int hp, int strength){
        Boss boss = new Boss();
        boss.setHP(hp);
        boss.setName(name);
        boss.setStrength(strength);

        return bossRepository.save(boss);
    }
}
