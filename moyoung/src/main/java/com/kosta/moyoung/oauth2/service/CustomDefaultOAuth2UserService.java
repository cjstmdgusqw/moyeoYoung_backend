//package com.kosta.moyoung.oauth2.service;
//
//import java.util.Optional;
//
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import com.kosta.moyoung.advice.DefaultAssert;
//import com.kosta.moyoung.member.entity.Authority;
//import com.kosta.moyoung.member.entity.Member;
//import com.kosta.moyoung.member.entity.Provider;
//import com.kosta.moyoung.member.repository.MemberRepository;
//import com.kosta.moyoung.member.util.UserPrincipal;
//import com.kosta.moyoung.oauth2.userInfo.OAuth2UserInfo;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Service
//public class CustomDefaultOAuth2UserService extends DefaultOAuth2UserService {
//		
//	private final MemberRepository memberRepository;
//	
//	@Override
//	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
//	
//		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
//		try {
//			return processOAuth2User(oAuth2UserRequest, oAuth2User);
//		} catch (Exception e) {
//			DefaultAssert.isAuthentication(e.getMessage());
//		}
//		return null;
//	}
//
//	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
//		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
//				oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
//		DefaultAssert.isAuthentication(!oAuth2UserInfo.getEmail().isEmpty());
//
//		Optional<Member> memberOptional = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
//		Member member;
//		if (memberOptional.isPresent()) {
//			member = memberOptional.get();
//			DefaultAssert.isAuthentication(member.getProvider()
//					.equals(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId())));
//			member = updateExistingMember(member, oAuth2UserInfo);
//		} else {
//			member = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
//		}
//
//		return UserPrincipal.create(member, oAuth2User.getAttributes());
//	}
//
//	private Member registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
//		Member member = Member.builder()
//				.provider(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
//				.nickname(oAuth2UserInfo.getName()).email(oAuth2UserInfo.getEmail())
//				.imageUrl(oAuth2UserInfo.getFileName()).authority(Authority.ROLE_USER).build();
//
//		return memberRepository.save(member);
//	}
//
//	private Member updateExistingMember(Member member, OAuth2UserInfo oAuth2UserInfo) {
//
//		member.updateNickname(oAuth2UserInfo.getName());
//		member.updateImageUrl(oAuth2UserInfo.getFileName());
//
//		return memberRepository.save(member);
//	}
//}
