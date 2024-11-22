package server.meeting.domain.team.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamCreateRequestDto {
    @NotBlank(message = "팀명은 필수 입니다.")
    private String name;
    @NotBlank(message = "소제목은 필수 입니다.")
    private String title;
    @NotBlank(message = "팀 설명은 필수 입니다.")
    private String description;
}
