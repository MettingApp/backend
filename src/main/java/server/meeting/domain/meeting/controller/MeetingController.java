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

    @PostMapping("/{id}")
    public SuccessResponse<String> createMeetingWithFile(@RequestPart @Valid MeetingCreateReq req,
                                                         @RequestPart MultipartFile file,
                                                         @CurrentMember String username,
                                                         @PathVariable(name = "id") String teamId) {

        meetingService.createMeeting(req, file, username, teamId);
        return SuccessResponse.ok("meeting created");
    }

    @PostMapping("/no-record/{id}")
    public SuccessResponse<String> createMeetingWithOutFile(@CurrentMember String username,
                                                            @RequestPart @Valid MeetingCreateReq req,
                                                            @PathVariable(name = "id") String teamId) {

        meetingService.creatingMeetingWithOutFile(req, username, teamId);
        return SuccessResponse.ok("meeting created");
    }

    @GetMapping("/{id}")
    public SuccessResponse<List<MeetingResDto>> getMeetings(@CurrentMember String username,
                                                            @PathVariable(name = "id") String teamId) {

        List<MeetingResDto> meetings = meetingService.getMeetings(username, teamId);

        return new SuccessResponse<>(meetings);
    }

    @GetMapping("/detail/{id}")
    public SuccessResponse<MeetingDetailResDto> getMeetingDetail(@RequestParam String meetingId,
                                                                 @CurrentMember String username,
                                                                 @PathVariable(name = "id") String teamId) {
        MeetingDetailResDto meeting = meetingService.getMeetingDetail(username, Long.valueOf(meetingId), teamId);

        return new SuccessResponse<>(meeting);
    }
}
