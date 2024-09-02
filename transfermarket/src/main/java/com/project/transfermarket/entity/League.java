package com.project.transfermarket.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "league")
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "league_name")
    private String leaguename;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "team_league",
            joinColumns = @JoinColumn(name = "league_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> participants;

    public League() {

    }

    public League(String leaguename) {
        this.leaguename = leaguename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeaguename() {
        return leaguename;
    }

    public void setLeaguename(String leaguename) {
        this.leaguename = leaguename;
    }

    public List<Team> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Team> participants) {
        this.participants = participants;
    }

    public void addTeam(Team theTeam){

        if (participants == null){
            participants = new ArrayList<>();
        }

        participants.add(theTeam);
    }

    public List<Team> generateGroup(){
        List<Team> shuffledTeams = participants;
        Collections.shuffle(shuffledTeams); 
   
        return shuffledTeams.size() > 4 ? shuffledTeams.subList(0, 4) : shuffledTeams;
    }

    @Override
    public String toString() {
        return leaguename;
    }

    
}
