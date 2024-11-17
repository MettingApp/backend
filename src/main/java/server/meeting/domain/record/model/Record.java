package server.meeting.domain.record.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.context.annotation.Lazy;
import server.meeting.domain.meeting.model.Meeting;
import server.meeting.global.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 300)
    @Column(length = 300, nullable = false)
    private String fileName;

    @NotNull
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String originalFileName;

    @NotNull
    @Column(length = 2083, nullable = false)
    private String recordFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meetingId")
    private Meeting meeting;

    public Record(String fileName, String originalFileName, String recordFile) {
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.recordFile = recordFile;
    }
}
