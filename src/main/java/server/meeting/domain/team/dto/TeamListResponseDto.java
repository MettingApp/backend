package server.meeting.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class TeamListResponseDto {

    private Long id;
    private String name;
    private String title;
    private List<String> members;
}
