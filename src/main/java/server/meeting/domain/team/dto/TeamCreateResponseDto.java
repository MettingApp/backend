package server.meeting.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.meeting.domain.team.model.Team;

@Getter
@NoArgsConstructor
public class TeamCreateResponseDto {
    private Long teamId;
    private String inviteCode;

    @Builder
    private TeamCreateResponseDto(Long teamId, String inviteCode) {
        this.teamId = teamId;
        this.inviteCode = inviteCode;
    }

    public static TeamCreateResponseDto toDtoFrom(Team team) {

        return TeamCreateResponseDto.builder()
                .teamId(team.getId())
                .inviteCode(team.getInviteCode())
                .build();
    }
}
