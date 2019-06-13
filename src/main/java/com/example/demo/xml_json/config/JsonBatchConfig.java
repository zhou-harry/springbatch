package com.example.demo.xml_json.config;

import com.example.demo.xml_json.entity.Trade;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 * @author zhouhong
 * @version 1.0
 * @title: JsonBatchConfig
 * @description: TODO
 * @date 2019/6/11 10:36
 */
@Configuration
public class JsonBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jsonTradeJob() {
        return this.jobBuilderFactory.get("jsonTradeJob")
                .start(jsonTradeStep())
                .build();
    }

    @Bean
    public Step jsonTradeStep() {
        ItemProcessor<Trade, Trade> processor = (item) -> {
            System.out.println("Json processor："+item.toString());
            return item;
        };
        return this.stepBuilderFactory.get("json_trade_step")
                .<Trade, Trade>chunk(1)
                .reader(jsonItemReader())
                .processor(processor)
                .writer(jsonFileItemWriter())
                .build();
    }

    @Bean
    public JsonItemReader<Trade> jsonItemReader() {
        return new JsonItemReaderBuilder<Trade>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Trade.class))
                .resource(new ClassPathResource("json/trades.json"))
                .name("tradeJsonItemReader")
                .build();
    }

    @Bean
    public JsonFileItemWriter<Trade> jsonFileItemWriter() {
        return new JsonFileItemWriterBuilder<Trade>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource("target/test-outputs/outputTrades.json"))
                .name("tradeJsonFileItemWriter")
                .build();
    }


}
