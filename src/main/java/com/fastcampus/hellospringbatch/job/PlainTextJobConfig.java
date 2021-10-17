package com.fastcampus.hellospringbatch.job;

import java.util.Collections;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import com.fastcampus.hellospringbatch.core.domain.PlainText;
import com.fastcampus.hellospringbatch.core.domain.ResultText;
import com.fastcampus.hellospringbatch.core.repository.PlainTextRepository;
import com.fastcampus.hellospringbatch.core.repository.ResultTextRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PlainTextJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PlainTextRepository plainTextRepository;
    private final ResultTextRepository resultTextRepository;

    @Bean("plainTextJob")
    public Job plainTextJob(Step plainTextStep) {
        return jobBuilderFactory.get("plainTextJob")
                .incrementer(new RunIdIncrementer())
                .start(plainTextStep)
                .build();
    }

    @JobScope
    @Bean("plainTextStep")
    public Step plainTextStep(ItemReader plainTextReader,
        ItemProcessor plainTextProcessor,
        ItemWriter plainTextWriter) {
        return stepBuilderFactory.get("plainTextStep")
            .<PlainText, String>chunk(2) // read, processing타입 및 chunksize
            .reader(plainTextReader)
            .processor(plainTextProcessor)
            .writer(plainTextWriter)
            .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<PlainText> plainTextReader() {
        return (new RepositoryItemReaderBuilder<PlainText>()
            .name("plainTextReader")
            .repository(plainTextRepository)
            .methodName("findBy") //호출하려고 하는 repository의 method
            .pageSize(2)) //commit interval size
            .arguments(List.of()) //현재는 파라미터로 넣는게 없음으로 빈어있는 list 보냄
            .sorts(Collections.singletonMap("id", Sort.Direction.DESC)) //id 기반 역순으로
            .build();

    }


    @StepScope
    @Bean  //람다식으로 간단히 사용 (process메서드)
    public ItemProcessor<PlainText, String> plainTextProcessor() {
        return item -> "processed " + item.getText();
    }

    @StepScope
    @Bean
    public ItemWriter<String> plainTextWriter() {
        return items -> {
            items.forEach(item -> resultTextRepository.save(new ResultText(null, item ))); //id를 null로 넣으면 자동으로 채번이 들어감
            System.out.println("==== chunk is finished");
        };
    }
}
