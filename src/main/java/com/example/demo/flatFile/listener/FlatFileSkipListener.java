package com.example.demo.flatFile.listener;

import com.example.demo.flatFile.entity.Player;
import org.springframework.batch.core.SkipListener;

/**
 * @author zhouhong
 * @version 1.0
 * @title: FlatFileSkipListener
 * @description: TODO
 * @date 2019/6/13 16:04
 */
public class FlatFileSkipListener implements SkipListener<Player, Player> {
    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("onSkipInRead: " + t);
    }

    @Override
    public void onSkipInWrite(Player item, Throwable t) {
        System.out.println("onSkipInWrite: " + item);
    }

    @Override
    public void onSkipInProcess(Player item, Throwable t) {
        System.out.println("onSkipInProcess: " + item);
    }
}
