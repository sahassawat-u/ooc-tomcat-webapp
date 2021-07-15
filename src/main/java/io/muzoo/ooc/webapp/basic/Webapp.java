package io.muzoo.ooc.webapp.basic;

import io.muzoo.ooc.webapp.basic.servlets.ServletRouter;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.beans.Encoder;
import java.io.File;
import java.sql.*;
import java.util.Base64;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import java.util.Scanner;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Webapp {

    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
//        try {
//            String dbUrl = "jdbc:mysql://localhost:3306/namedb";
//
//
//            String user = "root";
//            String pass = "bossy2012";
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection(
//                    dbUrl,user,pass);
//            System.out.println("connection successful");
//            String sql = "INSERT INTO test (firstname, lastname) VALUES (?,?)";
//            PreparedStatement stm = con.prepareStatement(sql);
//            stm.setString(1,"roh");
//            stm.setString(2,"jisun");
//            if (stm.executeUpdate() > 0){
//                System.out.println("A row inserted");
//            }
//            stm.close();
//            con.close();
////            Statement stmt = con.createStatement();
////            ResultSet rs = stmt.executeQuery("select * from test");
////            while(rs.next())
////                System.out.println(rs.getInt(1) + " " +
////                        rs.getString(2));
////            con.close();
//
//        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

//        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
//        Base64.Decoder decoder = Base64.getUrlDecoder();
//        System.out.print("Password: ");
//        String strPassword = new Scanner(System.in).nextLine();
//        String strSalt_ = "Your Salt String Here";
//        String strSalt = new String(strSalt_, "UTF-8");
//        byte[] bSalt = decoder.decode(strSalt); // String to Byte
//        System.out.println("Salt: " + strSalt);
//        System.out.println("String to be hashed: " + strPassword + strSalt);
//        String strHash = encoder.encodeToString(Cryptography.Hash(strPassword, bSalt)); // Byte to String
//        System.out.println("Hashed value (Password + Salt value): " + strHash);






        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);

        File doceBase = new File("src/main/webapp/");
        doceBase.mkdirs();

        try {
            Context ctx = tomcat.addWebapp("", doceBase.getAbsolutePath());

            ServletRouter servletRouter = new ServletRouter();
            servletRouter.init(ctx);

            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

    }
}
