package server.meeting.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import server.meeting.domain.record.dto.RecorderResDto;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class MeetingDetailResDto {

    private String title;
    private LocalDate date;
    private String extraContent;
    private String summary;
    private RecorderResDto recorder;

    public void withNoRecord(String title, LocalDate date, String extraContent, String summary) {
        this.title = title;
        this.date = date;
        this.extraContent = extraContent;
        this.summary = summary;
    }
}
