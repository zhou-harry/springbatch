package com.example.demo.jdbc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhouhong
 * @version 1.0
 * @title: CustomerCredit
 * @description: TODO
 * @date 2019/6/11 15:05
 */
@Entity
@Table(name = "CUSTOMER")
public class CustomerCredit implements Serializable {

    @Id
    @Column(name = "id" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name" )
    private String name;

    @Column(name = "credit" )
    private BigDecimal credit;


    @Override
    public String toString() {
        return "CustomerCredit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
}
