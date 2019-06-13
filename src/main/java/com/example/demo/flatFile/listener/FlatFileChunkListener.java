package com.example.demo.flatFile.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * @author zhouhong
 * @version 1.0
 * @title: FlatFileChunkListener
 * @description: TODO
 * @date 2019/6/13 15:49
 */
public class FlatFileChunkListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        System.out.println("Chunk Listener Before:" + context.getStepContext());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        System.out.println("Chunk Listener After:" + context.getStepContext());
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        System.err.println("Chunk Listener Error:" + context.getStepContext());
    }
}
