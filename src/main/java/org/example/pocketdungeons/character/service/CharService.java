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
    public Hero createCharacter(String player, String name, String classType){
        if (charRepository.getByPlayer(player).isPresent()){
            throw new RuntimeException("You already have a character.");
        }

        Hero character = new Hero();
        character.setName(name);
        character.setPlayer(player);
        character.setClassType(classType);
        switch (classType){
            case "Warrior":
                character.setSpell1(-1);
                character.setSpell2(-2);
                character.setSpell3(-3);
                character.setDefense(10);
                character.setMaxHP(20);
                character.setHP(20);
                character.setId(1);
                break;
            case "Rogue":
                character.setSpell1(-4);
                character.setSpell2(-5);
                character.setSpell3(-7);
                character.setDefense(5);
                character.setMaxHP(13);
                character.setHP(13);
                character.setId(2);
                break;
            case "Priest":
                character.setSpell1(-1);
                character.setSpell2(-2);
                character.setSpell3(5);
                character.setDefense(3);
                character.setMaxHP(10);
                character.setHP(10);
                character.setId(3);
                break;
        }
        return charRepository.save(character);
    }


    public void deleteCharacter(String player) {
        Optional<Hero> hero = charRepository.getByPlayer(player);
        hero.ifPresent(charRepository::delete);
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
    public Hero getHeroById(int id){
        Optional<Hero> opthero = charRepository.findById(id);
        return opthero.orElse(null);
    }

    public void reset(){
        List<Hero> heroes = charRepository.findAll();
        for (Hero hero : heroes) {
            hero.setHP(hero.getMaxHP());
            charRepository.save(hero);
        }
    }
    public Hero getHeroByPlayer(String name){
        return charRepository.findHeroByPlayer(name);
    }

    public void saveHero(Hero hero) {
        charRepository.save(hero);
    }
}
