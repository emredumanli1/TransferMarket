package com.project.transfermarket.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.transfermarket.entity.Team;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class TeamDAOImplementation implements TeamDAO{

    private EntityManager entityManager;

    @Autowired
    public TeamDAOImplementation(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Team team) {
        entityManager.persist(team);
    }

    @Override
    public Team findTeamById(int theId) {
        Team tempTeam = entityManager.find(Team.class, theId);
        return tempTeam;
    }

    @Override
    public Team findTeamByName(String name) {
        TypedQuery<Team> theQuery = entityManager.createQuery("select t from Team t where t.teamName = :data ", Team.class);
        theQuery.setParameter("data", name);
        Team tempTeam = theQuery.getSingleResult();
        
        return tempTeam;
    }

    @Override
    @Transactional
    public void update(Team team) {
        entityManager.merge(team);
    }

    @Override
    @Transactional
    public void deleteTeamById(int theId) {
       Team tempTeam = entityManager.find(Team.class, theId);
       entityManager.remove(tempTeam);
    }

    @Override
    @Transactional
    public void deleteTeamByName(String name) {
        TypedQuery<Team> theQuery = entityManager.createQuery("select t from Team t where t.teamName = :data ", Team.class);
        theQuery.setParameter("data", name);
        Team tempTeam = theQuery.getSingleResult();
        entityManager.remove(tempTeam);

    }

    @Override
    public List<Team> findAll() {
        TypedQuery<Team> theQuery = entityManager.createQuery("select t from Team t where t.teamName != 'NOTEAM' ", Team.class);

        List<Team> teams = theQuery.getResultList();

        return teams;
    }

    @Override
    public List<Team> findNationalLeague(String nation) {

        TypedQuery<Team> theQuery = entityManager.createQuery("select t from Team t where t.nation = :data ", Team.class);
        theQuery.setParameter("data", nation);
        List<Team> teams = theQuery.getResultList();

        return teams;
    }

}
