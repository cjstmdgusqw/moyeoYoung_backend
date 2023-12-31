package com.kosta.moyoung.member.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kosta.moyoung.member.entity.Authority;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.entity.Provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
	
    private String email;
    private String password;
    private String nickname;
	private String profileContent;
	private Provider provider;
	private String fileName;	
	
	
    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
				.profileContent(profileContent)
                .authority(Authority.ROLE_USER)
                .provider(provider)
                .fileName(fileName)
                .build();
    }
    
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}