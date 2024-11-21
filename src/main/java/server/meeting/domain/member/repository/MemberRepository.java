package server.meeting.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.meeting.domain.member.model.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUsername(String username);

    boolean existsMemberByUsername(String username);
    boolean existsMemberByName(String name);
}
