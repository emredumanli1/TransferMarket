package com.project.transfermarket.dao;

import java.util.List;

import com.project.transfermarket.entity.League;
import com.project.transfermarket.entity.Team;

public interface LeagueDAO {

    League findLeagueById(int theId);

    League findLeagueByName(String name);

    void update(League league);

    void save(League league);

    List<Team> findTeamsbyLeagueId(int theId);

} 

