package org.example.pocketdungeons.helper;

import org.example.pocketdungeons.boss.model.Boss;
import org.example.pocketdungeons.boss.service.BossService;
import org.example.pocketdungeons.character.model.Hero;
import org.example.pocketdungeons.character.service.CharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CombatHelper {

    private final BossService bossService;

    private final CharService charService;

    private Boss boss;

    private Hero hero;

    @Autowired
    public CombatHelper(BossService bossService, CharService charService) {
        this.bossService = bossService;
        this.charService = charService;
    }

    public String cast(int target, int spell) {
        int x = 0;
        switch (target){
            case 100:
                boss = bossService.getBossById(target);
                x = boss.getHP() + spell;
                boss.setHP(x);
                System.out.println(boss.getId());
                bossService.saveBoss(boss);
                if (spell > 0){
                    return String.format("%s has been healed for %d, and their current hp is %d", boss.getName(), spell, x);
                }
                return String.format("%s has been damaged for %d, and their current hp is %d", boss.getName(), spell, x);
            default:
                hero = charService.getHeroById(target);
                x = hero.getHP() + spell;
                hero.setHP(x);
                charService.saveHero(hero);
                if (spell > 0){
                    return String.format("%s has been healed for %d, and their current hp is %d", hero.getName(), spell, x);
                }
                return String.format("%s has been damaged for %d, and their current hp is %d", hero.getName(), spell, x);
        }

    }
    public void reset(){
        bossService.reset();
        charService.reset();
    }
}
