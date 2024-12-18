package server.meeting.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private S3Service s3Service;

    @DisplayName("s3에 정상적으로 업로드되는지 확인한다.")
    @Test
    void s3Upload() throws MalformedURLException {
     //given
        MockMultipartFile mockFile = new MockMultipartFile(
                "test", "test-audio.wav", MediaType.MULTIPART_FORM_DATA_VALUE, "audio content".getBytes()
        );

        URL mockUrl = new URL("https://s3.amazonaws.com/bucket-name/test-audio.m4a");
        Mockito.lenient().when(s3Service.uploadFile(mockFile)).thenReturn(mockUrl);

        System.out.println("hi" + mockUrl.toString());

     //when


     //then
    }
}