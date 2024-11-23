package server.meeting.domain.team.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import server.meeting.domain.team.dto.*;
import server.meeting.domain.team.service.TeamService;
import server.meeting.global.CurrentMember;
import server.meeting.global.api.Api;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team")
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Api<TeamCreateResponseDto> createTeam(@CurrentMember String username,
                             @Valid @RequestBody TeamCreateRequestDto requestBody,
                             HttpServletResponse response) {

        TeamCreateResponseDto responseBodyDto = teamService.createTeam(username, requestBody);
        URI location = UriComponentsBuilder.fromPath("/api/v1/team/{id}")
                .buildAndExpand(responseBodyDto.getTeamId())
                .toUri();

        response.setHeader("Location", location.toString());

        return Api.success(responseBodyDto);
    }

    @PostMapping("/join")
    public Api<TeamJoinResponseDto> joinTeam(@CurrentMember String username, @Valid @RequestBody TeamJoinRequestDto requestBody){

        return Api.success(teamService.joinTeam(username, requestBody));
    }

    @GetMapping("/{id}")
    Api<TeamJoinResponseDto> getTeam(@CurrentMember String username, @PathVariable("id") Long teamId) {
        return Api.success(teamService.getTeam(username, teamId));
    }

    @GetMapping
    Api<TeamListResponseDto> getTeamList(@CurrentMember String username,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        return Api.success(teamService.getTeamList(username, page));
    }

    @PatchMapping("{id}")
    Api<?> modifyTeam(@CurrentMember String username, @PathVariable("id") Long teamId) {
        teamService.modifyTeam(username, teamId);
        return Api.success(null);
    }

    @DeleteMapping("{id}")
    Api<TeamCreateResponseDto> removeTeam(@CurrentMember String username, @PathVariable("id") Long teamId) {
        teamService.removeTeam(username, teamId);
        return Api.success(null);
    }
}
