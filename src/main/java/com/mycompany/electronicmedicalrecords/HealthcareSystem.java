/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.electronicmedicalrecords;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

class Node {
    String patientID, patientName;
}

class MasterNode extends Node {
    String sex, bday, contactNum, address, bplace;
    MasterNode next;

    public MasterNode(String patientID, String name, String bday, String sex, String contactNum, String address, String bplace) {
        this.patientID = patientID;
        this.patientName = name;
        this.bday = bday;
        this.sex = sex;
        this.contactNum = contactNum;
        this.address = address;
        this.bplace = bplace;
        this.next = null;
    }
}

class TransactionNode extends Node {
    double serviceFee, medicineFee, totalFee;
    String visitID,date, symptoms, diagnosis, prescriptions, doctor;
    TransactionNode next;

    public TransactionNode(String visitID, String patientID, String patientName, double serviceFee, double medicineFee, double totalFee, String date, String symptoms, String diagnosis, String prescription, String doctor) {
        this.visitID = visitID;
        this.patientID = patientID;
        this.patientName = patientName;
        this.serviceFee = serviceFee;
        this.medicineFee = medicineFee;
        this.totalFee = totalFee;
        this.date = date;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.prescriptions = prescription;
        this.doctor = doctor;
        this.next = null;
    }
}

abstract class FileLoader {
    protected String filename;

    public FileLoader(String filename) {
        this.filename = filename;
    }

    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void processLine(String line);
}

class MasterLoader extends FileLoader {
    private HealthcareSystem system;

    public MasterLoader(String filename, HealthcareSystem system) {
        super(filename);
        this.system = system;
    }

    @Override
    protected void processLine(String line) {
        String[] data = line.split("\\|");

        system.addMaster(
            data[0].trim(),
            data[1].trim(),
            data[2].trim(),
            data[3].trim(),
            data[4].trim(),
            data[5].trim(),
            data[6].trim()
        );
    }
}

class TransactionLoader extends FileLoader {
    private HealthcareSystem system;

    public TransactionLoader(String filename, HealthcareSystem system) {
        super(filename);
        this.system = system;
    }

    @Override
    protected void processLine(String line) {
        String[] data = line.split("\\|");

        system.addTransaction(
            data[0].trim(),
            data[1].trim(),
            data[2].trim(),
            Double.parseDouble(data[8].trim()),
            Double.parseDouble(data[9].trim()),
            Double.parseDouble(data[10].trim()),
            data[3].trim(),
            data[4].trim(),
            data[5].trim(),
            data[6].trim(),
            data[7].trim()
        );
    }
}

public class HealthcareSystem {
    MasterNode headMaster;
    TransactionNode headTransaction;
    
    public void printMaster() { //pang debug
        MasterNode p = headMaster;
        
        if (p == null) {
            System.out.println("List Empty");
            return;
        }
        
        System.out.println("Master: ");
        while (p != null) {
            System.out.println(p.patientName);
            p = p.next;
        }
    }
    
    public void printTransaction() { //pang debug
        TransactionNode p = headTransaction;
        
        if (p == null) {
            System.out.println("List Empty");
            return;
        }
        
        System.out.println("\nTransaction: ");
        while (p != null) {
            System.out.println(p.visitID + " - " + p.patientName);
            p = p.next;
        }
    }
    
    public int countVisits() { //for generating visitid (pwede rin pambilang ng total visits for reports)
        TransactionNode p = headTransaction;
        int count = 0;
        
        while (p != null) {
            count++;
            p = p.next;
        }
        return count;
    }
    
    public int countPatients() { //for generating patientid (pwede rin pambilang ng total patients for reports)
        MasterNode p = headMaster;
        int count = 0;
        
        while (p != null) {
            count++;
            p = p.next;
        }
        return count;
    }
    
    public String generatePatientID() {
        int count = countPatients();
        return String.format("PID%d", ++count);
    }
    
     public void SavePatientFile(MasterNode newPatient) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Acads\\scar\\2nd year 2nd sem\\Programming Language\\ElectronicMedicalRecords\\src\\main\\java\\com\\mycompany\\electronicmedicalrecords\\master.txt", true))) {
            writer.write(newPatient.patientID + "|" + 
                         newPatient.patientName + "|" + 
                         newPatient.bday + "|" + 
                         newPatient.sex + "|" + 
                         newPatient.contactNum + "|" + 
                         newPatient.address + "|" + 
                         newPatient.bplace);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadData() {
        FileLoader masterLoader = new MasterLoader(
            "C:\\Acads\\scar\\2nd year 2nd sem\\Programming Language\\ElectronicMedicalRecords\\src\\main\\java\\com\\mycompany\\electronicmedicalrecords\\master.txt",
            this
        );

        FileLoader transactionLoader = new TransactionLoader(
            "C:\\Acads\\scar\\2nd year 2nd sem\\Programming Language\\ElectronicMedicalRecords\\src\\main\\java\\com\\mycompany\\electronicmedicalrecords\\transaction.txt",
            this
        );

        masterLoader.load();
        transactionLoader.load();
    }


    public void addMaster(String patientID, String name, String sex, String bday, String contactNum, String address, String bplace) {
        MasterNode newNode = new MasterNode(patientID, name, sex, bday, contactNum, address, bplace);
        MasterNode p = headMaster;

        if (headMaster == null) {
            headMaster = newNode;
            return;
        }

        while (p.next != null && !newNode.patientID.equals(p.patientID)) {
            p = p.next;
        }

        if (p.next == null) {
            p.next = newNode;
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Patient already exist",
                    "Error",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void addTransaction(String visitID, String patientID, String patientName, double serviceFee, double medicineFee, double totalFee, String date, String symptoms, String diagnosis, String prescription, String doctor) {
        TransactionNode newNode = new TransactionNode(visitID, patientID, patientName, serviceFee, medicineFee, totalFee, date, symptoms, diagnosis, prescription, doctor);
        TransactionNode p = headTransaction;

        if (headTransaction == null) {
            headTransaction = newNode;
            return;
        }

        while (p.next != null) {
            p = p.next;
        }

        if (p.next == null) {
            p.next = newNode;
        }
    }
    
}
