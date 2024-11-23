package server.meeting.domain.meeting.dto;

import lombok.Getter;
import server.meeting.domain.meeting.model.Meeting;
import server.meeting.domain.record.dto.RecorderResDto;

import java.time.LocalDate;

@Getter
public class MeetingResDto {

    private LocalDate localDate;
    private Long meetingId;

    public MeetingResDto(Meeting meeting) {
        this.localDate = meeting.getDate();
        this.meetingId = meeting.getId();
    }
}
