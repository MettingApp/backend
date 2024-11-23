package server.meeting.domain.meeting.dto;

import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class MeetingCreateReq {

    private String date;
    private String extraContent;
    private String title;
    private String fileName;
    private String category;
    private List<String> members;
}
