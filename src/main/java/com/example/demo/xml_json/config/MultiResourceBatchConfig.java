package com.example.demo.xml_json.config;

import com.example.demo.xml_json.entity.Trade;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;

/**
 * @author zhouhong
 * @version 1.0
 * @title: MultiResourceBatchConfig
 * @description: TODO
 * @date 2019/6/13 17:30
 */
@Configuration
public class MultiResourceBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Value("classpath:/xml/trades.xml")
    Resource inputResource;

    @Bean
    public Job multiTradeJob(Step multiTradeStep) {
        return this.jobBuilderFactory.get("multiTradeJob")
                .start(multiTradeStep)
                .build();
    }

    @Bean
    public Step multiTradeStep(StaxEventItemReader itemReader,ClassifierCompositeItemWriter<Trade> classifierCompositeItemWriter) {
        ItemProcessor<Trade, Trade> processor = (item) -> {
            System.out.println("multi processor：" + item.toString());
            return item;
        };
        ItemWriter<Trade> writer = (items) -> {
            items.forEach(item -> {
                System.out.println("写入器-打印数据：" + item);
            });
        };
        return this.stepBuilderFactory.get("multi_trade_step")
                .<Trade, Trade>chunk(1)
                .reader(itemReader)
                .processor(processor)
                .writer(classifierCompositeItemWriter)
                .build();
    }

    @Bean
    public StaxEventItemReader itemReader2(XStreamMarshaller tradeMarshaller) {
        return new StaxEventItemReaderBuilder<Trade>()
                .name("itemReader2")
                .resource(inputResource)
                .addFragmentRootElements("trade")
                .unmarshaller(tradeMarshaller)
                .build();

    }

    @Bean
    public StaxEventItemWriter xmlitemWriter(XStreamMarshaller tradeMarshaller) {
        return new StaxEventItemWriterBuilder<Trade>()
                .name("tradesWriter")
                .marshaller(tradeMarshaller)
                .resource(new FileSystemResource("target/test-outputs/outputTrades.xml"))
                .rootTagName("trade")
                .overwriteOutput(true)
                .build();

    }

    @Bean
    public ClassifierCompositeItemWriter<Trade> classifierCompositeItemWriter(
            JsonFileItemWriter<Trade> jsonFileItemWriter,
            StaxEventItemWriter xmlitemWriter) {
        ClassifierCompositeItemWriter<Trade> classifierCompositeItemWriter = new ClassifierCompositeItemWriter<>();

        ItemWriter<Trade> writer = (items) -> {
            items.forEach(item -> {
                System.out.println("写入器-打印数据 1：" + item);
            });
        };

        ItemWriter<Trade> writer1 = (items) -> {
            items.forEach(item -> {
                System.out.println("写入器-打印数据 2：" + item);
            });
        };

        classifierCompositeItemWriter.setClassifier(new Classifier<Trade, ItemWriter<? super Trade>>() {
            @Override
            public ItemWriter<? super Trade> classify(Trade trade) {
                if (trade.getQuantity() > 5l) {
                    return writer1;
                }
                return writer;
            }
        });

        return classifierCompositeItemWriter;
    }

}
