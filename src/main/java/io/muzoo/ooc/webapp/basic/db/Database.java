package io.muzoo.ooc.webapp.basic.db;

import io.muzoo.ooc.webapp.basic.security.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, String> dbList;
    private User user;
    public Database(){
        dbList = new HashMap<>();
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/namedb";
            String user = "ssc";
            String pass = "topsecret";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    dbUrl,user,pass);
//            System.out.println("connection successful");
            String sql = "SELECT * FROM test";
            Statement stm = con.createStatement();
            ResultSet result = stm.executeQuery(sql);
            int count = 0;
//            System.out.println("hi");
            while(result.next()){
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                count++;
                dbList.put(firstname,lastname);
//                System.out.println("Customer " + count + " " + firstname + " " + lastname);
            }
            con.close();
//            stm.setString(1,"roh");
//            stm.setString(2,"jisun");
//            if (stm.executeUpdate() > 0){
//                System.out.println("A row inserted");
//            }
//            stm.close();
//            con.close();
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("select * from test");
//            while(rs.next())
//                System.out.println(rs.getInt(1) + " " +
//                        rs.getString(2));
//            con.close();

        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Map<String, String> getDbList() {
        return dbList;
    }
}
