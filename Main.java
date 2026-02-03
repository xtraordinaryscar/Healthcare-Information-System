//read the file, extract each line and put it in singly linked list, search the id if it exist, if it exist: display if not: error
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class MasterNode {
    int pid;
    String name, sex, bday, contactNum, address, bplace;
    MasterNode next;

    public MasterNode(int pid, String name, String sex, String bday, String contactNum, String address, String bplace) {
        this.pid = pid;
        this.name = name;
        this.sex = sex;
        this.bday = bday;
        this.contactNum = contactNum;
        this.address = address;
        this.bplace = bplace;
        this.next = null;
    }
}

class TransactionNode {
    int vid, pid, sf, mf, tf;
    String date, symptoms, diagnosis, prescriptions, doctor;
    TransactionNode next;

    TransactionNode(int vid, int pid, int sf, int mf, int tf, String date, String symptoms, String diagnosis, String prescription, String doctor) {
        this.vid = vid;
        this.pid = pid;
        this.sf = sf;
        this.mf = mf;
        this.tf = tf;
        this.date = date;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.prescriptions = prescription;
        this.doctor = doctor;
        this.next = null;
    }
}

class Healthcare {
    MasterNode headMaster;
    TransactionNode headTransaction;

    public void retrieveMaster() {
        String filename = "C:/Acads/scar/2nd year 2nd sem/Programming Language/Healthcare Information System/master.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int pid = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                String sex = data[2].trim();
                String bday = data[3].trim();
                String contactNum = data[4].trim();
                String address = data[5].trim();
                String bplace = data[6].trim();

                addMaster(pid, name, sex, bday, contactNum, address, bplace);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieveTransaction() {
        String filename = "C:/Acads/scar/2nd year 2nd sem/Programming Language/Healthcare Information System/transaction.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int vid = Integer.parseInt(data[0].trim());
                int pid = Integer.parseInt(data[1].trim());
                String date = data[2].trim();
                String symptoms = data[3].trim();
                String diagnosis = data[4].trim();
                String prescription = data[5].trim();
                String doctor = data[6].trim();
                int sf = Integer.parseInt(data[7].trim());
                int mf = Integer.parseInt(data[8].trim());
                int tf = Integer.parseInt(data[9].trim());


                addTransaction(vid, pid, sf, mf, tf, date, symptoms, diagnosis, prescription, doctor);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMaster(int pid, String name, String sex, String bday, String contactNum, String address, String bplace) {
        MasterNode newNode = new MasterNode(pid, name, sex, bday, contactNum, address, bplace);
        MasterNode p = headMaster;

        if (headMaster == null) {
            headMaster = newNode;
            return;
        }

        while (p.next != null && newNode.pid != p.pid) {
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

    public void addTransaction(int vid, int pid, int sf, int mf, int tf, String date, String symptoms, String diagnosis, String prescription, String doctor) {
        TransactionNode newNode = new TransactionNode(vid, pid, sf, mf, tf, date, symptoms, diagnosis, prescription, doctor);
        TransactionNode p = headTransaction;

        if (headTransaction == null) {
            headTransaction = newNode;
            return;
        }

        while (p.next != null && newNode.pid != p.pid) {
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

    public void login() {
        JFrame frame = new JFrame("Login");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245));

        JTextField username = new JTextField();
        username.setMaximumSize(new Dimension(200, 25));
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        username.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField pass = new JPasswordField();
        pass.setMaximumSize(new Dimension(200, 25));
        pass.setFont(new Font("Arial", Font.PLAIN, 16));
        pass.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = new JButton("Login");
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(0, 0, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(username);
        panel.add(pass);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(button);
        panel.add(Box.createVerticalGlue());

        button.addActionListener(e -> {
            String usn = username.getText();
            String ps = pass.getText();

            if (usn.isEmpty() || ps.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter username and password");
                return;
            }

            if (!usn.equals("admin") || !ps.equals("1234")) {
                JOptionPane.showMessageDialog(null, "Wrong username or password");
                return;
            }

            patientHistory();
            frame.dispose();
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public void patientHistory() {
        JFrame frame = new JFrame("Patient History");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245));

        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(200, 25));
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = new JButton("View");
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(0, 0, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(e -> {
            String patientName = field.getText();

            if (patientName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please input patient name");
                return;
            }
            search(patientName);
        });

        panel.add(Box.createVerticalGlue());
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(button);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }

    public void search(String name) {
        MasterNode p = headMaster;

        while (p != null) {
            if  (p.name.equalsIgnoreCase(name)) {
                String patientInfo = "Patient ID: " + p.pid + "\n" + "Name: " + p.name + "\n" + "Sex: " + p.sex + "\n" + "Birthday: " + p.bday + "\n" + "Contact Number: " + p.contactNum + "\n" + "Address: " + p.address + "\n" + "Birthplace: " + p.bplace;

                JOptionPane.showMessageDialog(null, patientInfo, "Patient Details", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            p = p.next;
        }

        if (p == null) {
            JOptionPane.showMessageDialog(null, "Patient not in the list");
            return;
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Healthcare x = new Healthcare();
        x.retrieveMaster();
        x.retrieveTransaction();
        
        x.login();
    }
}