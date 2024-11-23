package server.meeting.domain.meeting.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.meeting.domain.meeting.dto.MeetingCreateReq;
import server.meeting.domain.meeting.dto.MeetingDetailResDto;
import server.meeting.domain.meeting.dto.MeetingResDto;
import server.meeting.domain.meeting.service.MeetingService;
import server.meeting.global.CurrentMember;
import server.meeting.global.success.SuccessResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public SuccessResponse<String> createMeetingWithFile(@RequestPart @Valid MeetingCreateReq req,
                                                         @RequestPart MultipartFile file,
                                                         @CurrentMember String username) {

        meetingService.createMeeting(req, file);
        return SuccessResponse.ok("meeting created");
    }

    @PostMapping("/no-record")
    public SuccessResponse<String> createMeetingWithOutFile(@RequestPart @Valid MeetingCreateReq req,
                                                            @CurrentMember String username) {

        meetingService.creatingMeetingWithOutFile(req);
        return SuccessResponse.ok("meeting created");
    }

    @GetMapping
    public SuccessResponse<List<MeetingResDto>> getMeetings(@RequestParam String teamId,
                                                            @CurrentMember String username) {

        List<MeetingResDto> meetings = meetingService.getMeetings(Long.valueOf(teamId));

        return new SuccessResponse<>(meetings);
    }

    @GetMapping("/detail")
    public SuccessResponse<MeetingDetailResDto> getMeetingDetail(@RequestParam String teamId,
                                                                 @RequestParam String meetingId,
                                                                 @CurrentMember String username) {
        MeetingDetailResDto meeting = meetingService.getMeetingDetail(Long.valueOf(teamId), Long.valueOf(meetingId));

        return new SuccessResponse<>(meeting);
    }
}
