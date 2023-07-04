package com.kosta.moyoung.openroom.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.moyoung.openroom.dto.RoomDTO;
import com.kosta.moyoung.openroom.entity.Room;
import com.kosta.moyoung.openroom.repository.OpenRoomRepository;
import com.kosta.moyoung.util.PageInfo;

@Service
public class OpenRoomServiceImpl implements OpenRoomService {
	
//	@Autowired
//	private ServletContext servletContext;
	@Autowired
	private ModelMapper modelMapper;  
	@Autowired
	private OpenRoomRepository orRepository;
	
	private String dir = "C:/resources/upload/";

	@Override
	public void fileView(String imgName,OutputStream out) throws Exception { 
		FileInputStream fis = new FileInputStream(dir + imgName);
		FileCopyUtils.copy(fis, out);
		out.flush();  
	}
	
	@Override
	public void makeRoom(RoomDTO roomDto, MultipartFile file) throws Exception {

		//1.개설일 설정
		Date today = new Date(System.currentTimeMillis()); 
		roomDto.setRoomCreateDate(today);
		//2. 유저id 설정
		roomDto.setUserId((long)101);
		//3. 멤버수 설정
		roomDto.setRoomUserCnt((long)1); 
		
		// 파일입력
		if (file != null && !file.isEmpty()) { 
			String fileName = file.getOriginalFilename();
			File dfile = new File(dir + fileName);
			file.transferTo(dfile);

		}  
		// save
		Room room = modelMapper.map(roomDto, Room.class); 
		orRepository.save(room);  

	}

	@Override
	public List<RoomDTO> findRoomList(Integer page, PageInfo pageInfo) throws Exception {
		PageRequest pageRequest = PageRequest.of(page-1,8,Sort.by(Sort.Direction.DESC, "roomCreateDate"));
		Page<Room> rooms = orRepository.findAll(pageRequest);
		
		pageInfo.setAllPage(rooms.getTotalPages());
		pageInfo.setCurPage(page);
		int startPage = (page-1)/8*8+1; 
		int endPage = startPage+8-1;
		if(endPage>pageInfo.getAllPage()) endPage=pageInfo.getAllPage();
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		List<RoomDTO> list = new ArrayList<>();
		
		for(Room r : rooms.getContent()) {
			list.add(modelMapper.map(r, RoomDTO.class));
		}
		return list; 
	}

	@Override
	public List<RoomDTO> fineRoomByCategory(String cateName,Integer page, PageInfo pageInfo) throws Exception {
		PageRequest pageRequest = PageRequest.of(page-1,8,Sort.by(Sort.Direction.DESC, "room_create_date"));
		Page<Room> rooms = orRepository.findAllByRoomCategory(cateName, pageRequest);
		pageInfo.setAllPage(rooms.getTotalPages());
		pageInfo.setCurPage(page);
		int startPage = (page-1)/8*8+1; 
		int endPage = startPage+8-1;
		if(endPage>pageInfo.getAllPage()) endPage=pageInfo.getAllPage();
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		List<RoomDTO> list = new ArrayList<>();
		
		for(Room r : rooms.getContent()) {
			list.add(modelMapper.map(r, RoomDTO.class));
		}
		return list; 
	}

	
	@Override
	public List<RoomDTO> fineRoomByWord(String word, Integer page, PageInfo pageInfo) throws Exception { 
//		return orRepository.findAllByRoomWord(word);
		PageRequest pageRequest = PageRequest.of(page-1,8,Sort.by(Sort.Direction.DESC, "room_create_date"));
		Page<Room> rooms = orRepository.findAllByRoomWord(word, pageRequest);
		pageInfo.setAllPage(rooms.getTotalPages());
		pageInfo.setCurPage(page);
		int startPage = (page-1)/8*8+1; 
		int endPage = startPage+8-1;
		if(endPage>pageInfo.getAllPage()) endPage=pageInfo.getAllPage();
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		List<RoomDTO> list = new ArrayList<>();
		
		for(Room r : rooms.getContent()) {
			list.add(modelMapper.map(r, RoomDTO.class));
		}
		return list; 
	}
	

}
