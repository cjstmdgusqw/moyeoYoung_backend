package com.kosta.moyoung.openroom.controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.member.dto.MemberResponseDto;
import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.member.service.MemberService;
import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.service.EnteranceService;
import com.kosta.moyoung.openroom.service.OpenRoomService;
import com.kosta.moyoung.security.jwt.JwtUtil;
import com.kosta.moyoung.util.FileService;
import com.kosta.moyoung.util.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
@RestController  
@RequestMapping("room") 
public class OpenRoomController {
	@Autowired
	private OpenRoomService orService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FileService fileService;
	@Autowired
	private EnteranceService enteranceService;

	@PostMapping("/makeRoom") 
	public ResponseEntity<Long> makeRoom(@ModelAttribute RoomDTO roomDto, 
			@RequestParam(value = "file", required=false) MultipartFile file){ 
		try {
			Member mem = memberService.findMember(JwtUtil.getCurrentMemberId());  
			Long roomId = orService.makeRoom(roomDto,file,mem); 
			enteranceService.JoinRoom(roomId, mem,true);
			return new ResponseEntity<Long>(roomId ,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/settingRoom") 
	public ResponseEntity<Long> settingRoom(@ModelAttribute RoomDTO roomDto, 
			@RequestParam(value = "file", required=false) MultipartFile file){ 
		try {  
			Long roomId = orService.modifyRoom(roomDto,file);  
			return new ResponseEntity<Long>(roomId ,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@GetMapping("/getroomMain/{roomId}")
	public ResponseEntity<RoomDTO> selectRoomById(@PathVariable Long roomId){
		try {
			log.info(roomId+"");
			RoomDTO room = orService.selectById(roomId);
			return new ResponseEntity<RoomDTO>(room, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<RoomDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/roomList/{page}")
	public ResponseEntity<Map<String, Object>> roomList(@PathVariable Integer page, @RequestParam(value = "cnt", required=false) Integer cnt) {
		Map<String,Object> res = new HashMap<>();  
		try {
			PageInfo pageInfo = new PageInfo();
			List<RoomDTO> list = orService.findRoomList(page, pageInfo, cnt);
			res.put("pageInfo", pageInfo);
			res.put("list", list); 
			if(JwtUtil.getCurrentMemberId()==null) {
				System.out.println("아이디업슴");
			}else {
				List<Long> isBookmarks = orService.isBookmarks(JwtUtil.getCurrentMemberId()); 
				if(!isBookmarks.isEmpty()) {
					res.put("isBookmarks", isBookmarks); 			 
				}
				
			}

			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST); 
		}
	} 
  
	@GetMapping("/roomListByCate/{page}")
	public ResponseEntity<Map<String, Object>> roomListByCate(@RequestParam("cateName") String cateName,
			@PathVariable Integer page) {
		try {
			PageInfo pageInfo = new PageInfo();
			Map<String, Object> res = new HashMap<>();
			List<RoomDTO> list = orService.fineRoomByCategory(cateName, page, pageInfo);
			res.put("list", list);
			res.put("pageInfo", pageInfo);
			
			MemberResponseDto mem =  memberService.findMemberInfoById(JwtUtil.getCurrentMemberId());
			List<Long> isBookmarks = orService.isBookmarks(mem.getMemberId()); 
			if(!isBookmarks.isEmpty()) {
				res.put("isBookmarks", isBookmarks); 				
			}
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/roomListByWord/{page}")
	public ResponseEntity<Map<String, Object>> roomListByWord(@RequestParam("word") String word,
			@PathVariable Integer page) {
		try {
			PageInfo pageInfo = new PageInfo();
			Map<String, Object> res = new HashMap<>();
			List<RoomDTO> list = orService.fineRoomByWord(word, page, pageInfo);
			res.put("list", list);
			res.put("pageInfo", pageInfo);
			
			MemberResponseDto mem =  memberService.findMemberInfoById(JwtUtil.getCurrentMemberId());
			List<Long> isBookmarks = orService.isBookmarks(mem.getMemberId()); 
			if(!isBookmarks.isEmpty()) {
				res.put("isBookmarks", isBookmarks); 				
			}
			return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/view/{imgName}")
	public void image(@PathVariable("imgName") String imgName, HttpServletResponse response) {
		try { 
			fileService.fileView(imgName, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/bookmark/{roomId}")
	public ResponseEntity<Map<String,Object>> bookmark(@PathVariable("roomId") Long roomId) {
		try { 
			Boolean isBookmark = orService.bookMark(roomId,JwtUtil.getCurrentMemberId()); 
			Map<String,Object> map = new HashMap<>();
			map.put("isBookmark", isBookmark);
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK); 
      
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/deleteRoom")
	public ResponseEntity<String> deleteRoom(@RequestParam("roomId") Long roomId) {
		try { 
			System.out.println(roomId);
			orService.removeRoom(roomId);
			return new ResponseEntity<String>("방이 성공적으로 삭제되었습니다!",HttpStatus.OK); 
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
 
}
