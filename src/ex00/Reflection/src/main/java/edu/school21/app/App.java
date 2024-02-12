package edu.school21.app;

import edu.school21.logic.Logic;


public class App {
    public static void main(String[] args) {
        try {
            Logic.printAllClasses();
            printDelimiter();
            Logic.scanClass();
            printDelimiter();
            Logic.printFieldsAndMethods();
            printDelimiter();
            try {
                Logic.createObj();
            } catch (NullPointerException ex) {
                System.out.println(ex.getMessage());
            }
            printDelimiter();
            try {
                Logic.changeField();
            } catch (NullPointerException ex) {
                System.out.println(ex.getMessage());
            }
            printDelimiter();
            try {
                Logic.runMethod();
            } catch (NullPointerException ex) {
                System.out.println(ex.getMessage());
            }

            printDelimiter();
        } catch (Exception ex) {
            Logic.closeScanner();
            System.err.println("Error: " + ex.getMessage());
        }
    }

    private static void printDelimiter() {
        System.out.println("---------------------");
    }
}
