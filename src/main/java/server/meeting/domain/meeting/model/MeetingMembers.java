package server.meeting.domain.meeting.model;

import jakarta.persistence.*;
import lombok.*;
import server.meeting.domain.member.model.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public MeetingMembers(Meeting meeting, Member member){
        this.meeting = meeting;
        this.member = member;
    }

    public MeetingMembers toEntity(Meeting meeting, Member member) {
        return new MeetingMembers(meeting, member);
    }
}
