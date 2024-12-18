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
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;


    /* --------------------------------- create ---------------------------------- */
    @Transactional(readOnly = false, rollbackFor = ApiException.class)
    public void createMeeting (MeetingCreateReq req, MultipartFile file, String username, String teamId) {
        String d = req.getDate();
        LocalDate convertDate = convertDate(d);

        URL url = s3Service.uploadFile(file);
        String recordUrl = url.toString();
        Recorder record = new Recorder(req.getFileName(), recordUrl);
        recorderRepository.save(record);

        Team team = findIfExists(teamId);
        Meeting meeting = Meeting.withFile(req.getTitle(), convertDate, req.getExtraContent(), record, team);
        addMembersToMeeting(req.getMembers(), meeting);
        meetingRepository.save(meeting);
    }

    @Transactional(readOnly = false, rollbackFor = ApiException.class)
    public void creatingMeetingWithOutFile (MeetingCreateReq req, String username, String teamId) {
        String d = req.getDate();
        LocalDate convertDate = convertDate(d);

        Team team = findIfExists(teamId);
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

    /* --------------------------------- Read ---------------------------------- */
    public List<MeetingResDto> getMeetings(String username, String teamId){
        Team team = findIfExists(teamId);

        List<Meeting> meetings = meetingRepository.selectMeetingList();
        return meetings.stream()
                .map(MeetingResDto::new)
                .toList();
    }

    public MeetingDetailResDto getMeetingDetail(String username, Long meetingId, String teamId) {
        Team team = findIfExists(teamId);

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ApiException(ErrorType._NOT_FOUND_MEETING));

        Recorder recorder = meeting.getRecorder();
        if(recorder != null){
            RecorderResDto recorderDto = new RecorderResDto(recorder.getFileName(), recorder.getRecordFile());

            MeetingDetailResDto resDto = new MeetingDetailResDto(meeting.getTitle(), meeting.getDate(),
                    meeting.getExtraContent(), meeting.getSummary(), recorderDto);

            return resDto;
        }

        return new MeetingDetailResDto(meeting.getTitle(), meeting.getDate(),
                meeting.getExtraContent(), meeting.getSummary(), new RecorderResDto("null", "null"));
    }

    /* --------------------------------- Update ---------------------------------- */

    public void updateMeeting(String username, Long meetingId){

    }

    /* --------------------------------- Delete ---------------------------------- */


    private Team findIfExists(String teamId) {
        Long id = Long.valueOf(teamId);
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorType._NOT_FOUND_TEAM));

        return team;
    }
}
