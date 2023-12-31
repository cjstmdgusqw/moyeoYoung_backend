package com.kosta.moyoung.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.entity.Provider;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	//Email 을 Login ID 로 갖고 있기 때문에 findByEmail 와 
	//중복 가입 방지를 위한 existsByEmail 만 추가
	Optional<Member> findByEmail(String email);
	Optional<Member> findById(long memberId); 
	//닉네임으로 프로필 조회
	Optional<Member> findByNickname(String nickname);
	
	
	boolean existsByEmail(String email);
	boolean existsByNickname(String nickname);
	
	//소셜타입과 소셜의 식별값으로 회원찾는 메소드
	Optional<Member> findByProviderAndSocialId(Provider provider, String socialId);
}
