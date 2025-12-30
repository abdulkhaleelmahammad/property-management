package com.mycompany.property_management.controller;

public class CalculatorMain {

    public static void main(String[] args){
        CalculatorController cc = new CalculatorController();
        Double result = cc.substract(4.5,4.5);
        System.out.println(result);
    }


}
