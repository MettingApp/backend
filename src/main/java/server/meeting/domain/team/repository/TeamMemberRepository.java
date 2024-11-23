package server.meeting.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.meeting.domain.member.model.Member;
import server.meeting.domain.team.model.Team;
import server.meeting.domain.team.model.TeamMember;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>, CustomTeamMemberRepository {
    boolean existsTeamMemberByMemberAndTeam(Member member, Team team);

}
