package com.project.transfermarket.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class User {

    @Id
    @Column(name = "user_id")
    private String username;

    @Column(name = "pw")
    private String password;


    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "team_id")
    private Team team;

    public User() {
        
    }

    public User(String username, String password, Team team) {
        this.username = username;
        this.password = password;
        this.team = team;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.team = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", team_id=" + team.getTeamName() + "]";
    }

    public Team getTeam(){

        if (team == null){
            team = new Team("NONE", "NONE");
        }

        return team;
    }

    public void setTeam(Team theTeam){
        this.team = theTeam;
    }


    
}
