package server.meeting.domain.member.service;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.meeting.domain.member.dto.MemberSignInRequestDto;
import server.meeting.domain.member.dto.MemberSignUpRequestDto;
import server.meeting.domain.member.dto.MemberSignUpResponseDto;
import server.meeting.domain.member.model.Member;
import server.meeting.domain.member.model.Role;
import server.meeting.domain.member.repository.MemberRepository;
import server.meeting.global.common.TokenDto;
import server.meeting.global.exception.ApiException;
import server.meeting.global.util.JwtProvider;

import static server.meeting.global.error.ErrorType._BAD_REQUEST_PASSWORD;
import static server.meeting.global.error.ErrorType._CONFLICT_USERNAME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImp implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Transactional
    @Override
    public MemberSignUpResponseDto signUp(MemberSignUpRequestDto request) {
        validateDuplicateUsername(request.getUsername());
        validateDuplicateName(request.getName());
        validatePasswordMatch(request.getPassword(), request.getCheckedPassword());
        String encodingPassword = passwordEncoder.encode(request.getPassword());

        Member member = Member.of(Role.USER, request.getUsername(), encodingPassword, request.getNickname(),request.getName());
        memberRepository.save(member);

        return MemberSignUpResponseDto.builder()
                .description("회원가입에 성공했습니다.")
                .build();
    }

    private void validateDuplicateName(String name) {
        if (memberRepository.existsMemberByName(name)) {
            throw new ApiException(_CONFLICT_USERNAME);
        }
    }

    private void validateDuplicateUsername(String username) {
        if (memberRepository.existsMemberByUsername(username)) {
            throw new ApiException(_CONFLICT_USERNAME);
        }
    }

    private void validatePasswordMatch(String password, String checkedPassword) {
        if (password == null || !password.equals(checkedPassword)) {
            throw new ApiException(_BAD_REQUEST_PASSWORD);
        }
    }

    @Transactional
    @Override
    public TokenDto signIn(MemberSignInRequestDto request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }
}
