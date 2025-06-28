package org.example.pocketdungeons.helpmeimconfused;

import org.example.pocketdungeons.boss.service.BossService;
import org.example.pocketdungeons.character.service.CharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BossCharHelpService {

    private final BossService bossService;

    private final CharService charService;

    @Autowired
    public BossCharHelpService(BossService bossService, CharService charService) {
        this.bossService = bossService;
        this.charService = charService;
    }

    public String bossDamage(int attack){
        return charService.beTarget("Boss", attack);
    }
}
