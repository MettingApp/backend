package server.meeting.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import server.meeting.domain.record.dto.RecorderResDto;

import java.io.File;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class MeetingDetailResDto {

    private String title;
    private LocalDate date;
    private String extraContent;
    private String summary;
    private RecorderResDto recorder;
}
