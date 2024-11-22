package server.meeting.domain.team.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.meeting.domain.member.model.Member;
import server.meeting.global.common.BaseEntity;

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


    public static Team of(String name, String title, String description, String inviteCode, Member member) {
        Team team = Team.builder()
                .name(name)
                .title(title)
                .description(description)
                .inviteCode(inviteCode)
                .build();

        team.connectMember(member);

        return team;
    }

    @Builder
    private Team(String name, String title, String description, String inviteCode, Member member) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.inviteCode = inviteCode;
        this.member = member;
    }

    public void connectMember(Member member) {
        if (this.member == null) {
            return;
        }
        this.member = member;
        member.linkTeam(this);
    }
}
