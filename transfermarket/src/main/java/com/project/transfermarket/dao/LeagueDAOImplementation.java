package com.project.transfermarket.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.transfermarket.entity.League;
import com.project.transfermarket.entity.Team;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class LeagueDAOImplementation implements LeagueDAO{

    private EntityManager entityManager;

    @Autowired
    public LeagueDAOImplementation(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public League findLeagueById(int theId) {
        League tempLeague = entityManager.find(League.class, theId);
        return tempLeague;
    }

    @Override
    public League findLeagueByName(String name) {
        
        TypedQuery<League> theQuery = entityManager.createQuery("select l from League l where l.leaguename = :data ", League.class);
        theQuery.setParameter("data", name);
        List<League> resultList = theQuery.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        else {
            return resultList.get(0);    
        }
    }

    @Override
    @Transactional
    public void update(League league) {
        entityManager.merge(league);
    }

    @Override
    @Transactional
    public void save(League league) {
        entityManager.persist(league);
    }

    @Override
    public List<Team> findTeamsbyLeagueId(int theId) {

        League league = findLeagueById(theId);

        List<Team> teams = league.getParticipants();

        return teams;
    }

}
