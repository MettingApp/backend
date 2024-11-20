package server.meeting.domain.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.meeting.domain.meeting.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
