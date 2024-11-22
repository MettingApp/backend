package server.meeting.domain.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.meeting.domain.record.model.Recorder;

public interface RecorderRepository extends JpaRepository<Recorder, Long> {
}
