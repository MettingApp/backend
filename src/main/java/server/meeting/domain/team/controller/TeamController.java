package server.meeting.domain.team.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import server.meeting.domain.team.dto.*;
import server.meeting.domain.team.service.TeamService;
import server.meeting.global.CurrentMember;
import server.meeting.global.success.SuccessResponse;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team")
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<TeamCreateResponseDto> createTeam(@CurrentMember String username,
                                                             @Valid @RequestBody TeamCreateRequestDto requestBody,
                                                             HttpServletResponse response) {

        TeamCreateResponseDto responseBodyDto = teamService.createTeam(username, requestBody);
        URI location = UriComponentsBuilder.fromPath("/api/v1/team/{id}")
                .buildAndExpand(responseBodyDto.getTeamId())
                .toUri();

        response.setHeader("Location", location.toString());

        return new SuccessResponse<>(responseBodyDto);
    }

    @PostMapping("/join")
    public SuccessResponse<TeamJoinResponseDto> joinTeam(@CurrentMember String username, @Valid @RequestBody TeamJoinRequestDto requestBody){

        return new SuccessResponse<>(teamService.joinTeam(username, requestBody));
    }

    @GetMapping("/{id}")
    public SuccessResponse<TeamDetailResponseDto> getTeam(@CurrentMember String username, @PathVariable("id") Long teamId) {
        return new SuccessResponse<>(teamService.getTeam(username, teamId));
    }

    @GetMapping
    public SuccessResponse<Page<TeamListResponseDto>> getTeamList(@CurrentMember String username,
                                                                 @PageableDefault(size = 5) Pageable pageable) {
        return new SuccessResponse<>(teamService.getTeamList(username, pageable));
    }

    @PatchMapping("{id}")
    SuccessResponse<?> modifyTeam(@CurrentMember String username, @PathVariable("id") Long teamId) {
        teamService.modifyTeam(username, teamId);
        return new SuccessResponse<>(null);
    }

    @DeleteMapping("{id}")
    public SuccessResponse<TeamCreateResponseDto> removeTeam(@CurrentMember String username, @PathVariable("id") Long teamId) {
        teamService.removeTeam(username, teamId);
        return new SuccessResponse<>(null);
    }
}
