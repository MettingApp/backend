package server.meeting.domain.team.service;

import com.amazonaws.services.kms.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.meeting.domain.member.model.Member;
import server.meeting.domain.member.repository.MemberRepository;
import server.meeting.domain.team.dto.TeamCreateRequestDto;
import server.meeting.domain.team.dto.TeamCreateResponseDto;
import server.meeting.domain.team.dto.TeamDetailResponse;
import server.meeting.domain.team.dto.TeamListResponseDto;
import server.meeting.domain.team.model.Team;
import server.meeting.domain.team.repository.TeamRepository;
import server.meeting.global.error.ErrorType;
import server.meeting.global.exception.ApiException;

import java.util.IllformedLocaleException;

import static server.meeting.global.error.ErrorType._NOT_FOUND_MEMBER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;


    @Override
    public TeamCreateResponseDto createTeam(String username, TeamCreateRequestDto dto) {
        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new ApiException(_NOT_FOUND_MEMBER));

        Team team = Team.of(dto.getName(), dto.getTitle(), dto.getDescription(), member);
        teamRepository.save(team);

        return TeamCreateResponseDto.toDtoFrom(team);
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
