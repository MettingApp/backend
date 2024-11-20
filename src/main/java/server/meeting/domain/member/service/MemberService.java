package server.meeting.domain.member.service;

import server.meeting.domain.member.dto.MemberSignInRequestDto;
import server.meeting.domain.member.dto.MemberSignUpRequestDto;
import server.meeting.domain.member.dto.MemberSignUpResponseDto;
import server.meeting.global.common.TokenDto;

public interface MemberService {
    MemberSignUpResponseDto signUp(MemberSignUpRequestDto request);

    TokenDto signIn(MemberSignInRequestDto request);
}
