package com.project.transfermarket.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "nation")
    private String nation;

    @OneToOne(mappedBy = "team",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private User user;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "team_id")
    private List<Player> players;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "team_league",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "league_id")
    )
    private List<League> leagues;
    

    public Team() {
        
    }

    public Team(String teamName, String nation) {
        this.teamName = teamName;
        this.nation = nation;
    }

    public double getMarketValue(){

        double tempValue = 0;

        for (Player player : players) {
            tempValue = tempValue + player.getMarketValue();
        }

        BigDecimal bd = new BigDecimal(tempValue);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        tempValue = bd.doubleValue();

        return tempValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player){

        if (players == null){
            players = new ArrayList<>();
        }

        players.add(player);
    }

    @Override
    public String toString() {
        return "Team [id=" + id + ", teamName=" + teamName + ", nation=" + nation + ", user=" + user + ", players="
                + players + "]";
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

    public void addLeague(League newLeague){

        if (leagues == null){
            leagues = new ArrayList<>();
        }

        leagues.add(newLeague);
    }
    

    
}
