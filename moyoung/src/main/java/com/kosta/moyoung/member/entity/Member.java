package com.kosta.moyoung.member.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

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
	
	private String imageUrl;
	  
	@Column
	private LocalDate regdate;
	
	@PrePersist
    protected void onCreate() {
        regdate = LocalDate.now();
    }
	
	@Enumerated(EnumType.STRING)
	private Authority authority;
	
	 @Builder
	 public Member(String email, String password, String nickname,  Provider provider, String profileContent, String imageUrl, Authority authority) {
	        this.email = email;
	        this.password = password;
	        this.nickname=nickname;
	        this.provider=provider;
	        this.profileContent = profileContent;
	        this.imageUrl=imageUrl;
	        this.authority = authority;
	    }
	 
	 @Builder
	 public Member(String nickname, String email, String imageUrl, Authority authority ) {
	        this.nickname=nickname;
	        this.email = email;
	        this.imageUrl=imageUrl;
	        this.authority = authority;
	 }
	 
	 public void updateNickname(String nickname){
	        this.nickname = nickname;
	    }
	 
	 public void updateImageUrl(String imageUrl){
	        this.imageUrl = imageUrl;
	    }
	
}