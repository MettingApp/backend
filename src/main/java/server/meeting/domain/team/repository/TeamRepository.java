package server.meeting.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.meeting.domain.team.model.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findById(String name);
}
