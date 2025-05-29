package org.example.pocketdungeons.character.repository;

import org.example.pocketdungeons.character.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharRepository extends JpaRepository<Hero, Integer> {
}
