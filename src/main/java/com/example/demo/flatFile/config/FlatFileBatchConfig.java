package com.example.demo.flatFile.config;

import com.example.demo.flatFile.entity.Player;
import com.example.demo.flatFile.listener.FlatFileJobListener;
import com.example.demo.flatFile.mapper.PlayerFieldSetMapper;
import com.example.demo.flatFile.service.TaskService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouhong
 * @version 1.0
 * @title: BatchConfig
 * @description: TODO
 * @date 2019/6/6 10:27
 */
@Configuration
public class FlatFileBatchConfig extends DefaultBatchConfigurer {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    TaskService taskService;
    @Value("classpath:/flatFile/*.csv")
    private Resource[] resources;


    @Bean
    public SimpleJobOperator jobOperator(JobExplorer jobExplorer,
                                         JobRepository jobRepository,
                                         JobRegistry jobRegistry,
                                         JobLauncher jobLauncher) {

        SimpleJobOperator jobOperator = new SimpleJobOperator();
        jobOperator.setJobExplorer(jobExplorer);
        jobOperator.setJobRepository(jobRepository);
        jobOperator.setJobRegistry(jobRegistry);
        jobOperator.setJobLauncher(jobLauncher);
        return jobOperator;
    }

    /**
     * jobOperator需要此处的JobRegistryBeanPostProcessor
     *
     * @param jobRegistry
     * @return
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

    @Bean
    public Job playerJob(Step playerStep) {

        Flow flow1 = new FlowBuilder<SimpleFlow>("flow1")
                .start(nextStep1())
                .next(nextStep2())
                .build();

        return this.jobBuilderFactory.get("player_Job")
                .start(playerStep)
                .split(new SimpleAsyncTaskExecutor())
                .add(flow1)
                .next(endStep())
                .end()
                .listener(new FlatFileJobListener())//Job监听器
                .validator((params) -> {
                    System.out.println("验证器-打印参数：" + params.toString());
                })
                .incrementer((parameters) -> {
                    String path = "src/main/resources/flatFile/players_1.csv";
                    if (parameters == null || parameters.isEmpty()) {
                        return new JobParametersBuilder()
                                .addLong("run.id", 1L)
                                .addString("input_file_path", path)
                                .toJobParameters();
                    }
                    long id = parameters.getLong("run.id", 1L) + 1;
                    return new JobParametersBuilder()
                            .addLong("run.id", id)
                            .addString("input_file_path", path)
                            .toJobParameters();
                })
                .build();
    }

    @Bean
    public Step playerStep(FlatFileItemReader<Player> playerItemReader,MultiResourceItemReader<Player> multiItemReader) {
        //处理器
        ItemProcessor<Player, Player> processor = (item) -> {
            item.setSex("未知");
            System.out.println("处理读-打印数据：" + item.toString());
            if ("AdamBo00".equals(item.getID())) {
                return null;
            }
            return item;
        };
        //写入
        ItemWriter<Player> writer = (items) -> {
            items.forEach(item -> {
                System.out.println("写入器-打印数据：" + item);
            });
        };
        return this.stepBuilderFactory.get("player_step")
                .<Player, Player>chunk(2)
                .reader(multiItemReader)
                .processor(processor)
//                .writer(playerItemWriter())
                .writer(writer)
                .writer(compositeItemWriter())
                .faultTolerant().skipLimit(10).skip(FlatFileParseException.class)
//                .listener(new FlatFileChunkListener())
//                .listener(new FlatFileReadListener())
//                .listener(new FlatFileSkipListener())
                .build();
    }

    @Bean
    public Step nextStep1() {
        return this.stepBuilderFactory.get("nextStep1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("=============================next Step 1：" + Thread.currentThread().getId());
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step nextStep2() {
        return this.stepBuilderFactory.get("nextStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("=============================next Step 2：" + Thread.currentThread().getId());
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step endStep() {
        return this.stepBuilderFactory.get("endStep")
                .tasklet(endTasklet(taskService))
                .build();
    }

    private MethodInvokingTaskletAdapter endTasklet(TaskService taskService) {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(taskService);
        adapter.setTargetMethod("updateTask");

        return adapter;
    }

    @Bean
    public CompositeItemWriter compositeItemWriter() {
        List<ItemWriter> writers = new ArrayList<>();
        writers.add(jsonItemWriter());
        writers.add(xmlItemWriter());

        CompositeItemWriter itemWriter = new CompositeItemWriter();

        itemWriter.setDelegates(writers);

        return itemWriter;
    }

    @Bean
    public JsonFileItemWriter<Player> jsonItemWriter() {
        return new JsonFileItemWriterBuilder<Player>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource("target/test-outputs/player.json"))
                .name("playerJsonFileItemWriter")
                .build();
    }

    @Bean
    public StaxEventItemWriter xmlItemWriter() {

        Map<String, Class> aliases = new HashMap<>();
        aliases.put("player", Player.class);

        XStreamMarshaller marshaller = new XStreamMarshaller();

        marshaller.setAliases(aliases);

        return new StaxEventItemWriterBuilder<Player>()
                .name("playersWriter")
                .marshaller(marshaller)
                .resource(new FileSystemResource("target/test-outputs/player.xml"))
                .rootTagName("player")
                .overwriteOutput(true)
                .build();

    }

    @Bean
    @StepScope
    public MultiResourceItemReader<Player> multiItemReader(FlatFileItemReader<Player> playerItemReader) {
        return new MultiResourceItemReaderBuilder<Player>()
                .name("multiFlatFileItemReader")
                .delegate(playerItemReader)
                .resources(resources)
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<Player> playerItemReader(@Value("#{jobParameters[input_file_path]}") String path) {
        return new FlatFileItemReaderBuilder<Player>()
                .name("playerItemReader")
//                .resource(new ClassPathResource("flatFile/players.csv"))
//                .resource(new FileSystemResource("src/main/resources/flatFile/players.csv"))
                .resource(new FileSystemResource(path))
                .lineTokenizer(new DelimitedLineTokenizer())
                .fieldSetMapper(new PlayerFieldSetMapper())
                .build();
    }

    @Bean
    public FlatFileItemWriter playerItemWriter() {
        BeanWrapperFieldExtractor<Player> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"ID", "firstName", "lastName", "position", "birthYear", "debutYear", "sex"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<Player> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<Player>()
                .name("playerItemWriter")
                .resource(new FileSystemResource("target/test-outputs/output.csv"))
                .lineAggregator(lineAggregator)
                .build();
    }

//    @Bean
//    public FlatFileItemWriter playerItemWriter() {
//        return  new FlatFileItemWriterBuilder<Player>()
//                .name("playerItemWriter")
//                .resource(new FileSystemResource("target/test-outputs/output.txt"))
//                .lineAggregator(new PassThroughLineAggregator<>())
//                .build();
//    }


}
