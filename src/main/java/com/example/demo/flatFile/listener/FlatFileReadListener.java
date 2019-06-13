package com.example.demo.flatFile.listener;

import com.example.demo.flatFile.entity.Player;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.item.file.FlatFileParseException;

/**
 * @author zhouhong
 * @version 1.0
 * @title: FlatFileReadListener
 * @description: TODO
 * @date 2019/6/13 15:56
 */
public class FlatFileReadListener implements ItemReadListener<Player> {

    @Override
    public void beforeRead() {
        System.out.println("Befor Read Listener。。。");
    }

    @Override
    public void afterRead(Player item) {
        System.out.println("After Read Listener："+item);
    }

    @Override
    public void onReadError(Exception ex) {
        if (ex instanceof FlatFileParseException){
            FlatFileParseException ffex=(FlatFileParseException)ex;
            System.out.println("Error on line "+ffex.getLineNumber()+" Read Listener："+ffex.getInput());
        }
    }
}
