package com.example.demo.xml_json.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhouhong
 * @version 1.0
 * @title: Trade
 * @description: TODO
 * @date 2019/6/11 10:37
 */
public class Trade implements Serializable {

    private String isin;
    private BigDecimal price;
    private String customer;
    private Long quantity;

    @Override
    public String toString() {
        return "Trade{" +
                "isin='" + isin + '\'' +
                ", price=" + price +
                ", customer='" + customer + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
