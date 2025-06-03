package org.example.pocketdungeons.character.service;

import org.example.pocketdungeons.boss.service.BossService;
import org.example.pocketdungeons.character.model.Hero;
import org.example.pocketdungeons.character.repository.CharRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CharService {

    private final CharRepository charRepository;
    private final BossService bossService;

    @Autowired
    public CharService(CharRepository charRepository, BossService bossService) {
        this.charRepository = charRepository;
        this.bossService = bossService;
    }
    public Hero createCharacter(String player, String name, int hp, int strength, int defense){
        if (charRepository.getByPlayer(player).isPresent()){
            throw new RuntimeException("You already have a character.");
        }

        Hero character = new Hero();
        character.setHP(hp);
        character.setMaxHP(hp);
        character.setName(name);
        character.setStrength(strength);
        character.setDefense(defense);
        character.setPlayer(player);
        character.setSpell1(-1);
        character.setSpell2(-2);
        character.setSpell3(-3);

        return charRepository.save(character);
    }


    public void deleteCharacter(String player) {
        Optional<Hero> hero = charRepository.getByPlayer(player);
        hero.ifPresent(charRepository::delete);
    }

    public String cast(String player, int spell){
        Optional<Hero> optHero = charRepository.getByPlayer(player);
        if (optHero.isPresent()){
            Hero hero = optHero.get();
            int x = switch (spell) {
                case 1 -> hero.getSpell1();
                case 2 -> hero.getSpell2();
                case 3 -> hero.getSpell3();
                default -> 0;
            };
            return bossService.beTarget(x);
        }
        return "Oh no, something went wrong!!!";
    }

}
