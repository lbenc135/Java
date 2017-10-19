import javax.swing.*;

public class SwingInput {
	public static void main(String[] args) {
		String temp; // Temporary storage for input.
		temp = JOptionPane.showInputDialog(null, "First number");
		float a = Integer.parseInt(temp); // string to int
		temp = JOptionPane.showInputDialog(null, "Second number");
		float b = Integer.parseInt(temp);
		temp = JOptionPane.showInputDialog(null, "Third number");
		float c = Integer.parseInt(temp);
		JOptionPane.showMessageDialog(null, "Average is " + (a+b+c)/3);
	}
}