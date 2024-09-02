package com.project.transfermarket.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.transfermarket.dao.TeamDAO;
import com.project.transfermarket.dao.UserDAO;
import com.project.transfermarket.entity.Team;
import com.project.transfermarket.entity.User;

@Controller
public class TeamController {

    private TeamDAO teamDAO;
    private UserDAO userDAO;

    @Autowired
    public TeamController(TeamDAO theDAO, UserDAO theUserDAO) {
        teamDAO = theDAO;
        userDAO = theUserDAO;
    }

    @GetMapping("/")
	public String listEmployees(Model theModel, Principal principal) {

		List<Team> theTeams = teamDAO.findAll();
        User tempUser = userDAO.findUserByName(principal.getName());
        String teamn = tempUser.getTeam().getTeamName();
        
 
        theModel.addAttribute("teamn", teamn);
		theModel.addAttribute("teams", theTeams);
        
		return "home";
	}

    @GetMapping("/teamlist/filter")
    public String filterTeams(
        @RequestParam(value = "nation", required = false) String nation,
        Model theModel, Principal principal) {

        List<Team> theTeams;
        if (nation == null || nation.isEmpty()) {
            theTeams = teamDAO.findAll();  
        } 
    
        else {
          theTeams = teamDAO.findNationalLeague(nation);
        }
    
        theModel.addAttribute("teams", theTeams);

        User tempUser = userDAO.findUserByName(principal.getName());
        String teamn = tempUser.getTeam().getTeamName();
 
        theModel.addAttribute("teamn", teamn);
        return "home";  
}


}
