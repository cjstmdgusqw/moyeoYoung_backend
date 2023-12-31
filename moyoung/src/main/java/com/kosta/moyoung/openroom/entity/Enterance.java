package com.kosta.moyoung.openroom.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.kosta.moyoung.member.entity.Member;
import com.kosta.moyoung.openroom.dto.EnteranceDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity  
@Setter
@NoArgsConstructor
@Getter
public class Enterance {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long enteranceId;
	
	@Column
	private Date entRegDate;
	
	
	@Column
	private boolean status;
	 
	 
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "roomId", referencedColumnName = "roomId")  
	private Room room;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="memberId") 
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Member member;

		 
	@Builder
	public Enterance(Date entRegDate, Room room, Member member, boolean status) { 
		this.entRegDate = entRegDate;
		this.member = member;
		this.room = room; 
		this.status = status;
	}
		
		

	
	
	
}
