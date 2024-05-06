package atm.simulator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

/**
 *
 * @author Manish
 */
public class FastCash extends JFrame implements ActionListener{
    JButton hundred, fivehundred, thousand, twothousand, fivethousand, tenthousand, back;
    String pinnumber;
    FastCash(String pinnumber){
        this.pinnumber = pinnumber;
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900,900,Image.SCALE_DEFAULT);
        // COnverting i2 back to image icon
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);
        
        JLabel text = new JLabel("Select withdrawl amount");
        text.setBounds(215, 300, 700, 35);
        text.setForeground(Color.WHITE); 
        text.setFont(new Font("System", Font.BOLD,16));
        image.add(text); // Generally when you write add(text)the text will get added on frame but here we have added image on our frame and we want text to be added on the image hence we have used image.add(text)
        
        // deposit
        hundred = new JButton("Rs 100");
        hundred.setBounds(170,415,150,30);
        hundred.addActionListener(this);
        image.add(hundred);
        
        // withdrawl
        fivehundred = new JButton("Rs 500");
        fivehundred.setBounds(355,415,150,30);
        fivehundred.addActionListener(this);
        image.add(fivehundred);
        
        // fast cash
        thousand = new JButton("Rs 1000");
        thousand.setBounds(170,450,150,30);
        thousand.addActionListener(this);
        image.add(thousand);
        
        // ministatement
        twothousand = new JButton("Rs 2000");
        twothousand.setBounds(355,450,150,30);
        twothousand.addActionListener(this);
        image.add(twothousand);
        
        // pinchange
        fivethousand = new JButton("Rs 5000");
        fivethousand.setBounds(170,485,150,30);
        fivethousand.addActionListener(this);
        image.add(fivethousand);

        tenthousand = new JButton("Rs 10000");
        tenthousand.setBounds(355,485,150,30);
        tenthousand.addActionListener(this);
        image.add(tenthousand);

        back = new JButton("Back");
        back.setBounds(355,520,150,30);
        back.addActionListener(this);
        image.add(back);
        
        setSize(900,900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == back){
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        }
        else{
            String amount = ((JButton)ae.getSource()).getText().substring(3); // Rs 500 we dont want Rs string hence we will be using substring
            Conn c = new Conn();
            try{
                ResultSet rs = c.s.executeQuery("Select * from bank where pin = '"+pinnumber+"'");
                int balance = 0;
                while(rs.next()){
                    if (rs.getString("type").equals("Deposit")){
                        balance += Integer.parseInt(rs.getString("amount"));
                    }
                    else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }
                if (ae.getSource()!=back && balance < Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null, "Insufficent Balance");
                    return;
                }
                
                Date date = new Date();
                String query = "Insert into bank values('"+pinnumber+"','"+date+"','Withdrawl', '"+amount+"')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Rs "+amount+" Debited successfully");
                
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);                
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
    public static void main(String[] args){
        new FastCash("");
    }
}
