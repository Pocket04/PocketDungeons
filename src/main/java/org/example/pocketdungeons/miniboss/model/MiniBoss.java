package org.example.pocketdungeons.miniboss.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class MiniBoss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int HP;

    @Column
    private int strength;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }

    public int getStrength() {
        return strength;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
