package fr.challenge.filerouge_utopios.controller;

import fr.challenge.filerouge_utopios.entity.Game;
import fr.challenge.filerouge_utopios.entity.Result;
import fr.challenge.filerouge_utopios.entity.Tournament;
import fr.challenge.filerouge_utopios.entity.User;
import fr.challenge.filerouge_utopios.service.LoginService;
import fr.challenge.filerouge_utopios.service.TournamentService;
import fr.challenge.filerouge_utopios.service.UserService;
import fr.challenge.filerouge_utopios.util.enums.Format;
import fr.challenge.filerouge_utopios.util.enums.Status;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class TournamentController {
    private final LoginService loginService;
    private final TournamentService tournamentService;
    private final UserService userService;

    public TournamentController(final LoginService loginService, TournamentService tournamentService, UserService userService) {
        this.loginService = loginService;
        this.tournamentService = tournamentService;
        this.userService = userService;
    }

    @RequestMapping("/tournaments")
    public String tournament(Model model) {
        if (loginService.isLoggedIn() || loginService.getUser() != null) {
            model.addAttribute("tournaments", tournamentService.findAll());
            return "tournament-list";
        }
        return "redirect:/login";
    }

    @RequestMapping("/newTournament")
    public String newTournament(Model model) {
        if (loginService.isLoggedIn() || loginService.getUser() != null) {
            model.addAttribute("tournament", new Tournament());
            model.addAttribute("formats", Format.values());
            model.addAttribute("statusMessage", null);
            return "tournament-create-update";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/newTournament", method = RequestMethod.POST)
    public String newTournament(@Valid @ModelAttribute("tournament") Tournament tournament, BindingResult result, Model model) {
        if (loginService.isLoggedIn() || loginService.getUser() != null) {
            tournament.setMinElo(0);
            tournament.setCreator(loginService.getUser());
            if (tournament.getStartDate().isAfter(tournament.getEndDate())) {
                model.addAttribute("tournament", new Tournament());
                model.addAttribute("formats", Format.values());
                model.addAttribute("statusMessage", "la date du debut de tournois ne peux commencer apres la fin ");
                return "tournament-create-update";
            }

            Tournament newTournament = tournamentService.save(tournament);
            model.addAttribute("tournament", new Tournament());
            model.addAttribute("formats", Format.values());
            model.addAttribute("statusMessage", "tournament " + tournament.getLabel() + " created");
            return "redirect:/tournaments/id=" + newTournament.getId();
        }
        return "redirect:/login";


    }

    @RequestMapping("/tournaments/id={idTournament}")
    public String tournamentById(@PathVariable("idTournament") Long idTournament, Model model) {
        if (loginService.isLoggedIn() || loginService.getUser() != null) {
            Tournament findTournament = tournamentService.findById(idTournament);

            if (findTournament != null) {
                model.addAttribute("tournament", findTournament);
                model.addAttribute("isCreator", findTournament.getCreator().equals(loginService.getUser()));
                return "tournament-details";
            }
            return "redirect:/tournaments";
        }
        return "redirect:/login";
    }

    @RequestMapping("/deleteTournament/id={idTournament}")
    public String deleteTournamentById(@PathVariable("idTournament") Long idTournament, Model model) {
        if (loginService.isLoggedIn() || loginService.getUser() != null) {
            Tournament findTournament = tournamentService.findById(idTournament);
            if (findTournament != null) {
                tournamentService.delete(findTournament);
            }
            return "redirect:/tournaments";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/finishTournament/id={idTournament}", method = RequestMethod.POST)
    public String finishTournamentById(@PathVariable("idTournament") Long idTournament, Model model) {
        Tournament tournament = tournamentService.findById(idTournament);
        for (Game game : tournament.getGames()) {
            game.setStatus(Status.FINISHED);
            for (Result result : game.getResults()) {
                result.setResult((int) (Math.random() * 20 - 10));
            }
        }
        tournament.setStatus(Status.FINISHED);
        tournamentService.save(tournament);

        return "redirect:/tournaments/id=" + tournament.getId();
    }
}
