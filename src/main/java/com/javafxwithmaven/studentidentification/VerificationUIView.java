/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafxwithmaven.studentidentification;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


//sigleton Class
public class VerificationUIView extends Application{
    public static Stage verificationUIStage = new Stage();
    public static boolean alreadyShown = false;
    private VerificationUIView() {
    }
    
    public static VerificationUIView getInstance() {
        return VerificationUIViewHolder.INSTANCE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        verificationUIStage = primaryStage;
        verificationUIStage.centerOnScreen();
        verificationUIStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/verificationUI.fxml"));
        Scene scene = new Scene(root,600,400);
        scene.getStylesheets().add("/styles/verificationui.css");
        verificationUIStage.setScene(scene);
        verificationUIStage.setTitle("Verification UI");
        alreadyShown = true;
        verificationUIStage.getIcons().add(new Image(MainApp.class.getResourceAsStream( "/images/logo.png" ))); 
        verificationUIStage.show();   
    }
    @Override
    public void stop(){
        verificationUIStage.hide();
        
    }
    @Override
    public void init(){
        
    }    
    private static class VerificationUIViewHolder {
        private static final VerificationUIView INSTANCE = new VerificationUIView();
    }
}
