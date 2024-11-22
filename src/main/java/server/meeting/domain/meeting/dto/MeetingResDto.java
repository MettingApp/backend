package server.meeting.domain.meeting.dto;

import lombok.Getter;
import server.meeting.domain.meeting.model.Meeting;

import java.time.LocalDate;

@Getter
public class MeetingResDto {

    private LocalDate localDate;
    private Long meetingId;

    public MeetingResDto(Meeting meeting) {
        this.localDate = localDate;
        this.meetingId = meetingId;
    }
}
