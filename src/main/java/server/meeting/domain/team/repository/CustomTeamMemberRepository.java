package server.meeting.domain.team.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import server.meeting.domain.team.model.Team;

import java.util.List;

public interface CustomTeamMemberRepository {

    List<String> findMemberNicknameByTeamId(Long teamId);

    Page<Team> findTeamsByMemberId(Long memberId, Pageable pageable);
}
