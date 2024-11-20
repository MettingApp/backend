package server.meeting.domain.meeting.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import server.meeting.domain.meeting.dto.MeetingCreateReq;
import server.meeting.domain.meeting.service.MeetingService;
import server.meeting.global.api.Api;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/meeting")
public class MeetingController {

    private final MeetingService meetingService;

//    @PostMapping
//    public Api<String> createMeeting(@RequestPart @Valid MeetingCreateReq req,
//                                     @RequestPart MultipartFile file) {
//
//    }
}
