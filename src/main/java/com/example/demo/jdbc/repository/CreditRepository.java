package com.example.demo.jdbc.repository;

import com.example.demo.jdbc.entity.CustomerCredit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhouhong
 * @version 1.0
 * @title: CreditRepository
 * @description: TODO
 * @date 2019/6/11 17:18
 */
public interface CreditRepository extends JpaRepository<CustomerCredit, Long> {



}
