package server.meeting.domain.team.service;

import server.meeting.domain.team.dto.TeamCreateRequestDto;
import server.meeting.domain.team.dto.TeamCreateResponseDto;
import server.meeting.domain.team.dto.TeamDetailResponse;
import server.meeting.domain.team.dto.TeamListResponseDto;

public interface TeamService {
    TeamCreateResponseDto createTeam(String username, TeamCreateRequestDto teamCreateRequestDto);

    TeamDetailResponse getTeam(String username, Long teamId);

    TeamListResponseDto getTeamList(String username, int page);

    void modifyTeam(String username, Long teamId);

    void removeTeam(String username, Long teamId);

}
