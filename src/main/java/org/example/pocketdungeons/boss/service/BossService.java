package org.example.pocketdungeons.boss.service;

import org.example.pocketdungeons.boss.model.Boss;
import org.example.pocketdungeons.boss.repository.BossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        boss.setId(100);
        return bossRepository.save(boss);
    }

    public Boss getBoss() {
        Optional<Boss> optionalBoss = bossRepository.findById(100);
        return optionalBoss.orElse(null);
    }
    public Boss getBossById(int id){
        Optional<Boss> optBoss = bossRepository.findById(id);
        return optBoss.orElse(null);
    }
    public void reset(){
        List<Boss> bosses = bossRepository.findAll();
        for (Boss boss : bosses) {
            boss.setHP(boss.getMaxHP());
            bossRepository.save(boss);
        }
    }
    public void deleteBoss(){
        bossRepository.deleteById(100);
    }

    public void saveBoss(Boss boss) {
        System.out.println(boss.getId());
        bossRepository.save(boss);
    }
}
