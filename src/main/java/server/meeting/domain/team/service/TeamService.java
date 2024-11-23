package server.meeting.domain.team.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import server.meeting.domain.team.dto.*;

public interface TeamService {
    TeamCreateResponseDto createTeam(String username, TeamCreateRequestDto teamCreateRequestDto);

    TeamJoinResponseDto joinTeam(String username, TeamJoinRequestDto teamJoinRequestDto);

    TeamDetailResponseDto getTeam(String username, Long teamId);

    Page<TeamListResponseDto> getTeamList(String username, Pageable pageable);

    void modifyTeam(String username, Long teamId);

    void removeTeam(String username, Long teamId);

}
