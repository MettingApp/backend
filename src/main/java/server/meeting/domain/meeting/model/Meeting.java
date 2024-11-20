package server.meeting.domain.meeting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import server.meeting.domain.member.model.Member;
import server.meeting.domain.record.model.Record;
import server.meeting.global.common.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEETING")
public class Meeting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Size(min = 8, max = 12)
    @Column(name = "date_column", length = 10, nullable = false)
    private LocalDate date;

    @Size(max = 3)
    @Column(name = "day_column", length = 5)
    private String day;

    @NotNull
    @Size(max = 1000)
    @Column(length = 1000)
    private String extraContent;

    @NotNull
    @Size(max = 5000)
    @Column(length = 5000, nullable = false)
    private String summary;

    @NotNull
    @Size(max = 500)
    @Column(length = 500)
    private String recommendKeyword;

    @NotNull
    @OneToOne(mappedBy = "meeting")
    @JoinColumn(name = "record_id")
    private Record record;

    @OneToMany(mappedBy = "meeting")
    private List<MeetingMembers> meetingMembers = new ArrayList<>();

    @Builder
    public Meeting(String title, LocalDate date, String extraContent){
        this.title = title;
        this.date = date;
        this.extraContent = extraContent;
    }
}
