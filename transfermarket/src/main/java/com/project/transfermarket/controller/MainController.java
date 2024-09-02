package com.project.transfermarket.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.transfermarket.dao.LeagueDAO;
import com.project.transfermarket.dao.PlayerDAO;
import com.project.transfermarket.dao.TeamDAO;
import com.project.transfermarket.dao.UserDAO;
import com.project.transfermarket.entity.League;
import com.project.transfermarket.entity.Player;
import com.project.transfermarket.entity.Team;
import com.project.transfermarket.entity.User;

@Controller
public class MainController {

    private TeamDAO teamDAO;
    private UserDAO userDAO;
    private PlayerDAO playerDAO;
    private LeagueDAO leagueDAO;

    @Autowired
    public MainController(TeamDAO theDAO, UserDAO theUserDAO, PlayerDAO thePlayerDAO, LeagueDAO theLeagueDAO) {
        teamDAO = theDAO;
        userDAO = theUserDAO;
        playerDAO = thePlayerDAO;
        leagueDAO = theLeagueDAO;
    }

    //testing purposes no effect on real program
    @GetMapping("/test")
	public String testFunc(Principal principal) {

        List<Team> teams = leagueDAO.findTeamsbyLeagueId(3);
        for (Team team : teams) {
            System.out.println(team.getTeamName());
        }
        
		return "access-denied";
	}

    @PostMapping("/viewTeam")
    public String viewTeam(@RequestParam("teamId") int teamId, Model model, Principal principal) {

        Team tempTeam = teamDAO.findTeamById(teamId);
        if (tempTeam == null) {
            return "access-denied";
        }

        List<Player> players = playerDAO.findPlayersofTeam(tempTeam);
        double marketvalue = tempTeam.getMarketValue();

        String marketvaluest = marketvalue + "M €";
        User user = userDAO.findUserByName(principal.getName());

        model.addAttribute("players", players);
        model.addAttribute("tempTeam", tempTeam);
        model.addAttribute("marketvalue", marketvaluest);
        model.addAttribute("coach", user);

        boolean ownTeam = user.getTeam().getId() == tempTeam.getId();
        model.addAttribute("ownteam", ownTeam);

        return "team-page";

    }

    @GetMapping("/viewTeam")
    public String viewTeamGet(@RequestParam("teamId") int teamId, Model model, Principal principal) {

        Team tempTeam = teamDAO.findTeamById(teamId);
        if (tempTeam == null) {
            return "access-denied";
        }

        List<Player> players = playerDAO.findPlayersofTeam(tempTeam);
        double marketvalue = tempTeam.getMarketValue();

        String marketvaluest = marketvalue + "M €";
        User user = userDAO.findUserByName(principal.getName());

        model.addAttribute("players", players);
        model.addAttribute("tempTeam", tempTeam);
        model.addAttribute("marketvalue", marketvaluest);
        model.addAttribute("coach", user);

        boolean ownTeam = user.getTeam().getId() == tempTeam.getId();
        model.addAttribute("ownteam", ownTeam);

        return "team-page";

    }



    @PostMapping("/transferPlayer")
    public String transferPlayer(@RequestParam("playerId") int playerId, Principal principal, RedirectAttributes redirectAttributes) {
       
        
        User tempUser = userDAO.findUserByName(principal.getName());
        Player transferPlayer = playerDAO.findPlayerById(playerId);
        Team oldTeam = transferPlayer.getTeam();

        String msg = playerDAO.TransferPlayer(tempUser, transferPlayer);
        playerDAO.save(transferPlayer);

        int teamId = oldTeam.getId();
        redirectAttributes.addFlashAttribute("resultMessage", msg);
       
        
        return "redirect:/viewTeam?teamId=" + teamId;
    }

    @PostMapping("/releasePlayer")
    public String releasePlayer(@RequestParam("playerId") int playerId, Principal principal, RedirectAttributes redirectAttributes) {
    
        User currentUser = userDAO.findUserByName(principal.getName());
        Player player = playerDAO.findPlayerById(playerId);
        int toggle;

        if(player.getFree() == 1){
            toggle = 0;
        }

        else{
            toggle = 1;
        }
    
          
        player.setFree(toggle);
        playerDAO.save(player);    
    
   
        return "redirect:/viewTeam?teamId=" + currentUser.getTeam().getId();
    }

    @PostMapping("/terminatePlayer")
    public String terminatePlayer(@RequestParam("playerId") int playerId, RedirectAttributes redirectAttributes) {

        Player player = playerDAO.findPlayerById(playerId);
        Team oldTeam = player.getTeam();

        player.setTeam(teamDAO.findTeamById(13));
        player.setFree(1);
        playerDAO.save(player);
        redirectAttributes.addFlashAttribute("resultMessage", "Player contract terminated successfully.");
    
        return "redirect:/viewTeam?teamId=" + oldTeam.getId();
    }
    
    @GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		Player player = new Player();

		theModel.addAttribute("player", player);

		return "player-form";
	}

    @PostMapping("/savePlayer")
	public String savePlayer(@ModelAttribute("player") Player player, RedirectAttributes redirectAttributes) {

        player.setTeam(teamDAO.findTeamById(13));
        boolean transferOK = true;

        Player tempPlayer = playerDAO.findPlayerByName(player.getName());

        if(tempPlayer != null){
            Player tempPlayer2 = playerDAO.findPlayerBysurname(player.getSurname());
            if (tempPlayer2 != null){
                transferOK = false;
                redirectAttributes.addFlashAttribute("resultMessage", "Player already exists!.");
            }
        }


        if (transferOK){
            playerDAO.update(player);
            redirectAttributes.addFlashAttribute("resultMessage", "Player added successfully.");
        }

		return "redirect:/";
	}


    @PostMapping("/updateKit")
    public String updateKitNumber(@RequestParam("playerId") int theId, 
                                  @RequestParam("newKitNumber") String newKitNumber, 
                                  RedirectAttributes redirectAttributes) {

        Player player = playerDAO.findPlayerById(theId);
        Team team = player.getTeam();
        
        try{
            int kitnum = Integer.parseInt(newKitNumber);
            player.setKitNumber(kitnum);

            playerDAO.update(player);
            redirectAttributes.addFlashAttribute("resultMessage", "Kit number updated successfully!");
        }

        catch(Exception e){
            redirectAttributes.addFlashAttribute("resultMessage", "Invalid Kit number!");
        }
    
        return "redirect:/viewTeam?teamId=" + team.getId();
    }

    @PostMapping("/generateLeague")
    public String handleGenerateOption(@RequestParam("generateValue") int generateValue, Model theModel) {
        
        League league = leagueDAO.findLeagueById(generateValue);

        List<Team> participants = league.generateGroup();

        //list teams from highest to lowest market value
        java.util.Collections.sort(participants, new java.util.Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            return Double.compare(t2.getMarketValue(), t1.getMarketValue());
        }
        });

        theModel.addAttribute("participants", participants);

        return "league-page";
    }




}
