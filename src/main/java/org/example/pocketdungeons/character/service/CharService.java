package org.example.pocketdungeons.character.service;

import org.example.pocketdungeons.boss.service.BossService;
import org.example.pocketdungeons.character.model.Hero;
import org.example.pocketdungeons.character.repository.CharRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public Hero createCharacter(String player, String name, int hp, int strength, int defense, String classType){
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
        character.setClassType(classType);
        switch (classType){
            case "Warrior":
                character.setSpell1(-1);
                character.setSpell2(-2);
                character.setSpell3(-3);
                break;
            case "Rogue":
                character.setSpell1(-4);
                character.setSpell2(-5);
                character.setSpell3(-7);
                break;
            case "Priest":
                character.setSpell1(-1);
                character.setSpell2(-2);
                character.setSpell3(5);
                break;
        }
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
            if (x > 0){
                return beTarget(player, x);
            }
            return bossService.beTarget(x);
        }
        return "Oh no, something went wrong!!!";
    }

    public String beTarget(String player, int spell){
        List<Hero> characters = charRepository.findAll();
        String death = "";
        for (Hero character : characters) {
            character.setHP(character.getHP() + spell);
            if (character.getHP() <= 0){
                death = character.getName() + " has died :( ";
            }
            charRepository.save(character);
        }
        if (spell > 0){
            return player + " has healed their party for: " + spell;
        }
        return "The group was damaged by the boss's ability for: " + spell + System.lineSeparator() + death;
    }
    public List<Hero> getAll(){
        List<Hero> heroes = new ArrayList<>();
        for (Hero hero : charRepository.findAll()) {
            if (hero.getHP() > 0){
                heroes.add(hero);
            }
        }
        return heroes;
    }
    public Hero getHero(String name){
        return charRepository.findHeroByPlayer(name);
    }

}
