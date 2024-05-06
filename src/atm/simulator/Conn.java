
package atm.simulator;

import java.sql.*;

/**
 *
 * @author Manish
 */
public class Conn {
    Connection c;
    Statement s;
    public Conn(){
        try{
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankmanagementsystem","root","manish@18");
            s = c.createStatement();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
