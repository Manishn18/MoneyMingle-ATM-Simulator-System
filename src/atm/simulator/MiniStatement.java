package atm.simulator;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
/**
 *
 * @author Manish
 */
public class MiniStatement extends JFrame{
    MiniStatement(String pinnumber){
        setTitle("Mini Statement");
        
        setLayout(null);
        
        JLabel mini = new JLabel();
        add(mini);
        
        JLabel bank = new JLabel("Bank of India");
        bank.setBounds(150, 20, 100, 20);
        add(bank);
        
        JLabel card = new JLabel();
        card.setBounds(20, 80, 300, 20);
        add(card);
        
        JLabel balance = new JLabel();
        balance.setBounds(20, 400, 300, 20);
        add(balance);
        
        try
        {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("Select * from login where pin = '"+pinnumber+"'");
            while(rs.next()){
                card.setText("Card Number: "+rs.getString("card_number").substring(0,4) + "-XXXX-XXXX-" + rs.getString("card_number").substring(12)); // Inserting values starting 4 digits and ending 4 digits can be seen on mini statement   
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        try 
        {
            Conn conn = new Conn();
            int bal = 0;
            ResultSet rs = conn.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pinnumber + "'");
            String statementText = "<html>";
            while (rs.next()) {
                statementText += rs.getString("date") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                                 rs.getString("type") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                                 rs.getString("amount") + "<br><br>";
                
                if (rs.getString("type").equals("Deposit")){
                    bal += Integer.parseInt(rs.getString("amount"));
                }
                else {
                    bal -= Integer.parseInt(rs.getString("amount"));
                }
            }
            balance.setText("Your current account balance is Rs "+bal);
            statementText += "</html>";
            mini.setText(statementText);
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }

        mini.setBounds(20, 140, 400, 200);
        setSize(400,600);
        setLocation(20,20); // 20 from left margin and 20 from top margin
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
        
    }
    
    public static void main(String[] args){
        new MiniStatement("");
    }
}
