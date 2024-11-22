package server.meeting.domain.team.service;

import server.meeting.domain.team.dto.*;

public interface TeamService {
    TeamCreateResponseDto createTeam(String username, TeamCreateRequestDto teamCreateRequestDto);

    TeamJoinResponseDto joinTeam(String username, TeamJoinRequestDto teamJoinRequestDto);

    TeamJoinResponseDto getTeam(String username, Long teamId);

    TeamListResponseDto getTeamList(String username, int page);

    void modifyTeam(String username, Long teamId);

    void removeTeam(String username, Long teamId);

}
