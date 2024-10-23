import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarksCalculator extends JFrame {
    private JTextField[] marksFields;
    private JButton calculateButton;
    private JTextArea resultArea;

    public MarksCalculator() {
        setTitle("Marks Calculator");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        
        JPanel inputPanel = new JPanel(new GridLayout(0, 1));
        marksFields = new JTextField[5];
        for (int i = 0; i < marksFields.length; i++) {
            inputPanel.add(new JLabel("Marks in Subject " + (i + 1) + ":"));
            marksFields[i] = new JTextField(5);
            inputPanel.add(marksFields[i]);
        }
        add(inputPanel);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateAction());
        add(calculateButton);

        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        setVisible(true);
    }

    private class CalculateAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int totalMarks = 0;
                for (JTextField field : marksFields) {
                    totalMarks += Integer.parseInt(field.getText());
                }
                double averagePercentage = totalMarks / (double) marksFields.length;
                String grade = calculateGrade(averagePercentage);

                resultArea.setText("Total Marks: " + totalMarks +
                        "\nAverage Percentage: " + averagePercentage +
                        "\nGrade: " + grade);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MarksCalculator.this,
                        "Please enter valid numeric values for marks.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return "A+";
        } else if (averagePercentage >= 80) {
            return "A";
        } else if (averagePercentage >= 70) {
            return "B";
        } else if (averagePercentage >= 60) {
            return "C";
        } else if (averagePercentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MarksCalculator::new);
    }
}
