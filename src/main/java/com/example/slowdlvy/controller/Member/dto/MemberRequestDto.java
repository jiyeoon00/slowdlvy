package com.example.slowdlvy.controller.Member.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

public class MemberRequestDto {

    @Getter
    @Setter
    public static class Join{

        @NotBlank(message = "아이디는 필수 입력값입니다.")
        private String username;
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;
    }

    @Getter
    @Setter
    public static class Login{
        @NotBlank(message = "아이디는 필수 입력값입니다.")
        private String username;
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication(){
            return new UsernamePasswordAuthenticationToken(username, password);
        }
    }
}
