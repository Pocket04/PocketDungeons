package org.example.pocketdungeons.character.service;

import org.example.pocketdungeons.character.model.Hero;
import org.example.pocketdungeons.character.repository.CharRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharService {

    private final CharRepository charRepository;

    @Autowired
    public CharService(CharRepository charRepository) {
        this.charRepository = charRepository;
    }
    public Hero createCharacter(String player, String name, int hp, int strength, int defense){
        Hero character = new Hero();
        character.setHP(hp);
        character.setName(name);
        character.setStrength(strength);
        character.setDefense(defense);
        character.setPlayer(player);

        return charRepository.save(character);
    }
}
