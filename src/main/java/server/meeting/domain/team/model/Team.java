package server.meeting.domain.team.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.meeting.domain.member.model.Member;
import server.meeting.global.common.BaseEntity;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, unique = true)
    private String inviteCode;

    @OneToMany(fetch = LAZY, mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    private Long masterId;

    public static Team of(String name, String title, String description, Member member) {
        Team team = Team.builder()
                .name(name)
                .title(title)
                .description(description)
                .build();

        team.connectMember(member);
        team.generateInviteCode();
        team.appointMasterTo(member);

        return team;
    }

    @Builder
    private Team(String name, String title, String description) {
        this.name = name;
        this.title = title;
        this.description = description;
    }

    public void connectMember(Member member) {
        if(member.getTeam() != null){
            return;
        }
        members.add(member);
        member.linkTeam(this);
    }

    private void generateInviteCode() {
        this.inviteCode = UUID.randomUUID().toString();
    }

    public void appointMasterTo(Member member) {
        this.masterId = member.getId();
    }

    public boolean isSameInviteCodeWith(String inviteCode) {
        return this.inviteCode.equals(inviteCode);
    }
}
