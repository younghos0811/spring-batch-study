package com.fastcampus.hellospringbatch.job;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
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

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HelloJobConfig.class, BatchConfig.class})   // 여기에 HelloJobConfig만 등록했기때문에 JobLauncherTestUtils.setjob에서 에러가 나지 않음!
class HelloJobConfigTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void success() throws Exception {
		//when
		JobExecution execution = jobLauncherTestUtils.launchJob();

		//then
		Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED); // 정상적으로 확인 됬는지 확인
	}
}
