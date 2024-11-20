package server.meeting.domain.meeting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import server.meeting.domain.member.model.Member;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Size(min = 8, max = 12)
    @Column(name = "date_column", length = 10, nullable = false)
    private LocalDate date;

    @NotNull
    @Size(max = 3)
    @Column(name = "day_column", length = 5, nullable = false)
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

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MeetingMembers> meetingMembers = new ArrayList<>();

}
