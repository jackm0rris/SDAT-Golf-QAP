package com.keyin.Repositories;

import com.keyin.Entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByNameContaining(String name);
    List<Member> findByEmailContaining(String email);
    List<Member> findByPhoneContaining(String phone);
    List<Member> findByMembershipTypeContaining(String membershipType);
}