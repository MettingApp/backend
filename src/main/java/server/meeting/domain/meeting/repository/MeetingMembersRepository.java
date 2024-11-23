package server.meeting.domain.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.meeting.domain.meeting.model.MeetingMembers;

public interface MeetingMembersRepository extends JpaRepository<MeetingMembers, Long> {
}
