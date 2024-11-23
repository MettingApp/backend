package server.meeting.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeamDetailResponseDto {

    private String name;
    private String title;
    private String description;
    private List<String> members;

}
