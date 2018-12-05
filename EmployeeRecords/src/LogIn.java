/// ****Authors: Wilfredo Hernandez Escobar & Thomas Gutierrez***///

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class LogIn {

	private JFrame frame;
	private JTextField EmpID;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn window = new LogIn();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 */
	Connection conn =null;
	
	public LogIn() {
		initialize();
		conn = sqlConnector.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 481, 324);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("EmployeeID");
		lblNewLabel.setBounds(63, 50, 117, 46);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(63, 117, 117, 40);
		frame.getContentPane().add(lblNewLabel_1);
		
		EmpID = new JTextField();
		EmpID.setBounds(190, 59, 126, 32);
		frame.getContentPane().add(EmpID);
		EmpID.setColumns(10);
		
		JButton btnLogIn = new JButton("LOGIN");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					String query = "select * from EMPDB where emp_id = ? and password = ? ";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, EmpID.getText());
					pst.setString(2, passwordField.getText());
					
					ResultSet reslt = pst.executeQuery();
					int count = 0;
					while(reslt.next()) {
						count = count+1;
					}
					if(count ==1)
					{
						//JOptionPane.showMessageDialog(null, "Employee ID and password is correct. ");
						frame.dispose();
						EmployeeInfo EmpInfo = new EmployeeInfo();
						EmpInfo.setVisible(true);
					}else if(count > 1)
					{
						JOptionPane.showMessageDialog(null, "Employee ID and password is duplicate. ");
					}else {
						JOptionPane.showMessageDialog(null, "Try Again.");
					}
						reslt.close();
						pst.close();
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				}
				
				
				
		}});
		btnLogIn.setBounds(161, 178, 155, 46);
		frame.getContentPane().add(btnLogIn);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(190, 117, 126, 30);
		frame.getContentPane().add(passwordField);
	}
}
