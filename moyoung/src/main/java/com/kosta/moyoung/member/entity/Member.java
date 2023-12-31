package com.kosta.moyoung.member.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.kosta.moyoung.note.entity.Note;
import com.kosta.moyoung.feedroom.entity.LikeEntity;
import com.kosta.moyoung.feedroom.entity.RoomfeedEntity;
import com.kosta.moyoung.openroom.entity.Bookmark;
import com.kosta.moyoung.openroom.entity.Enterance;
import com.kosta.moyoung.openroom.entity.Room;

//import com.kosta.moyoung.openroom.entity.Bookmark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long memberId; 

	@Column(nullable = false, length = 30, unique = true)
	private String email;
	
	@Setter
	@Column(nullable = false, length = 100)
	private String password;
	
	@Setter
	@Column(nullable = false, length = 20)
	private String nickname;
	
	@Setter
	@Column(length = 3000)
	private String profileContent;
	
	@Enumerated(EnumType.STRING)
    private Provider provider;
	
	@Setter
	@Column(nullable= true)
	private String fileName;
	@Column
	private String socialId;
	
	@Column
	private LocalDate regdate;
	
	@PrePersist
    protected void onCreate() {
        regdate = LocalDate.now();
    }
	
	@Enumerated(EnumType.STRING)
	private Authority authority;
	 
	@OneToMany(mappedBy="host", fetch=FetchType.EAGER)
	private List<Room> madeRooms = new ArrayList<>(); 
//	@OneToMany(mappedBy="sender", fetch = FetchType.LAZY)
//	private List<Note> sendNotes = new ArrayList<>();
//	
//	@OneToMany(mappedBy="receiver", fetch = FetchType.LAZY)
//	private List<Note> recevedNotes = new ArrayList<>();  
	@OneToMany(mappedBy="memberBookmark", fetch=FetchType.LAZY)
	private List<Bookmark> bookmarks = new ArrayList<>();
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY)
	private List<RoomfeedEntity> feeds = new ArrayList<RoomfeedEntity>();

	@OneToMany(mappedBy="member", fetch=FetchType.LAZY)
	private List<Enterance> joindRooms = new ArrayList<>();
		
	
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY)
	private List<LikeEntity> Like = new ArrayList<>();
	
	 @Builder
	 public Member(String email, String password, String nickname,  Provider provider, String profileContent, String fileName, Authority authority) {
	        this.email = email;
	        this.password = password;
	        this.nickname=nickname;
	        this.provider=provider;
	        this.profileContent = profileContent;
	        this.fileName=fileName;
	        this.authority = authority;
	    }
	 
//	 @Builder
//	 public Member(String nickname, String email, String fileName, Authority authority ) {
//	        this.nickname=nickname;
//	        this.email = email;
//	        this.fileName=fileName;
//	        this.authority = authority;
//	 }
	 
	 public void updateNickname(String nickname){
	        this.nickname = nickname;
	    }
	 
	 public void updateImageUrl(String fileName){
	        this.fileName = fileName;
	    }
	 
	 public void updateProfileContent(String profileContent){
	        this.profileContent = profileContent;
	    }

	 
	 
	
}
