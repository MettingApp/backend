package server.meeting.domain.team.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import server.meeting.domain.team.dto.TeamCreateRequestDto;
import server.meeting.domain.team.dto.TeamCreateResponseDto;
import server.meeting.domain.team.dto.TeamDetailResponse;
import server.meeting.domain.team.dto.TeamListResponseDto;
import server.meeting.domain.team.service.TeamService;
import server.meeting.global.api.Api;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team")
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Api<?> createTeam(@AuthenticationPrincipal String username,
                             @Valid @RequestBody TeamCreateRequestDto requestBody,
                             HttpServletResponse response) {

        TeamCreateResponseDto responseBodyDto = teamService.createTeam(username, requestBody);
        URI location = UriComponentsBuilder.fromPath("/api/v1/team/{id}")
                .buildAndExpand(responseBodyDto.getTeamId())
                .toUri();

        response.setHeader("Location", location.toString());

        return Api.success(null);
    }

    @GetMapping("/{id}")
    Api<TeamDetailResponse> getTeam(@AuthenticationPrincipal String username, @PathVariable("id") Long teamId) {
        return Api.success(teamService.getTeam(username, teamId));
    }

    @GetMapping
    Api<TeamListResponseDto> getTeamList(@AuthenticationPrincipal String username,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        return Api.success(teamService.getTeamList(username, page));
    }

    @PatchMapping("{id}")
    Api<?> modifyTeam(@AuthenticationPrincipal String username, @PathVariable("id") Long teamId) {
        teamService.modifyTeam(username, teamId);
        return Api.success(null);
    }

    @DeleteMapping("{id}")
    Api<TeamCreateResponseDto> removeTeam(@AuthenticationPrincipal String username, @PathVariable("id") Long teamId) {
        teamService.removeTeam(username, teamId);
        return Api.success(null);
    }
}
