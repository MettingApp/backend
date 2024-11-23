package server.meeting.domain.meeting.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.meeting.domain.meeting.dto.MeetingCreateReq;
import server.meeting.domain.meeting.dto.MeetingDetailResDto;
import server.meeting.domain.meeting.dto.MeetingResDto;
import server.meeting.domain.meeting.service.MeetingService;
import server.meeting.global.api.Api;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public Api<String> createMeetingWithFile(@RequestPart @Valid MeetingCreateReq req,
                                     @RequestPart MultipartFile file) {

        meetingService.createMeeting(req, file);
        return Api.success("meeting created");
    }

    @PostMapping("/no-record")
    public Api<String> createMeetingWithOutFile(@RequestPart @Valid MeetingCreateReq req) {

        meetingService.creatingMeetingWithOutFile(req);
        return Api.success("meeting created");
    }

    @GetMapping
    public Api<List<MeetingResDto>> getMeetings(@RequestParam String teamId) {

        List<MeetingResDto> meetings = meetingService.getMeetings(Long.valueOf(teamId));

        return Api.success(meetings);
    }

    @GetMapping("/detail")
    public Api<MeetingDetailResDto> getMeetingDetail(@RequestParam String teamId,
                                                @RequestParam String meetingId) {
        MeetingDetailResDto meeting = meetingService.getMeetingDetail(Long.valueOf(teamId), Long.valueOf(meetingId));

        return Api.success(meeting);
    }
}
