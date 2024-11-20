package server.meeting.domain.meeting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class MeetingCreateReq {

    private String date;
    private String extraContent;
    private String title;
    private String fileName;
}
