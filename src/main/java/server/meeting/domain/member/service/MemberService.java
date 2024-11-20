package server.meeting.domain.member.service;

import server.meeting.domain.member.dto.MemberSignUpRequestDto;
import server.meeting.domain.member.dto.MemberResponseDto;
import server.meeting.global.common.TokenDto;

public interface MemberService {
    MemberResponseDto signUp(MemberSignUpRequestDto request);
    TokenDto signIn(MemberSignUpRequestDto request);
}
