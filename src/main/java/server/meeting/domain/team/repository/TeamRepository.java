package server.meeting.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.meeting.domain.team.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
