package com.fastcampus.hellospringbatch.job;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fastcampus.hellospringbatch.BatchConfig;
import com.fastcampus.hellospringbatch.core.repository.PlainTextRepository;
import com.fastcampus.hellospringbatch.core.repository.ResultTextRepository;

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {PlainTextJobConfig.class, BatchConfig.class})   // 여기에 HelloJobConfig만 등록했기때문에 JobLauncherTestUtils.setjob에서 에러가 나지 않음!
class PlainJobConfigTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private PlainTextRepository plainTextRepository;

	@Autowired
	private ResultTextRepository resultTextRepository;

	@AfterEach
	public void tearDown() {
		plainTextRepository.deleteAll();
		resultTextRepository.deleteAll();
	}

	@DisplayName("주어진 값이 없을 경우의 test")
	@Test
	public void success_givenNoPlainText() throws Exception {
		//given
		//no plainText

		//when
		JobExecution execution = jobLauncherTestUtils.launchJob();

		//then
		Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
		Assertions.assertEquals(resultTextRepository.count(), 0); // 주어진 값이 없으므로 결과 테이블도 0이어야함

	}
}
