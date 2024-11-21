package server.meeting.domain.member.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.meeting.domain.meeting.model.MeetingMembers;
import server.meeting.domain.team.model.Team;
import server.meeting.global.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @OneToMany(mappedBy = "member")
    private List<MeetingMembers> meetingMembers = new ArrayList<>();

    private Member(Role role, String username, String password, String nickname, String name) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
    }

    public static Member of(Role role, String username, String password, String nickname, String name) {
        return new Member(role, username, password, nickname, name);
    }
}
