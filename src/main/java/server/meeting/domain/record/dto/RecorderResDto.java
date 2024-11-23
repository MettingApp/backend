package server.meeting.domain.record.dto;

import lombok.Getter;

@Getter
public class RecorderResDto {

    private String fileName;
    private String recordFile;

    public RecorderResDto(String fileName, String recordFile) {
        this.fileName = fileName;
        this.recordFile = recordFile;
    }
}