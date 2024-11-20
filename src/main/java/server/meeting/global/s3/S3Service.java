package server.meeting.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import server.meeting.global.error.ErrorType;
import server.meeting.global.exception.ApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Component
@Service
public class S3Service {

    private final String bucketName;
    private final AmazonS3 amazonS3Client;

    public S3Service(@Value("${cloud.aws.s3.bucket") String bucketName, AmazonS3 amazonS3Client) {
        this.bucketName = bucketName;
        this.amazonS3Client = amazonS3Client;
    }

    public URL uploadFile(MultipartFile file) {
        String key = file.getOriginalFilename();
        key = checkIfExists(key);

        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = setMetaData(file);

            PutObjectRequest request = new PutObjectRequest(bucketName, key, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3Client.putObject(request);
            return getImageUrl(key);
        } catch (IOException e) {
            throw new ApiException(ErrorType._S3_NOT_EXISTS_KEY);
        }
    }

    public void deleteImage(String key) {
        try {
            amazonS3Client.deleteObject(bucketName, key);
        } catch (Exception e) {
            throw new ApiException(ErrorType._S3_NOT_EXISTS_KEY);
        }
    }

    public URL getImageUrl(String key) {
        try {
            return amazonS3Client.getUrl(bucketName, key);
        } catch (Exception e) {
            throw new ApiException(ErrorType._S3_NOT_EXISTS_KEY);
        }
    }

    private ObjectMetadata setMetaData(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        return metadata;
    }

    private String checkIfExists(String fileName) {
        if(!StringUtils.hasText(fileName)) {
            return UUID.randomUUID() + getFileExtension(fileName);
        }
        return fileName;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if(dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex);
        }
        return ""; // 확장자 없음
    }
}
