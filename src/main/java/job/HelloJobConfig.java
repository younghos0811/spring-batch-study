package job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.config.Task;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class HelloJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean("helloJob")
	public Job helloJob(Step helloStep) {
		return jobBuilderFactory.get("helloJob")
			.incrementer(new RunIdIncrementer()) //job 실행시 횟수를 일정하게 증가시켜주는 설정
			.start(helloStep)
			.build();
	}

	@JobScope // Job이 실행되는 동안만 lifecycle을 가져감
	@Bean("helloStep")
	public Step helloStep(Tasklet tasklet) {
		return stepBuilderFactory.get("helloStep")
			.tasklet(tasklet)
			.build();
	}

	@StepScope // Step 실행되는 동안만 lifecycle을 가져감
	@Bean("tasklet")
	public Tasklet tasklet() {
		return(contribution, chunkContext) -> {
			//한 transaction안의 작업
			System.out.println("Hello Spring Batch");
			return RepeatStatus.FINISHED;
		};
	}

}
