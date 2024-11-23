package server.meeting.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.meeting.domain.member.model.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUsername(String username);
    @Query("select m from Member m left join fetch m.team where m.username = :username ")
    Optional<Member> findMemberByUsernameWithTeam(@Param("username") String username);
    boolean existsMemberByUsername(String username);
    boolean existsMemberByNickname(String name);
}
