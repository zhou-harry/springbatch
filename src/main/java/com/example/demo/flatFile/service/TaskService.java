package com.example.demo.flatFile.service;

import org.springframework.stereotype.Service;

/**
 * @author zhouhong
 * @version 1.0
 * @title: TaskService
 * @description: TODO
 * @date 2019/6/13 16:34
 */
@Service
public class TaskService {

    public void updateTask(){
        System.out.println("TaskService.updateTask============================="+Thread.currentThread().getId());
    }

}
