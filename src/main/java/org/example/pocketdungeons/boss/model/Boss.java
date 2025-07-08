package org.example.pocketdungeons.boss.model;

import jakarta.persistence.*;


@Entity
public class Boss {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private int HP;

    @Column
    private int maxHP;

    @Column
    private int strength;

    @Column
    private int turns;

    public void setId(int id){
        this.id = id;
    }


    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
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
