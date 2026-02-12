/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.electronicmedicalrecords;

public class Main {
    public static HealthcareSystem system;

    public static void main(String[] args) {
        system = new HealthcareSystem();
        system.loadData();
        
        new LoginFrame().setVisible(true);
        
        //hello github
    }
}

