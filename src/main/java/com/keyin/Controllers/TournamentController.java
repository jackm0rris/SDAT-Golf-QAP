package com.keyin.Controllers;

import com.keyin.Entities.Member;
import com.keyin.Entities.Tournament;
import com.keyin.Services.MemberService;
import com.keyin.Services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        return tournamentService.getTournamentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        Tournament createdTournament = tournamentService.addTournament(tournament);
        return ResponseEntity.ok(createdTournament);
    }

    @PostMapping("/{tournamentId}/members")
    public ResponseEntity<Tournament> addMembersToTournament(@PathVariable Long tournamentId, @RequestBody List<Long> memberIds) {
        return tournamentService.getTournamentById(tournamentId)
                .map(tournament -> {
                    List<Member> members = memberService.getMembersByIds(memberIds);
                    tournament.getMembers().addAll(members);
                    tournamentService.updateTournament(tournament);
                    return ResponseEntity.ok(tournament);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Tournament>> searchTournaments(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        if (location != null) {
            return ResponseEntity.ok(tournamentService.searchByLocation(location));
        } else if (startDate != null) {
            return ResponseEntity.ok(tournamentService.searchByStartDate(startDate));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<Set<Member>> getMembersInTournament(@PathVariable Long id) {
        return tournamentService.getTournamentById(id)
                .map(tournament -> ResponseEntity.ok(tournament.getMembers()))
                .orElse(ResponseEntity.notFound().build());
    }
}