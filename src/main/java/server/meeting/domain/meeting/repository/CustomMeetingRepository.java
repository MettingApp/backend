package server.meeting.domain.meeting.repository;

import server.meeting.domain.meeting.dto.MeetingResDto;
import server.meeting.domain.meeting.model.Meeting;

import java.util.List;

public interface CustomMeetingRepository {

    List<Meeting> selectMeetingList();
}
