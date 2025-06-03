package org.example.pocketdungeons.character.repository;

import org.example.pocketdungeons.character.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharRepository extends JpaRepository<Hero, Integer> {
    Optional<Hero> getByPlayer(String player);
}
