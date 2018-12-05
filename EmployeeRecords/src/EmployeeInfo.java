/// ****Authors: Wilfredo Hernandez Escobar & Thomas Gutierrez***///

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EmployeeInfo extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JComboBox comboBoxSelector;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeInfo frame = new EmployeeInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection = null;
	private JTextField EMP_ID;
	private JTextField FIRSTNAME;
	private JTextField LASTNAME;
	private JTextField DEPARTMENT;
	private JTextField SALARY;
	private JTextField searcher;
	
	
	/**
	 * Create the frame.
	 */
	public EmployeeInfo() {
		connection = sqlConnector.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select EMP_ID,FIRSTNAME,LASTNAME,DEPARTMENT,SALARY from EMPDB";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			}
		});
		loadButton.setBounds(432, 11, 89, 23);
		contentPane.add(loadButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(214, 45, 322, 319);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("EMP ID");
		lblNewLabel.setBounds(21, 63, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("FIRST NAME");
		lblNewLabel_1.setBounds(21, 88, 67, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("LAST NAME");
		lblNewLabel_2.setBounds(21, 114, 67, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("DEPARTMENT");
		lblNewLabel_3.setBounds(21, 139, 67, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblSalary = new JLabel("SALARY");
		lblSalary.setBounds(21, 164, 46, 14);
		contentPane.add(lblSalary);
		
		EMP_ID = new JTextField();
		EMP_ID.setBounds(94, 60, 86, 20);
		contentPane.add(EMP_ID);
		EMP_ID.setColumns(10);
		
		FIRSTNAME = new JTextField();
		FIRSTNAME.setBounds(94, 85, 86, 20);
		contentPane.add(FIRSTNAME);
		FIRSTNAME.setColumns(10);
		
		LASTNAME = new JTextField();
		LASTNAME.setBounds(94, 111, 86, 20);
		contentPane.add(LASTNAME);
		LASTNAME.setColumns(10);
		
		DEPARTMENT = new JTextField();
		DEPARTMENT.setBounds(94, 136, 86, 20);
		contentPane.add(DEPARTMENT);
		DEPARTMENT.setColumns(10);
		
		SALARY = new JTextField();
		SALARY.setBounds(94, 164, 86, 20);
		contentPane.add(SALARY);
		SALARY.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//HANDLES EVENTS ONCE ADD BUTTON IS PUSHED
				try {
					String query = "insert into EMPDB (EMP_ID,FIRSTNAME,LASTNAME,DEPARTMENT,SALARY) values(?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, EMP_ID.getText() );
					pst.setString(2, FIRSTNAME.getText() );
					pst.setString(3, LASTNAME.getText() );
					pst.setString(4, DEPARTMENT.getText() );
					pst.setString(5, SALARY.getText() );
					
					 pst.execute();
					
					JOptionPane.showMessageDialog(null, "New Employee Added.");
					
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(91, 195, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "delete from EMPDB where EMP_ID=' "+EMP_ID.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					
					 pst.execute();
					
					JOptionPane.showMessageDialog(null, "Employee Removed.");
					
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(91, 229, 89, 23);
		contentPane.add(btnDelete);
		
		searcher = new JTextField();
		searcher.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try
				{
					String selection = (String)comboBoxSelector.getSelectedItem();
					String query = "select * from EMPDB where "+selection+" = ?";
					System.out.println(query);
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1,searcher.getText());
					ResultSet rs = pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					while(rs.next())
					{
						
					}
					pst.close();
				}catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		searcher.setBounds(336, 12, 86, 20);
		contentPane.add(searcher);
		searcher.setColumns(10);
		
		JLabel lblSearch = new JLabel("Search By :");
		lblSearch.setBounds(201, 11, 67, 23);
		contentPane.add(lblSearch);
		
		comboBoxSelector = new JComboBox();
		comboBoxSelector.setModel(new DefaultComboBoxModel(new String[] {"EMP_ID", "FIRSTNAME", "LASTNAME", "DEPARTMENT"}));
		comboBoxSelector.setBounds(257, 12, 69, 20);
		contentPane.add(comboBoxSelector);
		
		
	}
}
