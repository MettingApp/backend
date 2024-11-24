package server.meeting.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.meeting.domain.member.model.Member;
import server.meeting.domain.member.repository.MemberRepository;
import server.meeting.domain.team.dto.*;
import server.meeting.domain.team.model.Team;
import server.meeting.domain.team.model.TeamMember;
import server.meeting.domain.team.repository.TeamMemberRepository;
import server.meeting.domain.team.repository.TeamRepository;
import server.meeting.global.exception.ApiException;
import server.meeting.global.exception.ErrorType;

import java.util.List;

import static server.meeting.global.exception.ErrorType.*;
import static server.meeting.global.exception.ErrorType._NOT_FOUND_MEMBER;
import static server.meeting.global.exception.ErrorType._NOT_FOUND_TEAM;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;


    /*@Override
    public TeamCreateResponseDto createTeam(String username, TeamCreateRequestDto dto) {
        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new ApiException(_NOT_FOUND_MEMBER));

        Team team = Team.of(dto.getName(), dto.getTitle(), dto.getDescription(), member);
        teamRepository.save(team);


        return TeamCreateResponseDto.toDtoFrom(team);
    }

    @Override
    public TeamJoinResponseDto joinTeam(String username, TeamJoinRequestDto dto) {
        Member member = memberRepository.findMemberByUsernameWithTeam(username)
                .orElseThrow(() -> new ApiException(_NOT_FOUND_MEMBER));

        Team team = teamRepository.findTeamByInviteCode(dto.getInviteCode())
                .orElseThrow(() -> new ApiException(_NOT_FOUND_TEAM));

        // 멤버가 속한
        if(member.getTeam() != null && member.getTeam() == team){
            throw new ApiException(_NOT_FOUND_TEAM);
        }

        if(team.isSameInviteCodeWith(dto.getInviteCode())){
            throw new ApiException(_NOT_FOUND_TEAM);
        }
        team.connectMember(member);

        return TeamJoinResponseDto.builder()
                .addedMemberId(member.getId())
                .build();
    }*/

    @Transactional
    @Override
    public TeamCreateResponseDto createTeam(String username, TeamCreateRequestDto dto) {
        // 팀 생성자(Member) 조회
        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new ApiException(_NOT_FOUND_MEMBER));

        // 팀 생성
        Team team = Team.of(dto.getName(), dto.getTitle(), dto.getDescription(), member);
        teamRepository.save(team);

        //팀 생성자(팀장)를 TeamMember로 등록
        TeamMember teamMember = TeamMember.of(team, member);
        teamMemberRepository.save(teamMember);

        return TeamCreateResponseDto.toDtoFrom(team);
    }

    @Transactional
    @Override
    public TeamJoinResponseDto joinTeam(String username, TeamJoinRequestDto dto) {
        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new ApiException(_NOT_FOUND_MEMBER));

        Team team = teamRepository.findTeamByInviteCode(dto.getInviteCode())
                .orElseThrow(() -> new ApiException(_NOT_FOUND_TEAM));

        if (teamMemberRepository.existsTeamMemberByMemberAndTeam(member, team)) {
            throw new ApiException(_ALREADY_JOINED_TEAM);
        }

        if (!team.isSameInviteCodeWith(dto.getInviteCode())) {
            throw new ApiException(ErrorType._BAD_REQUEST_INVITE_CODE);
        }

        // TeamMember 생성 및 저장
        TeamMember teamMember = TeamMember.of(team, member);
        teamMemberRepository.save(teamMember);

        return TeamJoinResponseDto.builder()
                .addedMemberId(member.getId())
                .build();
    }

    @Override
    public TeamDetailResponseDto getTeam(String username, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException(_NOT_FOUND_TEAM));

        List<String> nicknames = teamMemberRepository.findMemberNicknameByTeamId(teamId);

        return new TeamDetailResponseDto(team.getName(), team.getTitle(), team.getDescription(), nicknames);
    }

    @Override
    public Page<TeamListResponseDto> getTeamList(String username, Pageable pageable) {
        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new ApiException(_NOT_FOUND_MEMBER));


        Page<Team> teamList = teamMemberRepository.findTeamsByMemberId(member.getId(), pageable);
        Page<TeamListResponseDto> result = teamList.map(team -> {
            List<String> nicknames = teamMemberRepository.findMemberNicknameByTeamId(team.getId());
            return new TeamListResponseDto(team.getId(), team.getName(), team.getTitle(), nicknames);
        });
        return result;
    }

    @Override
    public void modifyTeam(String username, Long teamId) {

    }

    @Override
    public void removeTeam(String username, Long teamId) {

    }
}
