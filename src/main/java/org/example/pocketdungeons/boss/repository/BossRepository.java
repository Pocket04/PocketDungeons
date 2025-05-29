package org.example.pocketdungeons.boss.repository;

import org.example.pocketdungeons.boss.model.Boss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BossRepository extends JpaRepository<Boss, Integer> {
    Boss findByName(String name);
}
