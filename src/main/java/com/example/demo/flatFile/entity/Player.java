package com.example.demo.flatFile.entity;

import java.io.Serializable;

/**
 * @author zhouhong
 * @version 1.0
 * @title: Player
 * @description: TODO
 * @date 2019/6/10 16:10
 */
public class Player implements Serializable {

    private String ID;
    private String firstName;
    private String lastName;
    private String position;
    private int birthYear;
    private int debutYear;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getDebutYear() {
        return debutYear;
    }

    public void setDebutYear(int debutYear) {
        this.debutYear = debutYear;
    }

    @Override
    public String toString() {
        return "Player{" +
                "ID='" + ID + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", position='" + position + '\'' +
                ", birthYear=" + birthYear +
                ", debutYear=" + debutYear +
                ", sex='" + sex + '\'' +
                '}';
    }
}
