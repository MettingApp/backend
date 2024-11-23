package server.meeting.domain.team.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamJoinResponseDto {

    private Long addedMemberId;

    @Builder
    public TeamJoinResponseDto(Long addedMemberId) {
        this.addedMemberId = addedMemberId;
    }
}
