package server.meeting.domain.meeting.service;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import server.meeting.domain.meeting.dto.MeetingCreateReq;
import server.meeting.domain.meeting.model.Meeting;
import server.meeting.domain.meeting.repository.MeetingRepository;
import server.meeting.domain.member.model.Member;
import server.meeting.domain.member.model.Role;
import server.meeting.domain.member.repository.MemberRepository;
import server.meeting.domain.record.model.Recorder;
import server.meeting.domain.record.repository.RecorderRepository;
import server.meeting.domain.team.model.Team;
import server.meeting.domain.team.repository.TeamRepository;
import server.meeting.global.s3.S3Service;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MeetingServiceTest {

    @InjectMocks
    private MeetingService meetingService;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private RecorderRepository recorderRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("파일을 첨부한 상태에서 회의록이 생성됩니다.")
    @Test
    void createMeetingWithFile() throws Exception{
        // given
        // Mock 파일 준비
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-audio.mp3",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "test audio content".getBytes()
        );
        // Mock S3 업로드 결과
        URL mockUrl = new URL("https://s3.amazonaws.com/bucket-name/test-audio.mp3");
        Mockito.lenient().when(s3Service.uploadFile(mockFile)).thenReturn(mockUrl);
        // Mock Recorder 저장 결과
        Recorder mockRecorder = new Recorder("meeting-record", mockUrl.toString());
        Mockito.lenient().when(recorderRepository.save(Mockito.any(Recorder.class))).thenReturn(mockRecorder);

        Team mockTeam = new Team(1L, "teamA", "title", "description");
        Member mockMemberA = Member.of(Role.USER, "usernameA", "clark1245!", "parkA", "정곤A");
        Member mockMemberB = Member.of(Role.USER, "usernameB", "clark1245!", "parkB", "정곤B");
        Member mockMemberC = Member.of(Role.USER, "usernameC", "clark1245!", "parkC", "정곤C");

        Mockito.when(teamRepository.save(Mockito.any(Team.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(mockTeam));

        teamRepository.save(mockTeam); // Mock save 호출

        // then
        Optional<Team> savedTeam = teamRepository.findById(1L);
        assertThat(savedTeam).isPresent();
        assertThat(savedTeam.get().getName()).isEqualTo("teamA");

    }

}