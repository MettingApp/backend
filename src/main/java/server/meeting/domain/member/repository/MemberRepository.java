package server.meeting.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.meeting.domain.member.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
