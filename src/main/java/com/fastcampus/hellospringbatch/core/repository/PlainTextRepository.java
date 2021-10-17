package com.fastcampus.hellospringbatch.core.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.hellospringbatch.core.domain.PlainText;

public interface PlainTextRepository extends JpaRepository<PlainText, Integer> {
	Page<PlainText> findBy(Pageable pageable); // commit size만큼 가져올 수있게하려고
}
