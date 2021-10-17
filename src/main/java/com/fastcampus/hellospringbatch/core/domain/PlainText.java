package com.fastcampus.hellospringbatch.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "plain_text")
@DynamicUpdate // 실제 값이 변경된 컬럼으로만 update 쿼리를 만드는 기능

//write 시에 위 2개 필요 (정확히는 객체 만들시)
@AllArgsConstructor
@NoArgsConstructor //entity여서 필요
public class PlainText {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String text;

}
