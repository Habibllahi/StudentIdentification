package com.javafxwithmaven.studentidentification;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


public class LoginController implements Initializable {
    
    @FXML
    private Label userNameLabel,passwordLabel,loginStatusLabel;
    @FXML
    private Button loginButton,uploadButton,verifyButton;
    @FXML 
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private void onLoginButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        
        //check the login credential
        if(userNameField.getText().equals("admin")&& passwordField.getText().equals("admin")){
            loginStatusLabel.setText("Login sucessful, now you can upload or verify");
            loginStatusLabel.setTextFill(Color.BLACK);
            uploadButton.setDisable(false);
            verifyButton.setDisable(false);             
        }  
        else{
            loginStatusLabel.setText("Login failed");
            loginStatusLabel.setTextFill(Color.RED);
            uploadButton.setDisable(true);
            verifyButton.setDisable(true);           
        }
        //open new window and close existing window
    }
    @FXML
    private void onClickUploadButton(ActionEvent event){
        if(uploadButton.isDisabled()==false){
            //button event here
            System.out.println("upload button responds to click");
            try {
                if(UploadUiView.alreadyShown==false){
                    UploadUiView.getInstance().start(UploadUiView.uploadUiStage);
                }else{
                    UploadUiView.uploadUiStage.show();
                }
                UploadUiView.uploadUiStage.setOnCloseRequest(e -> {
			UploadUiView.getInstance().stop();
		});
            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    @FXML
    private void onClickVerifyButton(ActionEvent event){
         if(verifyButton.isDisabled()==false){
            //button event here
            System.out.println("Verify button responds to click");
             try {
                 if(VerificationUIView.alreadyShown==false){
                     VerificationUIView.getInstance().start(VerificationUIView.verificationUIStage);
                 }else{
                     VerificationUIView.verificationUIStage.show();
                 }
                 VerificationUIView.verificationUIStage.setOnCloseRequest(e -> {
			VerificationUIView.getInstance().stop();
		});
             } catch (Exception ex) {
                 Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
             }
        }       
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uploadButton.setDisable(true);
        verifyButton.setDisable(true);
    }    
}
