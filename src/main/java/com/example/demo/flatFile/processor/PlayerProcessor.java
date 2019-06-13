package com.example.demo.flatFile.processor;

import com.example.demo.flatFile.entity.Player;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author zhouhong
 * @version 1.0
 * @title: PlayerProcessor
 * @description: TODO
 * @date 2019/6/11 9:15
 */
public class PlayerProcessor implements ItemProcessor<Player, Player> {
    @Override
    public Player process(Player item) throws Exception {

        item.setSex("未知");
        System.out.println(item.toString());

        return item;
    }
}
