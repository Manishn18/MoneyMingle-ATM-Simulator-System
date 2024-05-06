package atm.simulator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

/**
 *
 * @author Manish Nawadkar
 */
public class Login extends JFrame implements ActionListener{
//    JLabel l1,l2,l3;
    JTextField cardTextField;
    JPasswordField pinTextField;
    JButton login,clear,signup;
  
    Login(){
        setTitle("MoneyMingle - ATM Simulator System");
        setLayout(null);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(70, 10, 100, 100);
        add(label);        
        JLabel text = new JLabel("Welcome to ATM");
        text.setFont(new Font("Osward", Font.BOLD, 38));
        text.setBounds(200,40,400,40);
        add(text);
        
        JLabel cardno = new JLabel("Card no:");
        cardno.setFont(new Font("Raleway", Font.BOLD, 28));
        cardno.setBounds(120,150,150,30);
        add(cardno);

        cardTextField = new JTextField();
        cardTextField.setBounds(300, 150, 230, 40);
        cardTextField.setFont(new Font("Arial", Font.BOLD, 18)); // For font that user will enter in the textfield created 
        add(cardTextField);
        
        JLabel pin = new JLabel("Pin:");
        pin.setFont(new Font("Raleway", Font.BOLD, 28));
        pin.setBounds(120,220,250,30);
        add(pin);

        pinTextField = new JPasswordField();
        pinTextField.setBounds(300, 220, 230, 40);
        pinTextField.setFont(new Font("Arial", Font.BOLD, 18)); // For font that user will enter in the textfield created 
        add(cardTextField);
        add(pinTextField);
        
        login = new JButton("SIGN IN");
        login.setBounds(300,300,100,30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);
        
        clear = new JButton("CLEAR");
        clear.setBounds(430,300,100,30);
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE);
        clear.addActionListener(this);
        add(clear);

        signup = new JButton("SIGN UP");
        signup.setBounds(300,350,230,30);
        signup.setBackground(Color.BLACK);
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);         
              
        getContentPane().setBackground(Color.WHITE);
        setSize(800,480); // sets length and breadth
        setLocation(550,200); // By default your frame will open at top left corner and if you want to set a specific location at which you want your frame to be opened then setLocation(length, breadth) can be used 
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource()== clear){
            cardTextField.setText("");
            pinTextField.setText("");
        }
        else if (ae.getSource() == login){
            Conn conn = new Conn();
            String cardnumber = cardTextField.getText();
            String pinnumber = pinTextField.getText(); // pinnumber is a password Textfield and with that getText() cannot be used hence the strikethrough
            String query = "SELECT * FROM login WHERE card_number = '" + cardnumber + "' AND pin = '" + pinnumber + "'";
            try{
                ResultSet rs = conn.s.executeQuery(query);
                if (rs.next()){
                    // if the data is matched do the followng - 
                    setVisible(false); // close current frame
                    new Transactions(pinnumber).setVisible(true); // open the Transactions frame
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or Pin");
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        else if (ae.getSource() == signup){
            setVisible(false); // closes the current frame which is login Frame when clicked on the sign up button 
            new SignupOne().setVisible(true); // Immediately opens the SignupOne frame 
        }
    }
    public static void main(String[] args){
        new Login();
    }
}