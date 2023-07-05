package com.kosta.moyoung.openroom.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter 
@NoArgsConstructor 
@ToString
public class Room {
	@Id  @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roomId; //방 아이디
	@Column(nullable = false, length = 17)
	private String roomTitle; //제목
	@Column(nullable = false, length = 300)
	private String roomContent; //소개글
	@Column(nullable = false)
	private String roomImage; //이미지제목
	@Column(nullable = false)
	private String roomCategory; //카테고리
	@Column(nullable = false)
	private Date roomCreateDate; //생성일-수정일
	@Column(nullable = false)
	private String roomType; //모임유형 : open/close비공개 
	@Column(nullable = false) 
	private Long userId; //방장아이디
	@Column(nullable = false)
	private Long roomUserCnt;  //멤버수
	
	@OneToMany(mappedBy="roomBookmark")
	private List<Bookmark> bookmarks = new ArrayList<>();
	
	@Builder 
	public Room(Long roomId, String roomTitle, String roomContent, String roomImage, String roomCategory,
			Date roomCreateDate, String roomType, Long userId, Long roomUserCnt) {
		super();
		this.roomId = roomId;
		this.roomTitle = roomTitle;
		this.roomContent = roomContent;
		this.roomImage = roomImage;
		this.roomCategory = roomCategory;
		this.roomCreateDate = roomCreateDate;
		this.roomType = roomType;
		this.userId = userId;
		this.roomUserCnt = roomUserCnt;
	}
	

	
	
}

