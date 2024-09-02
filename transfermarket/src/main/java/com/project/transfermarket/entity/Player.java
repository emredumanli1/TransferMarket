package com.project.transfermarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "marketvalue")
    private double marketValue;

    @Column(name = "kitnumber")
    private int kitNumber;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "free")
    private int free;

    public Player() {
        this.free = 1;
    }

    public Player(String name, String surname, double marketValue, int kitNumber) {
        this.name = name;
        this.surname = surname;
        this.marketValue = marketValue;
        this.kitNumber = kitNumber;
        this.team = null;
        this.free = 1;
    }

    public Player(String name, String surname, double marketValue, int kitNumber, Team team) {
        this.name = name;
        this.surname = surname;
        this.marketValue = marketValue;
        this.kitNumber = kitNumber;
        this.team = team;
        this.free = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public int getKitNumber() {
        return kitNumber;
    }

    public void setKitNumber(int kitNumber) {
        this.kitNumber = kitNumber;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", name=" + name + ", surname=" + surname + ", marketValue=" + marketValue
                + ", kitNumber=" + kitNumber + "Team: " + getTeam().getTeamName();
    }

    public boolean isFree() {
        
        if (this.free == 0){
            return false;
        }
        return true;
    }
    
    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    

}
