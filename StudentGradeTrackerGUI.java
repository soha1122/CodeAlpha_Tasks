import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Student {
    int roll;
    String name;
    double grade;

    Student(int roll, String name, double grade) {
        this.roll = roll;
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTrackerGUI {

    static ArrayList<Student> students = new ArrayList<>();
    static JPanel studentGridPanel;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Student Grade Tracker");
        frame.setSize(750,650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10,10));

        // ===== TITLE =====
        JLabel title = new JLabel("STUDENT GRADE TRACKER", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        frame.add(title, BorderLayout.NORTH);

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel, BorderLayout.CENTER);

        // ===== INPUT PANEL =====
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Student Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel rollLabel = new JLabel("Student Roll Number:");
        JTextField rollField = new JTextField(15);
        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField(15);
        JLabel gradeLabel = new JLabel("Student Grade:");
        JTextField gradeField = new JTextField(15);

        JButton confirmBtn = new JButton("Confirm");

        gbc.gridx=0; gbc.gridy=0; inputPanel.add(rollLabel,gbc);
        gbc.gridx=1; inputPanel.add(rollField,gbc);

        gbc.gridx=0; gbc.gridy=1; inputPanel.add(nameLabel,gbc);
        gbc.gridx=1; inputPanel.add(nameField,gbc);

        gbc.gridx=0; gbc.gridy=2; inputPanel.add(gradeLabel,gbc);
        gbc.gridx=1; inputPanel.add(gradeField,gbc);

        // ===== ONLY CONFIRM BUTTON (CENTER) =====
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(confirmBtn);
        inputPanel.add(btnPanel,gbc);

        mainPanel.add(inputPanel);

        // ===== STUDENT DETAILS PANEL =====
        studentGridPanel = new JPanel();
        studentGridPanel.setLayout(new BoxLayout(studentGridPanel, BoxLayout.Y_AXIS));
        studentGridPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        JScrollPane scrollPane = new JScrollPane(studentGridPanel);
        scrollPane.setPreferredSize(new Dimension(700,250));
        mainPanel.add(scrollPane);

        // ===== RESULT PANEL =====
        JPanel resultPanel = new JPanel(new GridLayout(3,1));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Results"));

        JLabel avgLabel = new JLabel("Average: ");
        JLabel highLabel = new JLabel("Highest: ");
        JLabel lowLabel = new JLabel("Lowest: ");

        resultPanel.add(avgLabel);
        resultPanel.add(highLabel);
        resultPanel.add(lowLabel);

        mainPanel.add(resultPanel);

        // ===== CONFIRM BUTTON =====
        confirmBtn.addActionListener(e -> {
            try {
                if(rollField.getText().isEmpty() || nameField.getText().isEmpty() || gradeField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(frame,"Please enter all details");
                    return;
                }

                int roll = Integer.parseInt(rollField.getText());
                String name = nameField.getText();
                double grade = Double.parseDouble(gradeField.getText());

                students.add(new Student(roll,name,grade));

                JPanel rowPanel = new JPanel(new GridLayout(1,3,5,5));
                rowPanel.setMaximumSize(new Dimension(700,40));
                rowPanel.setPreferredSize(new Dimension(700,40));

                rowPanel.add(createCell("Roll: " + roll));
                rowPanel.add(createCell("Name: " + name));
                rowPanel.add(createCell("Grade: " + grade));

                studentGridPanel.add(rowPanel);

                studentGridPanel.revalidate();
                studentGridPanel.repaint();

                rollField.setText("");
                nameField.setText("");
                gradeField.setText("");

                double total=0;
                double high=students.get(0).grade;
                double low=students.get(0).grade;

                for(Student s:students){
                    total+=s.grade;
                    if(s.grade>high) high=s.grade;
                    if(s.grade<low) low=s.grade;
                }

                avgLabel.setText("Average: "+(total/students.size()));
                highLabel.setText("Highest: "+high);
                lowLabel.setText("Lowest: "+low);

            } catch(Exception ex){
                JOptionPane.showMessageDialog(frame,"Invalid input");
            }
        });

        frame.setVisible(true);
    }

    static JPanel createCell(String text){
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel lbl = new JLabel(text, JLabel.CENTER);
        p.add(lbl);
        return p;
    }
}