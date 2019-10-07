/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafxwithmaven.studentidentification.DataAcess;

import java.io.FileInputStream;
import java.io.InputStream;


/**
 *
 * @author Habeeb
 */
public class DataServicePOJO {
    
    private DataServicePOJO() {
    }
    //variable to hold data going to DB
    public  String name;
    public  String level;
    public  String department;
    public String facaulty;
    public String matric;
    public String id;
    public int fileLenght;

    public String imagePath;
    //variables to hold data retrieved from DB
    public  String dName;
    public  String dLevel;
    public  String dDepartment;
    public String dFacaulty;
    public String dMatric;
    public String dImagePath;    
    public static DataServicePOJO getInstance() {
        return DataServiceHolder.INSTANCE;
    }
    
    private static class DataServiceHolder {

        private static final DataServicePOJO INSTANCE = new DataServicePOJO();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println(this.name);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
        System.out.println(this.level);
    }

    public String getDepartment() {
        return department;
        
    }

    public void setDepartment(String department) {
        this.department = department;
        System.out.println(this.department);
    }

    public String getFacaulty() {
        return facaulty;
    }

    public void setFacaulty(String facaulty) {
        this.facaulty = facaulty;
        System.out.println(this.facaulty);
    }
    public String getMatric() {
        return matric;
    }

    public void setMatric(String matric) {
        this.matric = matric;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        System.out.println(this.id);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String path) {
        this.imagePath = path;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdLevel() {
        return dLevel;
    }

    public void setdLevel(String dLevel) {
        this.dLevel = dLevel;
    }

    public String getdDepartment() {
        return dDepartment;
    }

    public void setdDepartment(String dDepartment) {
        this.dDepartment = dDepartment;
    }

    public String getdFacaulty() {
        return dFacaulty;
    }

    public void setdFacaulty(String dFacaulty) {
        this.dFacaulty = dFacaulty;
    }

    public String getdMatric() {
        return dMatric;
    }

    public void setdMatric(String dId) {
        this.dMatric = dId;
    }

    public String getdImagePath() {
        return dImagePath;
    }

    public void setdImagePath(String dImageAsInputStream) {
        this.dImagePath = dImageAsInputStream;
    }
    public void setFileLenght(int fileName) {
        this.fileLenght = fileName;
    }

    public int getFileLenght() {
        return fileLenght;
    }    
}
