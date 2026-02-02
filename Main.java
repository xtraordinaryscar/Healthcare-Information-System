//read the file, extract each line and put it in singly linked list, search the id if it exist, if it exist: display if not: error
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Node {
    int pid;
    String name, sex, bday, contactNum, address, bplace;
    Node next;

    public Node(int pid, String name, String sex, String bday, String contactNum, String address, String bplace) {
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

class LinkedList {
    Node head;

    public void add(int pid, String name, String sex, String bday, String contactNum, String address, String bplace) {
        Node newNode = new Node(pid, name, sex, bday, contactNum, address, bplace);
        Node p = head;

        if (head == null) {
            head = newNode;
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

    public void search(String name) {
        Node p = head;

        while (p != null) {
            if  (p.name.equalsIgnoreCase(name)) {
                String patientInfo = "Patient ID: " + p.pid + "\n" + "Name: " + p.name + "\n" + "Sex: " + p.sex + "\n" + "Birthday: " + p.bday + "\n" + "Contact Number: " + p.contactNum + "\n" + "Address: " + p.address + "\n" + "Birthplace: " + p.bplace;

                JOptionPane.showMessageDialog(null, patientInfo, "Patient Details", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            p = p.next;
        }

        if (p == null) {
            JOptionPane.showMessageDialog(null, "Patient not on the list");
        }
    }

    public void retrieve() {
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

                add(pid, name, sex, bday, contactNum, address, bplace);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.retrieve();

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

        JButton button = new JButton("Search");
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(0, 0, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(e -> {
            String patientName = field.getText();
            list.search(patientName);
        });

        panel.add(Box.createVerticalGlue());
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(button);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);
    }
}