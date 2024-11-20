package server.meeting.domain.meeting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.meeting.domain.meeting.dto.MeetingCreateReq;
import server.meeting.domain.meeting.model.Meeting;
import server.meeting.domain.meeting.repository.MeetingRepository;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public void createMeeting(MeetingCreateReq req, MultipartFile file) {
        String d = req.getDate();
        LocalDate convertDate = LocalDate.parse(d);

        // 파일이 없어도 그대로 진행
        if(!file.isEmpty()){
        }

    }
}
