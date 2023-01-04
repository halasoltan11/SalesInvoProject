/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesInvoiceMain;

import ControllerPackage.MainController;
import ModelPackage.FileOperations;
import ModelPackage.HeaderInvoice;

import javax.swing.plaf.synth.SynthDesktopIconUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author halas
 */
public class Main {

    public  static void main(String[] args) throws IOException {
        MainController mainController = new MainController();
        mainController.handleAction();

    }
}
