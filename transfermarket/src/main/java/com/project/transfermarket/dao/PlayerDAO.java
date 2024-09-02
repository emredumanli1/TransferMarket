package com.project.transfermarket.dao;

import java.util.List;

import com.project.transfermarket.entity.Player;
import com.project.transfermarket.entity.Team;
import com.project.transfermarket.entity.User;


public interface PlayerDAO {

    void save(Player player);

    Player findPlayerById(int theId);

    Player findPlayerByName(String name);

    Player findPlayerBysurname(String surname);

    void update(Player player);

    void deletePlayerById(int theId);

    void deletePlayerByName(String name);

    List<Player> findAll();

    List<Player> findPlayersofTeam(Team team);

    String TransferPlayer(User user, Player player);
}
