package server.meeting.domain.meeting.repositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import server.meeting.domain.meeting.dto.MeetingResDto;
import server.meeting.domain.meeting.model.Meeting;
import server.meeting.domain.meeting.model.QMeeting;
import server.meeting.domain.meeting.repository.CustomMeetingRepository;
import server.meeting.domain.meeting.repository.MeetingRepository;

import java.util.List;

import static server.meeting.domain.team.model.QTeam.team;

@Repository
public class MeetingRepositoryImpl implements CustomMeetingRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MeetingRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Meeting> selectMeetingList() {
        QMeeting meeting = QMeeting.meeting;

        List<Meeting> meetings = jpaQueryFactory
                .selectFrom(meeting)
                .where(meeting.team.eq(team))
                .join(meeting.team, team)
                .orderBy(meeting.date.desc())
                .fetch();

        return meetings;
    }

}