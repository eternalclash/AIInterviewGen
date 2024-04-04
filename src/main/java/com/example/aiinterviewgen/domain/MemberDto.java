package com.example.aiinterviewgen.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$", message = "이름은 8글자~16글자의 영문자 + 숫자 조합이어야 합니다.")
    private String name;
    @NotBlank
    @Pattern(regexp = "^[0-9]{6}$", message = "비밀번호는 숫자 6자리입니다.")
    private String password;
}
