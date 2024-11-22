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
import server.meeting.domain.record.model.Recorder;
import server.meeting.domain.record.repository.RecorderRepository;
import server.meeting.global.s3.S3Service;

import java.net.URL;
import java.time.LocalDate;

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
    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @DisplayName("파일을 첨부한 상태에서 회의록이 생성됩니다.")
//    @Test
//    void createMeetingWithFile() throws Exception{
//        // Mock 파일 준비
//        MockMultipartFile mockFile = new MockMultipartFile(
//                "file",
//                "test-audio.m4a",
//                MediaType.MULTIPART_FORM_DATA_VALUE,
//                "test audio content".getBytes()
//        );
//
//        // Mock S3 업로드 결과
//        URL mockUrl = new URL("https://s3.amazonaws.com/bucket-name/test-audio.m4a");
//        Mockito.when(s3Service.uploadFile(mockFile)).thenReturn(mockUrl);
//
//        // Mock Recorder 저장 결과
//        Recorder mockRecorder = new Recorder("meeting-record", mockUrl.toString());
//        Mockito.when(recorderRepository.save(Mockito.any(Recorder.class))).thenReturn(mockRecorder);
//
//        // Mock Meeting 저장 결과
//        Meeting mockMeeting = Meeting.withFile(
//                "Team Meeting",
//                LocalDate.parse("2024-11-21"),
//                "Weekly team meeting",
//                mockRecorder
//        );
//        Mockito.when(meetingRepository.save(Mockito.any(Meeting.class))).thenReturn(mockMeeting);
//
//        // When
//        MeetingCreateReq req = new MeetingCreateReq("2022-10-11","안녕하세요","1차 회의","basic_file");
//        meetingService.createMeeting(req, mockFile);
//
//        // Then
//        // Recorder 저장 검증
//        ArgumentCaptor<Recorder> recorderCaptor = ArgumentCaptor.forClass(Recorder.class);
//        Mockito.verify(recorderRepository, Mockito.times(1)).save(recorderCaptor.capture());
//        Recorder savedRecorder = recorderCaptor.getValue();
//        assertEquals("meeting-record", savedRecorder.getFileName());
//
//        // Meeting 저장 검증
//        ArgumentCaptor<Meeting> meetingCaptor = ArgumentCaptor.forClass(Meeting.class);
//        Mockito.verify(meetingRepository, Mockito.times(1)).save(meetingCaptor.capture());
//        Meeting savedMeeting = meetingCaptor.getValue();
//        assertEquals("Team Meeting", savedMeeting.getTitle());
//        assertEquals(LocalDate.parse("2024-11-21"), savedMeeting.getDate());
//        assertEquals("Weekly team meeting", savedMeeting.getExtraContent());
//        assertEquals(savedRecorder, savedMeeting.getRecorder());
//
//    }
}