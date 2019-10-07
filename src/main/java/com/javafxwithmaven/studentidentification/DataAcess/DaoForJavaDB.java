/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafxwithmaven.studentidentification.DataAcess;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Habeeb
 */
public class DaoForJavaDB extends Dao {
    public static boolean recordFound;
    private ResultSet tables, rp;
    private ResultSet schemas;
    private String insertAllStringDatas =
            "INSERT INTO STUDENTRECORDD (NAME,LEVEL,DEPARTMENT,FACAULTY,MATRIC,ID,PASSPORT)"
            + "VALUES(?,?,?,?,?,?,?)";
    
    
    private String createSTUDENTRECORDD = 
            "CREATE TABLE STUDENTRECORDD("+
            "NAME VARCHAR(100) NOT NULL,"+
            "LEVEL VARCHAR(100) NOT NULL,"+
            "DEPARTMENT VARCHAR(100) NOT NULL,"+
            "FACAULTY VARCHAR(100) NOT NULL,"+
            "MATRIC VARCHAR(100) NOT NULL,"+
            "ID VARCHAR(100) NOT NULL,"+
            "PASSPORT VARCHAR(200) NOT NULL,"+
            "PRIMARY KEY(ID)"+
            ")";
    
    private String queryById =
            "SELECT NAME,LEVEL,DEPARTMENT,FACAULTY,MATRIC,PASSPORT "+
            "FROM STUDENTRECORDD "+
            "WHERE ID = ?";
    
    private String queryByMatric =
            "SELECT NAME,LEVEL,DEPARTMENT,FACAULTY,MATRIC,PASSPORT "+
            "FROM STUDENTRECORDD "+
            "WHERE MATRIC = ?";  
    
    private String queryAllMatric = 
            "SELECT MATRIC "+
            "FROM STUDENTRECORDD";
    
    private String deleteUsingSelectedMatric = 
            "DELETE FROM STUDENTRECORDD "+
            "WHERE MATRIC = ?";
    private boolean schemaAndTableExisit = false;
    
    private String url1 = "jdbc:derby:StudentIdentificationDB;create=true"; //url for new DB which will be created if not already exixting
    public static boolean aDeleteWasMade = false;
  //  private String 
    private DaoForJavaDB() {
         init();
         dataBaseProperties();
         queryDbForAllMatric();
    }
    
    public static DaoForJavaDB getInstance() {
        return DaoForJavaDBHolder.INSTANCE;
    }

    @Override
    public void init(){//init() creates database, connection object and the data base (new or exisitng),also create metadata object once
        url= "jdbc:derby:StudentIdentificationDB";
        userName = "codetrik";
        password = "ADEWALEHAMZAT1992";
        driver= "org.apache.derby.jdbc.EmbeddedDriver";
        dataBaseType = "JavaDB derby";
        try{
           connection = createConnection(url,userName,password); //load driver and create connnection object to connect to existing dataBase
           System.out.println("Connection made to existing Derby DB");
           JOptionPane.showMessageDialog(null, "Connection made to existing Derby DB","DB exist", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection = createConnection(url1,userName,password); //load driver, create database and connection object to connect to it.
                System.out.println("new Derby DB created and Connection made to it");
                JOptionPane.showMessageDialog(null, "DB created and Connection made to it","DB Created", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex1) {
                Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex1);
                JOptionPane.showMessageDialog(null, "Sorry Could not create DB","DB Error", JOptionPane.ERROR_MESSAGE);
            }             
        }//end of DB connection object created
        
        //assign MetaData object in to meta
        if(connection!=null){
            try {
                meta = connection.getMetaData();
            } catch (SQLException ex) {
                Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
    }//end of init
    
    
    //this method creates Scema CODETRIK
    private void createSchemaCodetrik(){
        try{
            /*****creating object of statement ****************/
            statement = connection.createStatement();   
            System.out.println(">>>>STATEMENT OBJECT CREATED For schema creation");
            executeStatementUpdate(statement,"CREATE SCHEMA CODETRIK");
            System.out.print("Schema CODETRIK created.........");            
        }catch(SQLException ex){
            Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                statement.close();
                System.out.println(">>>>STATEMENT OBJECT CLOSED after schema creation");
            } catch (SQLException ex) {
                Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //this method check if scema CODETRIK Exists, it then create it if it does not
    private void checkScemaExistence(){
        String rScema;
        boolean found = false;
        try {
               schemas = meta.getSchemas();  
               while(schemas.next()){
                  rScema = schemas.getString("TABLE_SCHEM");
                  System.out.println(rScema);
                  if(rScema.contains("CODETRIK")){
                      System.out.println("CODETRIK SCHEMA FOUND");
                      found = true;
                  }
               }
            if(found==false){
                createSchemaCodetrik();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                schemas.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //this method checks if STUDENTRECORDD exits, otherwise, it create it
    private void checkSTUDENTRECORDD_table(){
        String rTable;
        boolean found = false;
        try {
            tables = meta.getTables(null, null, null, new String[] {"TABLE"});
            while(tables.next()){
                rTable = tables.getString("TABLE_NAME");
                System.out.println(">>>>"+rTable);
                if(rTable.contains("STUDENTRECORDD")){
                      System.out.println("STUDENTRECORDD TABLE FOUND");
                      found = true;
                  }
            }
            if(found == false){
                statement = connection.createStatement();//CREATE STATEMENT OBJECT
                System.out.println(">>>>>Statement is opened for Student record table creation");
                statement.executeUpdate(createSTUDENTRECORDD);
                System.out.println(">>>> TABLE STUDENTRECORDD CREATED");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(statement!=null){
                    statement.close();
                    System.out.println(">>>>>Statement is closed after Student record table creation");
                }
                tables.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void dataInsertionIntoTable_studentrecord(){

          if(schemaAndTableExisit==false){
                checkScemaExistence(); //check if schema CODETRIK (DB Schema is always by default the user name of the DB administrator)exist otherwise create it
                checkSTUDENTRECORDD_table();//check if STUDENTRECORD Table exsit otherwise create it 
                schemaAndTableExisit=true;
          }
          //insert in to the student record TABLE           
        try {
                prepStatement = createPreparedStatement(connection,insertAllStringDatas);
                
                prepStatement.setString(1, DataServicePOJO.getInstance().getName());
                prepStatement.setString(2, DataServicePOJO.getInstance().getLevel());
                prepStatement.setString(3, DataServicePOJO.getInstance().getDepartment());
                prepStatement.setString(4, DataServicePOJO.getInstance().getFacaulty());
                prepStatement.setString(5, DataServicePOJO.getInstance().getMatric());
                prepStatement.setString(6, DataServicePOJO.getInstance().getId());  
                prepStatement.setString(7, DataServicePOJO.getInstance().getImagePath());
                System.out.println(">>>>>>>dataInsertionIntoTable_studentrecord() prepared statement object opened");
                prepStatement.executeUpdate();
                System.out.println(">>>>congrat data insertion to STUDENTRECORDD sucessfull <<<<<");
        } catch (SQLException ex) {
            Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              try {
                  prepStatement.close();
                  System.out.println(">>>>>>>dataInsertionIntoTable_studentrecord() prepared statement object closed");
              } catch (SQLException ex) {
                  Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
              }
        }          
    }
    public void dataQueryFromTable_studentrecordById(){
          recordFound = false;
          if(schemaAndTableExisit==false){
                checkScemaExistence(); //check if schema CODETRIK (DB Schema is always by default the user name of the DB administrator)exist otherwise create it
                checkSTUDENTRECORDD_table();//check if STUDENTRECORD Table exsit otherwise create it 
                schemaAndTableExisit=true;
          }             
        try {
            prepStatement = createPreparedStatement(connection,queryById);
            prepStatement.setString(1, DataServicePOJO.getInstance().getId());
            System.out.println(">>>>>>>dataQueryFromTable_studentrecordById() prepared statement object opened");
            rs = prepStatement.executeQuery();
            System.out.println(">>>>Congrat, the App suessfully query the database using the specified ID");
            //process result set
            while(rs.next()){
                //NAME,LEVEL,DEPARTMENT,FACAULTY,ID,PASSPORT
                System.out.println(">>>>Some record found. processing....");
                DataServicePOJO.getInstance().setdName(rs.getString("NAME"));
                DataServicePOJO.getInstance().setdLevel(rs.getString("LEVEL"));
                DataServicePOJO.getInstance().setdDepartment(rs.getString("DEPARTMENT"));
                DataServicePOJO.getInstance().setdFacaulty(rs.getString("FACAULTY"));
                DataServicePOJO.getInstance().setdMatric(rs.getString("MATRIC"));
                DataServicePOJO.getInstance().setdImagePath(rs.getString("PASSPORT"));
                System.out.println(">>>>one row in result set just got processed");
                recordFound = true;
            }
          /*  if(){
                JOptionPan!rs.first()e.showMessageDialog(null, "No Record","Sorry, no record found", JOptionPane.INFORMATION_MESSAGE);
            } */           
        } catch (SQLException ex) {
            Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              try {
                  prepStatement.close();
                  System.out.println(">>>>>>>dataQueryFromTable_studentrecordById() prepared statement object closed");
                  rs.close();
                  System.out.println(">>>>>>>>dataQueryFromTable_studentrecordById() resultset object closed");
              } catch (SQLException ex) {
                  Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
        
    }
    public void dataQueryFromTable_studentrecordByMatric(){
          recordFound = false;
          if(schemaAndTableExisit==false){
                checkScemaExistence(); //check if schema CODETRIK (DB Schema is always by default the user name of the DB administrator)exist otherwise create it
                checkSTUDENTRECORDD_table();//check if STUDENTRECORD Table exsit otherwise create it 
                schemaAndTableExisit=true;
          }             
        try {
            prepStatement = createPreparedStatement(connection,queryByMatric);
            prepStatement.setString(1, DataServicePOJO.getInstance().getMatric());
            System.out.println(">>>>>>>dataQueryFromTable_studentrecordByMatric() prepared statement object opened");
            rs = prepStatement.executeQuery();
            System.out.println(">>>>Congrat, the App suessfully query the database using the specified MATRIC");
            //process result set
            while(rs.next()){
                //NAME,LEVEL,DEPARTMENT,FACAULTY,ID,PASSPORT
                System.out.println(">>>>Some record found. processing....");
                DataServicePOJO.getInstance().setdName(rs.getString("NAME"));
                DataServicePOJO.getInstance().setdLevel(rs.getString("LEVEL"));
                DataServicePOJO.getInstance().setdDepartment(rs.getString("DEPARTMENT"));
                DataServicePOJO.getInstance().setdFacaulty(rs.getString("FACAULTY"));
                DataServicePOJO.getInstance().setdMatric(rs.getString("MATRIC"));
                DataServicePOJO.getInstance().setdImagePath(rs.getString("PASSPORT"));
                System.out.println(">>>>one row in result set just got processed");
                recordFound = true;
            }
          /*  if(){
                JOptionPan!rs.first()e.showMessageDialog(null, "No Record","Sorry, no record found", JOptionPane.INFORMATION_MESSAGE);
            } */           
        } catch (SQLException ex) {
            Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              try {
                  prepStatement.close();
                  System.out.println(">>>>>>>dataQueryFromTable_studentrecordByMatric() prepared statement object closed");
                  rs.close();
                  System.out.println(">>>>>>>>dataQueryFromTable_studentrecordByMatric() resultset object closed");
              } catch (SQLException ex) {
                  Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
        
    }    
    private void queryDbForAllMatric(){
          if(schemaAndTableExisit==false){
                checkScemaExistence(); //check if schema CODETRIK (DB Schema is always by default the user name of the DB administrator)exist otherwise create it
                checkSTUDENTRECORDD_table();//check if STUDENTRECORD Table exsit otherwise create it 
                schemaAndTableExisit=true;
          }        
        try {
            boolean found=false;
            statement = connection.createStatement();
            System.out.println(">>Statement object created so as to look in to all MATRIC in the DB");
            rs = statement.executeQuery(queryAllMatric);
            while(rs.next()){
                System.out.println(rs.getString("MATRIC")+" "+" is present in the database");
                found = true;
            }
            if(found==false){
                System.out.println(">>>Sorry no MATRIC found");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                statement.close();
                rs.close();
                System.out.println(">>Statement object created so as to look in to all MATRIC in the DB WAS CLOSED AFTER USED");
            } catch (SQLException ex) {
                Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void deleteUsingMatric(){
        int affect = 0;
           if(schemaAndTableExisit==false){
                checkScemaExistence(); //check if schema CODETRIK (DB Schema is always by default the user name of the DB administrator)exist otherwise create it
                checkSTUDENTRECORDD_table(); //check if STUDENTRECORD Table exsit otherwise create it 
                schemaAndTableExisit=true;
          }
           try{
               prepStatement = createPreparedStatement(connection,deleteUsingSelectedMatric);
               prepStatement.setString(1, DataServicePOJO.getInstance().getMatric());
               affect = executePreparedStatementUpdate(prepStatement); //calls executeUpdate on preperedStatement object
               if(affect!=0){
                   aDeleteWasMade = true;
               }
           } catch(SQLException ex){
               Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
           } finally{
               try {
                   prepStatement.close();
               } catch (SQLException ex) {
                   Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
    }
    private void dataBaseProperties(){
        boolean prop=false;
        System.out.println("printing client info proerties<<<<<<<<<<<<");
        try {
            rp = connection.getMetaData().getClientInfoProperties();
            while(rp.next()){
                prop=true;
                System.out.println(rs.getString("NAME")+"  "+rs.getString("MAX_LEN")+"  "+rs.getString("DEFAULT_VALUE")+ "  "+ rs.getString("DESCRIPTION"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(prop==false){
                    System.out.println("Client proerty is not available<<<<<<<<<");
                }
                rp.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoForJavaDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private static class DaoForJavaDBHolder {

        private static final DaoForJavaDB INSTANCE = new DaoForJavaDB();
    }
}
