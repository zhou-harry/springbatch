package com.example.demo.flatFile.mapper;

import com.example.demo.flatFile.entity.Player;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

/**
 * @author zhouhong
 * @version 1.0
 * @title: PlayerFieldSetMapper
 * @description: TODO
 * @date 2019/6/10 16:12
 */
public class PlayerFieldSetMapper implements FieldSetMapper<Player> {

    public Player mapFieldSet(FieldSet fieldSet) {
        Player player = new Player();

        player.setID(fieldSet.readString(0));
        player.setLastName(fieldSet.readString(1));
        player.setFirstName(fieldSet.readString(2));
        player.setPosition(fieldSet.readString(3));
        player.setBirthYear(fieldSet.readInt(4));
        player.setDebutYear(fieldSet.readInt(5));

        return player;
    }

}