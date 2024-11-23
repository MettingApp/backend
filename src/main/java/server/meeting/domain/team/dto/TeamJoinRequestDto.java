package server.meeting.domain.team.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TeamJoinRequestDto {

    @NotBlank(message = "초대 코드를 입력해주세요.")
    private String inviteCode;
}
