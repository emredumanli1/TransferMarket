package com.project.transfermarket.dao;

import java.util.List;

import com.project.transfermarket.entity.Team;

public interface TeamDAO {

    void save(Team team);

    Team findTeamById(int theId);

    Team findTeamByName(String name);

    void update(Team team);

    void deleteTeamById(int theId);

    void deleteTeamByName(String name);

    List<Team> findAll();

    List<Team> findNationalLeague(String nation);

}
