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

    @Builder
    private TeamCreateResponseDto(Long teamId) {
        this.teamId = teamId;
    }

    public static TeamCreateResponseDto toDtoFrom(Team team) {

        return TeamCreateResponseDto.builder()
                .teamId(team.getId())
                .build();
    }
}
