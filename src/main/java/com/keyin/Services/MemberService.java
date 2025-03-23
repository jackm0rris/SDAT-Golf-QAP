package com.keyin.Services;

import com.keyin.Entities.Member;
import com.keyin.Entities.Tournament;
import com.keyin.Repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TournamentService tournamentService;

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }

    public void saveAll(List<Member> members) {
        memberRepository.saveAll(members);
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> searchByName(String name) {
        return memberRepository.findByNameContaining(name);
    }

    public List<Member> searchByEmail(String email) {
        return memberRepository.findByEmailContaining(email);
    }

    public List<Member> searchByPhone(String phone) {
        return memberRepository.findByPhoneContaining(phone);
    }

    public List<Member> searchByMembershipType(String membershipType) {
        return memberRepository.findByMembershipTypeContaining(membershipType);
    }

    public List<Member> getMembersByIds(List<Long> ids) {
        return memberRepository.findAllById(ids);
    }

    public List<Member> getMembersByIdsForTournament(List<Long> memberIds) {
        return memberRepository.findAllById(memberIds);
    }
}