/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafxwithmaven.studentidentification;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class UploadUiView extends Application{
    public static Stage uploadUiStage = new Stage();
    Parent root;
    Scene scene;
    public static boolean alreadyShown = false;
    private UploadUiView() {
             
    }
    
    public static UploadUiView getInstance() {
        return UploadUiViewHolder.INSTANCE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        uploadUiStage = primaryStage;
        uploadUiStage.centerOnScreen();
        uploadUiStage.setResizable(false);
        root = UploadUiPane.getInstance();
        scene = new Scene(root,600,400);
        uploadUiStage.setScene(scene);
        uploadUiStage.setTitle("Student Registration");
        alreadyShown = true;
        uploadUiStage.getIcons().add(new Image(MainApp.class.getResourceAsStream( "/images/logo.png" ))); 
        uploadUiStage.show();        
    }
    @Override
    public void stop(){
        uploadUiStage.hide();
        
    }
    @Override
    public void init(){
        
    }

    private static class UploadUiViewHolder {

        private static final UploadUiView INSTANCE = new UploadUiView();
    }
}
