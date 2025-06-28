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

    public Boss createBoss(String name, int strength, int hp){
        Boss boss = new Boss();
        boss.setHP(hp);
        boss.setMaxHP(hp);
        boss.setName(name);
        boss.setStrength(strength);
        return bossRepository.save(boss);
    }
    public String beTarget(int amount){
        Boss boss = bossRepository.findByName("Boss");
        int hp = boss.getHP() + amount;
        boss.setHP(hp);
        bossRepository.save(boss);
        return "The boss was damaged for " + amount + " and their current hp is " + hp;
    }

    public Boss getBoss() {
        return bossRepository.getByName("Boss");
    }
    public void deleteBoss(){
        bossRepository.deleteById(1);
    }
}
