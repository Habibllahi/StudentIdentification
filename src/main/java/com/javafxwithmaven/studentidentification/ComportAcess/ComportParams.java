/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafxwithmaven.studentidentification.ComportAcess;

/**
 *
 * @author Habeeb
 */
public class ComportParams {
    public  static int selectedBaudRate=9600,selectedBitPerWord=8,SelectedPortIndex,selectedParity=0,selectedStopBit=0; 
    public static boolean ChangedFlag=false;
    public static boolean portOpenStatus;
    public static boolean portClosedStatus; 
    public static String PORTDESCRIPTION,sendViaComPortText;
    public static boolean dontPorpulate = false, couldNotFindDesiredPort = false; 
    public static String CarriageType="In Line";
}
