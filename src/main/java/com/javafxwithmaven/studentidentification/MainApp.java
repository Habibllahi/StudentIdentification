package com.javafxwithmaven.studentidentification;

import com.javafxwithmaven.studentidentification.DataAcess.DaoForJavaDB;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
      Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Student Identification");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream( "/images/logo.png" ))); 
        stage.show();        
    }
    @Override
    public void init() throws Exception {

    }
   @Override
    public void stop() throws Exception { 
       // DaoForJavaDB.getInstance().statement.close();
      //  DaoForJavaDB.getInstance().connection.close();
      //  System.out.println("Connection and statement object used by this App are now closed");
 	Platform.exit();
	System.exit(0);    
    }
    public static void main(String[] args) {
        launch(args);
    }

}
