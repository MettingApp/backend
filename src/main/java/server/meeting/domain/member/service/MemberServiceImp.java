package server.meeting.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.meeting.domain.member.dto.MemberSignUpRequestDto;
import server.meeting.domain.member.dto.MemberResponseDto;
import server.meeting.domain.member.repository.MemberRepository;
import server.meeting.global.common.TokenDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImp implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public MemberResponseDto signUp(MemberSignUpRequestDto request) {
        return null;
    }

    @Transactional
    @Override
    public TokenDto signIn(MemberSignUpRequestDto request) {
        return null;
    }
}
