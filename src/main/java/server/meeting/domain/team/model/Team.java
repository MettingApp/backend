package server.meeting.domain.team.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.meeting.domain.meeting.model.Meeting;
import server.meeting.domain.member.model.Member;
import server.meeting.global.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String title;

    private String description;

    private String inviteCode;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "id")
    private Member member;

    @OneToMany(mappedBy = "team")
    private List<Meeting> meetings= new ArrayList<>();

    public Team(Long id, String name, String title, String description) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
    }
}
