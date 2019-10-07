package com.javafxwithmaven.studentidentification;

import com.javafxwithmaven.studentidentification.DataAcess.DaoForJavaDB;
import com.javafxwithmaven.studentidentification.DataAcess.DataServicePOJO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JOptionPane;
 
public class UploadUiPane extends GridPane{
    FileChooser choose;
    String imagePath;
   /*************Nodes*****************************/
    public Button fileChooserButton = new Button("Select Passport");


    public  Label nameLabel = new Label("Name");
    public  Label levelLabel = new Label("Level");
    public  Label departmentLabel = new Label ("Department");
    public  Label facaultyLabel = new Label("Facaulty");
    public  Label idLabel = new Label("ID");
    public  Label matricLabel = new Label("Matric Number");
    
    public  TextField nameField = new TextField();
    public  TextField levelField = new TextField();
    public  TextField departmentField = new TextField();
    public  TextField facualtyField = new TextField();
    public  TextField idField = new TextField();
    public  TextField matricField = new TextField();


    
    public  Button upload = new Button("Upload");
   
    private UploadUiPane() {
           init();  
          
    }
  
   private void init(){
      //construct scene tree
       add(nameLabel,0,0);
       add(levelLabel,0,1);
       add(departmentLabel,0,2);
       add(facaultyLabel,0,3);
       add(matricLabel,0,4);
       add(idLabel,0,5);
       add(upload,0,6);
       add(nameField,1,0);
       add(levelField,1,1);
       add(departmentField,1,2);
       add(facualtyField,1,3);
       add(matricField,1,4);
       add(idField,1,5);
       add(fileChooserButton,1,6);
       //add properties to nodes
       nameLabelProperties();
       nameFieldProperties();  
       gridPaneProperties();
       new UploadUiController(); //instantiate the Controller class for the UploadUiPane java based designed UI
      
   }
   private void nameLabelProperties(){
       nameLabel.setPrefSize(70, 20);
       nameLabel.setMaxSize(80,22);   
   }
   private void nameFieldProperties(){
       nameField.setPrefSize(200,20);
       nameField.setMaxSize(210,22);
   }
   private void gridPaneProperties(){
       setPrefSize(600,400);
       setVgap(5);
       setHgap(5);
       setPadding(new Insets(10,10,10,10));
       setAlignment(Pos.CENTER);
   }
   /*singleton code*/
   
    public static UploadUiPane getInstance() {
        return UploadUIViewHolder.INSTANCE;
    }
    
    private static class UploadUIViewHolder {

        private static final UploadUiPane INSTANCE = new UploadUiPane();
    }
 
    /**
     * @return the nameLabel
     */
    public Label getNameLabel() {
        return nameLabel;
    }

    /**
     * @return the levelLabel
     */
    public Label getLevelLabel() {
        return levelLabel;
    }

    /**
     * @return the departmentLabel
     */
    public Label getDepartmentLabel() {
        return departmentLabel;
    }

    /**
     * @return the facaultyLabel
     */
    public Label getFacaultyLabel() {
        return facaultyLabel;
    }

    /**
     * @return the idLabel
     */
    public Label getIdLabel() {
        return idLabel;
    }

    /**
     * @return the nameField
     */
    public TextField getNameField() {
        return nameField;
    }

    /**
     * @return the levelField
     */
    public TextField getLevelField() {
        return levelField;
    }

    /**
     * @return the departmentField
     */
    public TextField getDepartmentField() {
        return departmentField;
    }

    /**
     * @return the facualtyField
     */
    public TextField getFacualtyField() {
        return facualtyField;
    }

    /**
     * @return the idField
     */
    public TextField getIdField() {
        return idField;
    }
    public TextField getMatricField() {
        return matricField;
    }
    /**
     * @return the upload
     */
    public Button getUpload() {
        return upload;
    }
    public Button getFileChooserButton() {
        return fileChooserButton;
    }
 /****************Controller class for UploadUiPane User Interface design***************/
    private class UploadUiController implements Initializable{
        UploadUiController(){
            this.init();
            EventHandler();
        }
        /*is use t0 handle all events of the UploadUiPane : java based user interface design*/
        private void EventHandler(){
            //handler for FileChooserButton
            getFileChooserButton().setOnAction((ActionEvent event) -> {
                File image = choose.showOpenDialog(UploadUiView.uploadUiStage);
                try {
                    
                   DataServicePOJO.getInstance().setFileLenght((int) image.length());
                   imagePath = (image.getAbsoluteFile().toURI().toString());//get Image path
                   System.out.println("The image path uploaded is >>> "+imagePath);
                } catch (Exception ex) {
                    Logger.getLogger(UploadUiPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            //Handler for UploadButton
            getUpload().setOnAction((ActionEvent event) -> {
                //write to data service POJO
                if(getNameField().getText().isEmpty()||getLevelField().getText().isEmpty()||getDepartmentField().getText().isEmpty()
                        ||getFacualtyField().getText().isEmpty()||getIdField().getText().isEmpty()||getMatricField().getText().isEmpty()||imagePath==null){
                    JOptionPane.showMessageDialog(null, "NO field can be empty, also select passport","Empty data", JOptionPane.ERROR_MESSAGE);
                }else{
                    DataServicePOJO.getInstance().setName(getNameField().getText());
                    DataServicePOJO.getInstance().setLevel(getLevelField().getText());
                    DataServicePOJO.getInstance().setDepartment(getDepartmentField().getText());
                    DataServicePOJO.getInstance().setFacaulty(getFacualtyField().getText());
                    DataServicePOJO.getInstance().setMatric(getMatricField().getText());
                    DataServicePOJO.getInstance().setId(getIdField().getText());
                    DataServicePOJO.getInstance().setImagePath(imagePath);
                    DaoForJavaDB.getInstance().dataInsertionIntoTable_studentrecord();
                    //clear UI fields
                    getNameField().setText("");
                    getLevelField().setText("");
                    getDepartmentField().setText("");
                    getFacualtyField().setText("");
                    getMatricField().setText("");
                    getIdField().setText("");
                    imagePath = null;
                    System.out.println("All datas sent to POJO"); 
                    
                }                                
            });
        }
        private void init(){
           choose = new FileChooser(); //object instance of a file chooser
           //set configuration of the file chooser
           choose.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.jpg","*.png","*.gif"));
           choose.setTitle("Select Student Passport");
           choose.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        @Override
        public void initialize(URL location, ResourceBundle resources) {
          //this method will only work if the user interface is designed with FXML, in replacement of this methis, init() is create
        }
        
    }

}
