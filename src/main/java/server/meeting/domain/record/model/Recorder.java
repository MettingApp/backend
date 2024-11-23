package server.meeting.domain.record.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.meeting.domain.meeting.model.Meeting;
import server.meeting.global.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recorder extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자가 저장한 이름
    @NotNull
    @Size(max = 300)
    @Column(length = 300, nullable = false)
    private String fileName;

    // s3에 저장된 이름
    @NotNull
    @Column(length = 2083, nullable = false)
    private String recordFile;

    @OneToOne(fetch = FetchType.LAZY)
    private Meeting meeting;

    @Builder
    public Recorder(String fileName, String recordFile) {
        this.fileName = fileName;
        this.recordFile = recordFile;
    }
}
