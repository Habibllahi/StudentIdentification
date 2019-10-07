
package com.javafxwithmaven.studentidentification.DataAcess;
import java.sql.*;

public abstract class Dao {
    //let us create inheritable objects that is needed for jdbc connetion
    public Connection connection = null;
    public Statement statement = null;
    public ResultSet resultSet= null;
    public PreparedStatement prepStatement = null;
    public String url = "";
    String userName = "";
    String password = "";
    String driver = " ";
    String dataBaseType = " ";
    DatabaseMetaData meta;
    ResultSet rs;
    
    // let us create object of nullpointer exception 
    private static final NullPointerException e = new NullPointerException("You are getting this error message because you are pointing to an empty jdbc object");
    // let us create a method to create connection
    public Connection createConnection(String url, String userName, String password) throws SQLException{
        try{
            Class.forName(driver);
            System.out.println("Successfully loaded the " +dataBaseType+ " driver");
        }catch(ClassNotFoundException y){
            System.err.println(y.getCause());
        }
        return DriverManager.getConnection(url, userName, password);
    }//end of createConnection()
    public Connection createConnection(String url) throws SQLException{
        try{
            Class.forName(driver);
            System.out.println("Successfully loaded the " +dataBaseType+ " driver");
        }catch(ClassNotFoundException y){
            System.err.println(y.getCause());
        }
        return DriverManager.getConnection(url, userName, password);
    }//end of createConnection()    
    //let us create a method that close connection taking Statement and Connection object
    public void destroy(Statement st, Connection conn){
        if(st!=null && conn!=null){
            try {
                if(st !=null)
                    st.close();
                //st = null;
                if(!conn.isClosed()){//check if the connection object is already close, else close
                    conn.close();
                }
                 reset();
            } catch (SQLException ex) {
               System.err.println(ex.getCause());
            }
            System.out.println("connection to DB is closed,Statement and connection objects destroyed");
        }else{
            System.out.println("Error either the connection or statement is null!!!");
            throw e;
        }
    }//end of destroy()
    
    //let us create a method that close connection taking PrearedStatement and Connection object
    public void destroy(PreparedStatement st, Connection conn){
        if(st!=null || conn!=null){
            try {
                if(st !=null)
                    st.close();
                if(!conn.isClosed()){//check if the connection object is already close, else close
                    conn.close();
                }
                 reset(); //reset all parameter back to null
            } catch (SQLException ex) {
               System.err.println(ex.getCause());
            }
            System.out.println("connection to DB is closed, Prepare Statement and connection objects destroyed");
        }else{
            System.out.println("Error either the connection or statement is null!!!");
            throw e;
        }
    }//end of destroy()
    
    //let us create a method to create statement object 
    public Statement createStatement(Connection conn){
        Statement st = null;
        if(conn !=null){
            try {
                st = conn.createStatement();
                System.out.println("statemnt object created");
            } catch (SQLException ex) {
                System.err.println(ex.getCause());
            }
        }else{
            System.out.println("Error the connection is null!!!");
            throw e;
        }
        return st;
    }//end of createStatement()
    
    //let us create a method to create PreparedStatement object
    public PreparedStatement createPreparedStatement(Connection conn, String query){
        PreparedStatement pst = null;
        if(conn != null){
            try {
                pst = conn.prepareStatement(query);
                System.out.println("prepared statemnt object created");
            } catch (SQLException ex) {
                System.err.println(ex.getCause());
            }
        }else{
            System.out.println("Error the connection is null!!!");
            throw e;
        }
        return pst;
    }//end of createPreparedStatement()
    
    //let us create  method to execute DML 
    public int executeStatementUpdate(Statement st, String query){
        int a= 0;
        if(st!=null){
            try {
                a= st.executeUpdate(query);
            } catch (SQLException ex) {
                System.err.println(ex.getCause());
            }
        }else{
            System.out.println("Error the statement is null!!!");
            throw e;
        }
        return a;
    }//end of executeStatementUpdate()
    
    public int executePreparedStatementUpdate(PreparedStatement pst){
        int a= 0;
        if(pst!=null){
            try {
                a= pst.executeUpdate();
                System.out.println("executeUpdate performed on object of prepared statement");
            } catch (SQLException ex) {
                System.err.println(ex.getCause());
            }
        }else{
            System.out.println("Error the prepared statement is null!!!");
            throw e;
        }
        return a;        
    }//end of executePreparedStatementUpdate()
    
    //let us create a method that execute DQL and return object of ResultSet
    
    public ResultSet executeStatementQuery(Statement st, String query){
        ResultSet r = null;
        if(st != null){
            try {
                r = st.executeQuery(query);
            } catch (SQLException ex) {
                System.err.println(ex.getCause());
            }
        }else{
            System.out.println("Error the statement is null!!!");
            throw e;
        }
        return r;
    }//end of executeStatementQuery()
    
    //let us create a method that execute DQL and return object of ResultSet
    public ResultSet executePreparedQuery(PreparedStatement st){
        ResultSet r = null;
        if(st != null){
            try {
                r = st.executeQuery();
            } catch (SQLException ex) {
                System.err.println(ex.getCause());
            }
        }else{
            System.out.println("Error the prepared statement is null!!!");
            throw e;
        }
        return r;
    }//end of executePreparedQuery()    
    
    //let us create a method that checks if the Resultset has content
    public boolean hasContent(ResultSet rs){
        boolean flag = false;
        if(rs!=null){
            try {
                if(rs.next()){
                    flag = true;
                }
            } catch (SQLException ex) {
                System.err.println(ex.getCause());
            }
        }else{
            System.out.println("Error the resultset is null!!!");
            throw e;
        }
        return flag;
    }//end of hasContent()
    
    //let us create an init() method that initializes the jdbc process as desired
    
    public abstract void init();
    public void reset(){
        connection = null;
        statement = null;
        prepStatement = null;
    }
    
}//end of class
