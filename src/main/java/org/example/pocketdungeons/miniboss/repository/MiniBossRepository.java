package org.example.pocketdungeons.miniboss.repository;

import org.example.pocketdungeons.miniboss.model.MiniBoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiniBossRepository extends JpaRepository<MiniBoss, Integer> {
}
