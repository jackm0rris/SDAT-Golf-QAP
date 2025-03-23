package com.keyin.Controllers;

import com.keyin.Entities.Member;
import com.keyin.Entities.Tournament;
import com.keyin.Services.MemberService;
import com.keyin.Services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.addMember(member));
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String membershipType) {
        if (name != null) {
            return ResponseEntity.ok(memberService.searchByName(name));
        } else if (email != null) {
            return ResponseEntity.ok(memberService.searchByEmail(email));
        } else if (phone != null) {
            return ResponseEntity.ok(memberService.searchByPhone(phone));
        } else if (membershipType != null) {
            return ResponseEntity.ok(memberService.searchByMembershipType(membershipType));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{memberId}/tournaments")
    public ResponseEntity<Member> addTournamentsToMember(@PathVariable Long memberId, @RequestBody List<Long> tournamentIds) {
        return memberService.getMemberById(memberId)
                .map(member -> {
                    List<Tournament> tournaments = tournamentService.getTournamentsByIds(tournamentIds);
                    if (!tournaments.isEmpty()) {
                        member.setTournament(tournaments.get(0));
                        memberService.updateMember(member);
                    }
                    return ResponseEntity.ok(member);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
