/*
version 1.1
This application is owned and
Developed by Hamzat Habibllahi Adewale 2018
*/
package com.javafxwithmaven.studentidentification.ComportAcess;
import com.fazecast.jSerialComm.*;
import javax.swing.JOptionPane;

public class serialComm extends ComportParams{
  //static variables set for obtaing available real or virtual  comports on the system 
public  static int numOfComAvailable = SerialPort.getCommPorts().length; 
public  static SerialPort[] ports = new SerialPort[numOfComAvailable]; 
public static String[] portsName = new String[numOfComAvailable];
public  int x = 0;
public  static byte[]readBuffer; //this byte[] array size shall be set to the lenght of byte available to read. and read byte shall be stored in it.
public  static int readSucessful;
public  static boolean readSucessfulFlag=false;

public serialComm(){
   //creates object instance of SerialPort class and simultaniously fettchhing the available comPorts as the created objects
            init();         
}
public void init(){
        try{
                for(SerialPort COM: SerialPort.getCommPorts()){
                    System.out.println("I am now inside the forloop of the serialComm() constructor");
                    if(x < numOfComAvailable){
                        System.out.println(">>seems port is available, let me try and fetch the port and the there port description");
                        ports[x] = COM;
                        portsName[x] = ports[x].getDescriptivePortName();
                        System.out.println(ports[x]);
                        System.out.println(portsName[x]);
                        x+=1;
                    }//end of comPort fettching  
            }            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getCause(), "COM port error", JOptionPane.ERROR_MESSAGE);
        }
} 
}

