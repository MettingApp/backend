package server.meeting.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class MemberSignUpResponseDto {
    private String description;

    @Builder
    public MemberSignUpResponseDto(String description) {
        this.description = description;
    }
}
