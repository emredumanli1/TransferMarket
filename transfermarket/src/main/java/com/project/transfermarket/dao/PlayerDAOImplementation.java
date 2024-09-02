package com.project.transfermarket.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.transfermarket.entity.Player;
import com.project.transfermarket.entity.Team;
import com.project.transfermarket.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class PlayerDAOImplementation implements PlayerDAO{

    private EntityManager entityManager;

    @Autowired
    public PlayerDAOImplementation(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Player player) {
        entityManager.persist(player);
    }

    @Override
    public Player findPlayerById(int theId) {
        Player tempPlayer = entityManager.find(Player.class, theId);
        return tempPlayer;
    }

    @Override
    public Player findPlayerByName(String name) {
        TypedQuery<Player> theQuery = entityManager.createQuery("select p from Player p where p.name = :data ", Player.class);
        theQuery.setParameter("data", name);
        List<Player> resultList = theQuery.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        else {
            return resultList.get(0);    
        }
    
    }

    @Override
    @Transactional
    public void update(Player player) {
        entityManager.merge(player);
    }

    @Override
    @Transactional
    public void deletePlayerById(int theId) {
        Player tempPlayer = entityManager.find(Player.class, theId);
        entityManager.remove(tempPlayer);
    }

    @Override
    @Transactional
    public void deletePlayerByName(String name) {
        TypedQuery<Player> theQuery = entityManager.createQuery("select p from Player p where p.name = :data ", Player.class);
        theQuery.setParameter("data", name);
        Player tempPlayer = theQuery.getSingleResult();
        entityManager.remove(tempPlayer);
    }

    @Override
    public List<Player> findAll() {
        TypedQuery<Player> theQuery = entityManager.createQuery("from Player", Player.class);

        List<Player> players = theQuery.getResultList();

        return players;
    }

    @Override
    public List<Player> findPlayersofTeam(Team team) {
        TypedQuery<Player> theQuery = entityManager.createQuery("select p from Player p where p.team = :data ", Player.class);
        theQuery.setParameter("data", team);
        List<Player> players = theQuery.getResultList();

        return players;
    }

    @Override
    public String TransferPlayer(User user, Player player) {

        Team newTeam = user.getTeam();
        Team oldTeam = player.getTeam();

        if (oldTeam.getId() == newTeam.getId()){
            return "This player already exists in team!";
        }

        if (!player.isFree()){
            return "You can't sign this player!";
        }


        player.setTeam(newTeam);
        player.setFree(0);
        return "Successfully transferred player to your club!";

    }

    @Override
    public Player findPlayerBysurname(String surname) {
        TypedQuery<Player> theQuery = entityManager.createQuery("select p from Player p where p.surname = :data ", Player.class);
        theQuery.setParameter("data", surname);
        List<Player> resultList = theQuery.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        else {

            return resultList.get(0); 
            
        }
    }

}
