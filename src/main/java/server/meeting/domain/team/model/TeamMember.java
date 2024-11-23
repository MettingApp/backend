package server.meeting.domain.team.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.meeting.domain.member.model.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamMemberId;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Team team;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @Builder
    private TeamMember(Team team, Member member) {
        this.team = team;
        this.member = member;
    }

    public static TeamMember of(Team team, Member member) {
        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .member(member)
                .build();

        teamMember.connectTeam(team);
        teamMember.connectMember(member);

        return teamMember;
    }

    public void connectMember(Member member) {
        if (this.member == null) {
            return;
        }
        member.addTeamMember(this);
    }

    public void connectTeam(Team team) {
        if (this.team == null) {
            return;
        }
        team.addTeamMember(this);
    }

}
