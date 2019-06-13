package com.example.demo.xml_json.config;

import com.example.demo.xml_json.entity.Trade;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhong
 * @version 1.0
 * @title: XMLBatchConfig
 * @description: TODO
 * @date 2019/6/11 10:36
 */
@Configuration
public class XMLBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job xmlTradeJob() {
        return this.jobBuilderFactory.get("xmlTradeJob")
                .start(tradeStep())
                .build();
    }

    @Bean
    public Step tradeStep() {
        ItemProcessor<Trade, Trade> processor = (item) -> {
            System.out.println("XML processor："+item.toString());
            return item;
        };
        return this.stepBuilderFactory.get("trade_step")
                .<Trade, Trade>chunk(1)
                .reader(itemReader(new FileSystemResource("src/main/resources/xml/trades.xml")))
                .processor(processor)
                .writer(itemWriter(new FileSystemResource("target/test-outputs/outputTrades.xml")))
                .build();
    }

    public StaxEventItemReader itemReader(Resource inputResource) {
        return new StaxEventItemReaderBuilder<Trade>()
                .name("itemReader")
                .resource(inputResource)
                .addFragmentRootElements("trade")
                .unmarshaller(tradeMarshaller())
                .build();

    }

    public StaxEventItemWriter itemWriter(Resource outputResource) {
        return new StaxEventItemWriterBuilder<Trade>()
                .name("tradesWriter")
                .marshaller(tradeMarshaller())
                .resource(outputResource)
                .rootTagName("trade")
                .overwriteOutput(true)
                .build();

    }
    @Bean
    public XStreamMarshaller tradeMarshaller() {
        Map<String, Class> aliases = new HashMap<>();
        aliases.put("trade", Trade.class);
        aliases.put("price", BigDecimal.class);
        aliases.put("isin", String.class);
        aliases.put("customer", String.class);
        aliases.put("quantity", Long.class);

        XStreamMarshaller marshaller = new XStreamMarshaller();

        marshaller.setAliases(aliases);

        return marshaller;
    }


}
