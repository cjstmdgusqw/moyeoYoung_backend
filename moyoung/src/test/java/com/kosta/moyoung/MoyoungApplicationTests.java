package com.kosta.moyoung;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.repository.MemberRepository;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.openroom.entity.Enterance;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.BookmarkRepository;
import com.kosta.moyoung.openroom.repository.EnteranceRepository;
import com.kosta.moyoung.openroom.repository.OpenRoomRepository;
import com.kosta.moyoung.openroom.service.OpenRoomService;
import com.kosta.moyoung.security.jwt.JwtUtil;

@SpringBootTest
class MoyoungApplicationTests {
	
	
	@Autowired
	private MemberRepository memberRepository;
	
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private OpenRoomRepository orRepository;
	@Autowired
	private OpenRoomService orService;
	@Autowired
	private MemberRepository memRepository; 
	@Autowired
	private EnteranceRepository entRepository;
	@Autowired
	private BookmarkRepository bookmarkRepository;
	
	
	@Test
	public void test() {
		Long memberId = JwtUtil.getCurrentMemberId();
		Optional<Member> member = memberRepository.findById(memberId);
		System.out.println(member);
	}
	
	// 방개설 save 테스트
//	@Test 
//	void makeRoom() {
//		try {
//			Room room =  Room.builder().roomId((long)1001).roomTitle("룰루랄라c방").roomContent("내가 왕이다!")
//					.roomCategory("프로젝트").roomType("open").userId((long)1).build();
//			orRepository.save(room);
//			System.out.println(room);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Test
//	void bookmarkTest() {
//		try {
//			System.out.println(orService.isBookmarks((long)101));
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	 
//	@Test
//	void memberList() {
//		try {
//			List<Enterance> list = entRepository.findByRoomRoomId(44L);
//			System.out.println(list);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	} 
}
