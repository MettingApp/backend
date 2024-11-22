package server.meeting.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.meeting.domain.member.repository.MemberRepository;
import server.meeting.domain.team.dto.TeamCreateRequestDto;
import server.meeting.domain.team.dto.TeamCreateResponseDto;
import server.meeting.domain.team.dto.TeamDetailResponse;
import server.meeting.domain.team.dto.TeamListResponseDto;
import server.meeting.domain.team.repository.TeamRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;


    @Override
    public TeamCreateResponseDto createTeam(String username, TeamCreateRequestDto teamCreateRequestDto) {

        return null;
    }

    @Override
    public TeamDetailResponse getTeam(String username, Long teamId) {
        return null;
    }

    @Override
    public TeamListResponseDto getTeamList(String username, int page) {
        return null;
    }

    @Override
    public void modifyTeam(String username, Long teamId) {

    }

    @Override
    public void removeTeam(String username, Long teamId) {

    }
}
