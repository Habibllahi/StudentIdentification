package com.javafxwithmaven.studentidentification;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.javafxwithmaven.studentidentification.ComportAcess.ComportParams;
import static com.javafxwithmaven.studentidentification.ComportAcess.ComportParams.SelectedPortIndex;
import static com.javafxwithmaven.studentidentification.ComportAcess.ComportParams.dontPorpulate;
import com.javafxwithmaven.studentidentification.ComportAcess.serialComm;
import static com.javafxwithmaven.studentidentification.ComportAcess.serialComm.ports;
import static com.javafxwithmaven.studentidentification.ComportAcess.serialComm.readBuffer;
import static com.javafxwithmaven.studentidentification.ComportAcess.serialComm.readSucessful;
import com.javafxwithmaven.studentidentification.DataAcess.DaoForJavaDB;
import com.javafxwithmaven.studentidentification.DataAcess.DataServicePOJO;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

public class VerificationUIController implements Initializable{    
    //UI control nodes
   int answer;
   boolean portRead = false;
   @FXML
   private TextField nameField,levelField,deptField,facualtyField,matricField;
   @FXML
   private ComboBox<String> portDiscription;
   @FXML
   private Button queryButton, deleteButton;
   @FXML
   private ImageView studentPhoto;
   
    @FXML
    private void portComboActionEvent(ActionEvent e){
        try{
                ComportParams.PORTDESCRIPTION = portDiscription.getValue(); //get the selected port description from the combo box
                SelectedPortIndex = portDiscription.getSelectionModel().getSelectedIndex();//get selected index of the selected port description
                System.out.println("you selected "+ ComportParams.PORTDESCRIPTION);
                openComPortIfClosed();//open selected com port
                setComportParameter();
                serialComm.ports[SelectedPortIndex].setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING,100,100);//set comport timeout 
        }catch(Exception error){
            JOptionPane.showMessageDialog(null, error.getMessage(),"Action Event Error on Port", JOptionPane.ERROR_MESSAGE); 
        }
    }
    @FXML
    private void queryButtonAction(ActionEvent e){
        //first listen to the selected serial port and read byte from it, ensure you read all byte sent     
        
    try{
              ports[SelectedPortIndex].addDataListener(
               new SerialPortDataListener(){
                   @Override
                   public int getListeningEvents() {
                       return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                   }

                   @Override
                   public void serialEvent(SerialPortEvent event){                       
                           if(event.getEventType()==SerialPort.LISTENING_EVENT_DATA_AVAILABLE){
                               //ChangedFlag=true;//make true so that the app can resume to the previous mode          
                               readBuffer = new byte[serialComm.ports[SelectedPortIndex].bytesAvailable()];//give just enough size to the readBuffer byte[] array
                               readSucessful = serialComm.ports[SelectedPortIndex].readBytes(readBuffer,readBuffer.length);
                               if(readSucessful==readBuffer.length){// if all available bytes as been read, then convert the bytes in to string
                                   //set the read byte to string as the ID value of the student in the DataServicePOJO singleton class 
                                   String s = new String(readBuffer);//second convert the read byte in to string
                                   DataServicePOJO.getInstance().setId(s);//assign the read byte converted to string as the ID
                                   System.out.print("ID "+s+" well recieved from the selected port and set to DataServicePOJO");
                                   portRead=true;
                                  answer = JOptionPane.showConfirmDialog(null,"ID is: "+s,"Press OK to Query",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);         
                        }
                    }
                   }
               }
        );
    }catch(Exception em){
        JOptionPane.showMessageDialog(null, "Nothing read from port or port not seleccted","Listen Event Error on Port", JOptionPane.ERROR_MESSAGE); /**********/
    }   
    
        //query the ID from the data base using the id recieved and approved from port
        if(portRead==true){
            queryTheStudentRecordDBbyID_AndProcessResult();
            portRead=false;
        }else{
            DataServicePOJO.getInstance().setMatric(matricField.getText());
            queryTheStudentRecordDBbyMatric_AndProcessResult();
        }
        if(answer==JOptionPane.CANCEL_OPTION){
            JOptionPane.showMessageDialog(null,"Please select port or enter and ID to query");
        }

        //post all result gotten from result set in to the database
    }
    //code to fetch all available ports and populated the combox
    @FXML
    private void PortComboHooverEvent(MouseEvent event){
        try{
            if(dontPorpulate == false){
               serialComm comm = new serialComm();
               System.out.println(comm);
               //itrate through the portsName array to fetch the strings to a variable called description
               int x = 0;
               for(String description:serialComm.portsName){

                   if(x<serialComm.portsName.length){
                       portDiscription.getItems().add(description);//populate the Combo box with port description of all available ports
                       x+=1;
                   }
               }
                   dontPorpulate = true;//set to true so it does not have to perform hoover action until reset is done
                   System.out.println("Port and portdescription name are now gotten. this will occur once");
            }
        }catch(Exception g){
           JOptionPane.showMessageDialog(null, g.getMessage(),"Exception error", JOptionPane.ERROR_MESSAGE); 
        }

    }
    @FXML
    private void deleteButtonEvent(ActionEvent event){
        if(!matricField.getText().equals("")){
            DataServicePOJO.getInstance().setMatric(matricField.getText());//SET the Id value to be used for query 
            DaoForJavaDB.getInstance().deleteUsingMatric();
            if(DaoForJavaDB.aDeleteWasMade==true){
                nameField.setText("");
                levelField.setText("");
                deptField.setText("");
                facualtyField.setText("");
                matricField.setText("");            
                studentPhoto.setImage(new Image(getClass().getResource("/images/avatar.jpg").toString()));                
                JOptionPane.showMessageDialog(null, "Record is deleted from the table successfully...", "status", JOptionPane.INFORMATION_MESSAGE);
                DaoForJavaDB.aDeleteWasMade=false;
            }else{
                nameField.setText("");
                levelField.setText("");
                deptField.setText("");
                facualtyField.setText("");
                matricField.setText("");            
                studentPhoto.setImage(new Image(getClass().getResource("/images/avatar.jpg").toString()));                                
                JOptionPane.showMessageDialog(null, "Please record is not found", "record not found", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Please provide MATRIC to query with", "MATRIC value empty", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    /************************************************************/
   public int getBitPerWord(){
       return ComportParams.selectedBitPerWord;
   }
   public int getBaurdRate(){
     return ComportParams.selectedBaudRate;
   }
   public int getParity(){
       return ComportParams.selectedParity;
   }
   public int getStopBit(){
       return ComportParams.selectedStopBit;
   }
   public void setComportParameter(){
      serialComm.ports[SelectedPortIndex].setComPortParameters(getBaurdRate(),getBitPerWord(),getStopBit(),getParity()); 
      setFlowControlForButtonSignals();
   } 
   public void setFlowControlForButtonSignals(){
     serialComm.ports[SelectedPortIndex].setFlowControl(SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED);
     serialComm.ports[SelectedPortIndex].setFlowControl(SerialPort.FLOW_CONTROL_XONXOFF_OUT_ENABLED);
   }
   public void  openComPortIfClosed(){
    if(serialComm.ports[SelectedPortIndex].isOpen()==false){
       ComportParams.portOpenStatus = serialComm.ports[SelectedPortIndex].openPort();
       System.out.println(ComportParams.PORTDESCRIPTION+" is now opened");
   }
   }
   public void closePortAfterUsage(){
       ComportParams.portClosedStatus=serialComm.ports[SelectedPortIndex].closePort();
       System.out.println(ComportParams.PORTDESCRIPTION+" is now closed");
   }
   public SerialPort getSelectedPort(){
      return serialComm.ports[SelectedPortIndex]; 
   }  
   public void queryTheStudentRecordDBbyID_AndProcessResult(){
       DaoForJavaDB.getInstance().dataQueryFromTable_studentrecordById();//get data from the database     
       if(DaoForJavaDB.recordFound==false){
            nameField.setText("");
            levelField.setText("");
            deptField.setText("");
            facualtyField.setText("");
            matricField.setText("");            
            studentPhoto.setImage(new Image(getClass().getResource("/images/avatar.jpg").toString()));           
           JOptionPane.showMessageDialog(null,"No record found","No record",JOptionPane.INFORMATION_MESSAGE);
       }else{
       //process result for nameField,levelField,deptField,facualtyField,matricField
            nameField.setText(DataServicePOJO.getInstance().getdName());
            levelField.setText(DataServicePOJO.getInstance().getdLevel());
            deptField.setText(DataServicePOJO.getInstance().getdDepartment());
            facualtyField.setText(DataServicePOJO.getInstance().getdFacaulty());
            matricField.setText(DataServicePOJO.getInstance().getdMatric());
            try{
                System.out.println("Alert!!!!!  Image path retried from DB is "+DataServicePOJO.getInstance().getdImagePath());
                studentPhoto.setPreserveRatio(true); 
                studentPhoto.setImage(new Image(DataServicePOJO.getInstance().getdImagePath(),200,150,true,true));
                                    
            }catch(Exception ex){
                System.out.println("Error constructing image");
                Logger.getLogger(VerificationUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
       }    
   }
    private void queryTheStudentRecordDBbyMatric_AndProcessResult() {
       DaoForJavaDB.getInstance().dataQueryFromTable_studentrecordByMatric();//get data from the database     
       if(DaoForJavaDB.recordFound==false){
            nameField.setText("");
            levelField.setText("");
            deptField.setText("");
            facualtyField.setText("");
            matricField.setText("");         
            studentPhoto.setImage(new Image(getClass().getResource("/images/avatar.jpg").toString()));           
           JOptionPane.showMessageDialog(null,"No record found","No record",JOptionPane.INFORMATION_MESSAGE);
       }else{
       //process result for nameField,levelField,deptField,facualtyField,matricField
            nameField.setText(DataServicePOJO.getInstance().getdName());
            levelField.setText(DataServicePOJO.getInstance().getdLevel());
            deptField.setText(DataServicePOJO.getInstance().getdDepartment());
            facualtyField.setText(DataServicePOJO.getInstance().getdFacaulty());
            matricField.setText(DataServicePOJO.getInstance().getdMatric());
            try{
                System.out.println("Alert!!!!!  Image path retried from DB is "+DataServicePOJO.getInstance().getdImagePath());
                studentPhoto.setPreserveRatio(true); 
                studentPhoto.setImage(new Image(DataServicePOJO.getInstance().getdImagePath(),200,150,true,true)); 
                
            }catch(Exception ex){
                System.out.println("Error constructing image");
                Logger.getLogger(VerificationUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
       } 
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialization done for Verification UI");
        
    }        
}
