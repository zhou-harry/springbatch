package com.example.demo.jdbc.config;

import com.example.demo.jdbc.entity.CustomerCredit;
import com.example.demo.jdbc.mapper.CustomerCreditRowMapper;
import com.example.demo.jdbc.repository.CreditRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.math.BigDecimal;

/**
 * @author zhouhong
 * @version 1.0
 * @title: JdbcBatchConfig
 * @description: TODO
 * @date 2019/6/11 15:08
 */
@Configuration
public class JdbcBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CreditRepository creditRepository;

    @Bean
    public Job jdbcCreditJob() {
        return this.jobBuilderFactory.get("jdbcCreditJob")
                .start(jdbcCreditStep())
                .build();
    }

    @Bean
    public Step jdbcCreditStep() {
        ItemProcessor<CustomerCredit, CustomerCredit> processor = (item) -> {
            item.setId(0L);
            item.setCredit(new BigDecimal(100).add(item.getCredit()));
            System.out.println("jdbc processor：" + item.toString());
            return item;
        };
        return this.stepBuilderFactory.get("jdbc_credit_step")
                .<CustomerCredit, CustomerCredit>chunk(1)
                .reader(creditReader())
                .processor(processor)
                .writer(itemWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<CustomerCredit> creditReader() {
        return new JdbcCursorItemReaderBuilder<CustomerCredit>()
                .dataSource(this.dataSource)
                .name("creditReader")
                .sql("select ID, NAME, CREDIT from CUSTOMER")
                .rowMapper(new CustomerCreditRowMapper())
                .build();
    }

    @Bean
    public JsonFileItemWriter<CustomerCredit> creditWriter() {
        return new JsonFileItemWriterBuilder<CustomerCredit>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource("target/test-outputs/outputCredit.json"))
                .name("creditJdbcFileItemWriter")
                .build();
    }

    //重用现有数据库操作服务==============================================================
    @Bean
    public ItemReaderAdapter itemReader() {
        ItemReaderAdapter reader = new ItemReaderAdapter();

        reader.setTargetObject(creditRepository);
        reader.setTargetMethod("findAll");

        return reader;
    }

    @Bean
    public ItemWriterAdapter itemWriter() {
        ItemWriterAdapter writer = new ItemWriterAdapter();

        writer.setTargetObject(creditRepository);
        writer.setTargetMethod("save");

        return writer;
    }


}
