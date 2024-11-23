package server.meeting.domain.meeting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.meeting.domain.meeting.dto.MeetingCreateReq;
import server.meeting.domain.meeting.dto.MeetingDetailResDto;
import server.meeting.domain.meeting.dto.MeetingResDto;
import server.meeting.domain.meeting.model.Meeting;
import server.meeting.domain.meeting.model.MeetingMembers;
import server.meeting.domain.meeting.repository.MeetingMembersRepository;
import server.meeting.domain.meeting.repository.MeetingRepository;
import server.meeting.domain.member.model.Member;
import server.meeting.domain.member.repository.MemberRepository;
import server.meeting.domain.record.dto.RecorderResDto;
import server.meeting.domain.record.model.Recorder;
import server.meeting.domain.record.repository.RecorderRepository;
import server.meeting.domain.team.model.Team;
import server.meeting.domain.team.repository.TeamRepository;
import server.meeting.global.exception.ErrorType;
import server.meeting.global.exception.ApiException;
import server.meeting.global.s3.S3Service;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class MeetingService {

    private final S3Service s3Service;
    private final MeetingRepository meetingRepository;
    private final RecorderRepository recorderRepository;
    private final MeetingMembersRepository meetingMembersRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;


    /* --------------------------------- create ---------------------------------- */
    @Transactional(readOnly = false, rollbackFor = ApiException.class)
    public void createMeeting (MeetingCreateReq req, MultipartFile file) {
        String d = req.getDate();
        LocalDate convertDate = convertDate(d);

        URL url = s3Service.uploadFile(file);
        String recordUrl = url.toString();
        Recorder record = new Recorder(req.getFileName(), recordUrl);
        recorderRepository.save(record);

        Team team = findIfExists(req.getTeamId());
        Meeting meeting = Meeting.withFile(req.getTitle(), convertDate, req.getExtraContent(), record, team);
        addMembersToMeeting(req.getMembers(), meeting);
        meetingRepository.save(meeting);
    }

    @Transactional(readOnly = false, rollbackFor = ApiException.class)
    public void creatingMeetingWithOutFile (MeetingCreateReq req) {
        String d = req.getDate();
        LocalDate convertDate = convertDate(d);

        Team team = findIfExists(req.getTeamId());
        Meeting meeting = Meeting.withoutFile(req.getTitle(), convertDate, req.getExtraContent(), team);
        addMembersToMeeting(req.getMembers(), meeting);
        meetingRepository.save(meeting);
    }

    private LocalDate convertDate(String date){
        return LocalDate.parse(date);
    }

    // 최적화 필요!!
    private void addMembersToMeeting (List<String> nickNames, Meeting meeting) {
        List<Member> members = nickNames.stream().map(nickName ->{
            return memberRepository.findMemberByUsername(nickName)
                    .orElseThrow(() -> new ApiException(ErrorType._NOT_FOUND_MEMBER));
        }).toList();

        for(Member member : members){
            MeetingMembers meetingMember = new MeetingMembers(meeting, member);
            meetingMembersRepository.save(meetingMember);
        }
    }

    private Team findIfExists(String teamId) {
        return teamRepository.findById(Long.valueOf(teamId))
                .orElseThrow(() -> new ApiException(ErrorType._NOT_FOUND_TEAM));
    }

    /* --------------------------------- Read ---------------------------------- */
    public List<MeetingResDto> getMeetings(Long teamId){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException(ErrorType._NOT_FOUND_TEAM));

        List<Meeting> meetings = meetingRepository.selectMeetingList();
        return meetings.stream()
                .map(MeetingResDto::new)
                .toList();
    }

    public MeetingDetailResDto getMeetingDetail(Long teamId, Long meetingId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException(ErrorType._NOT_FOUND_TEAM));

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ApiException(ErrorType._NOT_FOUND_MEETING));

        Recorder recorder = meeting.getRecorder();
        RecorderResDto recorderDto = new RecorderResDto(recorder.getFileName(), recorder.getRecordFile());

        MeetingDetailResDto resDto = new MeetingDetailResDto(meeting.getTitle(), meeting.getDate(),
                meeting.getExtraContent(), meeting.getSummary(), recorderDto);

        return resDto;
    }

    /* --------------------------------- Update ---------------------------------- */

//    public void updateMeeting(Long teamId, Long meetingId){
//
//    }

    /* --------------------------------- Delete ---------------------------------- */


}
