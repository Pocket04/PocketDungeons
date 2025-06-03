package org.example.pocketdungeons.character.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String player;

    @Column
    private String name;

    @Column
    private int HP;

    @Column
    private int maxHP;

    @Column
    private int strength;

    @Column
    private int defense;

    @Column
    private int spell1;

    @Column
    private int spell2;

    @Column
    private int spell3;

    public int getSpell1() {
        return spell1;
    }

    public void setSpell1(int spell1) {
        this.spell1 = spell1;
    }

    public int getSpell2() {
        return spell2;
    }

    public void setSpell2(int spell2) {
        this.spell2 = spell2;
    }

    public int getSpell3() {
        return spell3;
    }

    public void setSpell3(int spell3) {
        this.spell3 = spell3;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getId() {
        return id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
