package server.meeting.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.meeting.domain.member.dto.MemberSignInRequestDto;
import server.meeting.domain.member.dto.MemberSignUpRequestDto;
import server.meeting.domain.member.dto.MemberSignUpResponseDto;
import server.meeting.domain.member.service.MemberService;
import server.meeting.global.api.Api;
import server.meeting.global.common.TokenDto;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/sign-up")
    public Api<MemberSignUpResponseDto> signUp(@Valid @RequestBody MemberSignUpRequestDto requestBody) {
        return Api.success(memberService.signUp(requestBody));
    }

    @PostMapping("/sign-in")
    public Api<TokenDto> signIn(@Valid @RequestBody MemberSignInRequestDto requestBody) {
        return Api.success(memberService.signIn(requestBody));
    }
}
