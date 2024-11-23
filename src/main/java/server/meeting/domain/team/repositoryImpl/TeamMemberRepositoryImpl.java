package server.meeting.domain.team.repositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import server.meeting.domain.meeting.model.QMeetingMembers;
import server.meeting.domain.member.model.QMember;
import server.meeting.domain.team.model.QTeam;
import server.meeting.domain.team.model.QTeamMember;
import server.meeting.domain.team.model.Team;
import server.meeting.domain.team.model.TeamMember;
import server.meeting.domain.team.repository.CustomTeamMemberRepository;
import server.meeting.domain.team.repository.TeamMemberRepository;

import java.util.ArrayList;
import java.util.List;

import static server.meeting.domain.member.model.QMember.member;
import static server.meeting.domain.team.model.QTeam.team;


@Repository
public class TeamMemberRepositoryImpl implements CustomTeamMemberRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public TeamMemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<String> findMemberNicknameByMemberId(Long teamId) {
        QTeamMember qTeamMember = QTeamMember.teamMember;
        QMember qMember = member;
        QTeam qTeam = team;

        return jpaQueryFactory
                .select(member.nickname)
                .from(qTeamMember)
                .where(team.id.eq(teamId))
                .join(qTeamMember.member, member)
                .join(qTeamMember.team, team)
                .fetch();

    }

    @Override
    public Page<Team> findTeamsByMemberId(Long memberId, Pageable pageable) {
        QTeamMember qTeamMember = QTeamMember.teamMember;
        QMember qMember = member;
        QTeam qTeam = team;

        List<Team> content = jpaQueryFactory
                .select(qTeam)
                .from(qTeamMember)
                .where(qTeamMember.member.id.eq(memberId))
                .join(qTeamMember.team, qTeam)
                .join(qTeamMember.member, qMember)
                .offset(pageable.getOffset()) // 시작 인덱스
                .limit(pageable.getPageSize()) // 가져올 데이터 수
                .fetch();


        return new PageImpl<>(content);
    }
}
