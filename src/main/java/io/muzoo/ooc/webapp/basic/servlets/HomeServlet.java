package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.SimplePasswordEncoder;
import io.muzoo.ooc.webapp.basic.db.Database;
import io.muzoo.ooc.webapp.basic.security.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

public class HomeServlet extends AbstractRoutableHttpServlet {

//    private PasswordEncoder passwordEncoder;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("wanna logout");
        String logout = request.getParameter("logout");
        System.out.println(logout);
        if (securityService.isAuthorized(request) && logout==null) {
            String username = securityService.getCurrentUsername(request);
            request.setAttribute("username", username);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/home.jsp");
            requestDispatcher.include(request, response);
        } else {
            response.sendRedirect("/login");
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        MyClass myClass = new MyClass();
//        System.out.println("post from home");
        if (securityService.isAuthorized(request)) {
//            RandomNumberGenerator rng = new SecureRandomNumberGenerator();
//            Object salt = rng.nextBytes();
            System.out.println("post from home");
            String x = request.getParameter("testParam");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String editedName = request.getParameter("toBeEdited");
            String oldName = request.getParameter("edited");
//            System.out.println(editedName);
            HttpSession session = request.getSession();
//            System.out.println(x);
            try
            {
                if(username!=null && password!=null){
                    System.out.println("hi from insert");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/namedb", "ssc", "topsecret");
                    PreparedStatement pstm = conn.prepareStatement("insert into test(firstname,lastname) values(?,?)");
                    pstm.setString(1, username);
                    String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
                    pstm.setString(2, encodedPassword);
                    pstm.executeUpdate();
                    conn.close();
                    Map<String, String> dbList = new Database().getDbList();
                    ArrayList users = (ArrayList)session.getAttribute("users");
                    users = new ArrayList<User>(){{
                        for(Map.Entry<String,String> entry : dbList.entrySet()){
//            System.out.println("Customer " +  entry.getKey() + " " +  entry.getValue());
                            add(new User(entry.getKey(), entry.getValue()));
                        }
                    }};
                    session.setAttribute("users", users);
                }
                else if(editedName==null && username==null && password==null){
//                    System.out.println("testt")
                    System.out.println("hi from delete");
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/namedb", "ssc", "topsecret");
                    PreparedStatement pstm = conn.prepareStatement("delete from test where firstname=? ");
                    pstm.setString(1, x);
                    pstm.executeUpdate();
                    conn.close();
                    Map<String, String> dbList = new Database().getDbList();
                    ArrayList users = (ArrayList)session.getAttribute("users");
                    users = new ArrayList<User>(){{
                        for(Map.Entry<String,String> entry : dbList.entrySet()){
//            System.out.println("Customer " +  entry.getKey() + " " +  entry.getValue());
                            add(new User(entry.getKey(), entry.getValue()));
                        }
                    }};
                    session.setAttribute("users", users);
                }
                else if(editedName!=null){
                    System.out.println(editedName);
//                    System.out.println(oldName);
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/namedb", "ssc", "topsecret");
                    PreparedStatement pstm = conn.prepareStatement("update test set firstname=? where firstname=? ");
                    pstm.setString(1, editedName);
                    pstm.setString(2, oldName);
                    pstm.executeUpdate();
                    conn.close();
                    Map<String, String> dbList = new Database().getDbList();
                    ArrayList users = (ArrayList)session.getAttribute("users");
                    users = new ArrayList<User>(){{
                        for(Map.Entry<String,String> entry : dbList.entrySet()){
//            System.out.println("Customer " +  entry.getKey() + " " +  entry.getValue());
                            add(new User(entry.getKey(), entry.getValue()));
                        }
                    }};
                    session.setAttribute("users", users);
                }
//                Statement st=conn.createStatement();
//                int i=st.executeUpdate("DELETE FROM test WHERE firstname="+x);
//                System.out.println("Data Deleted Successfully!");
            }
            catch(Exception e)
            {
                System.out.print(e);
                e.printStackTrace();
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/home.jsp");
            requestDispatcher.include(request, response);
        } else {
            response.sendRedirect("/login");
        }
//        if (request.getParameter("button1") != null) {
////            myClass.method1();
//        } else if (request.getParameter("button2") != null) {
////            myClass.method2();
//        } else if (request.getParameter("button3") != null) {
////            myClass.method3();
//        } else {
//            // ???
//        }

    }

    @Override
    public String getPattern() {
        return "/index.jsp";
    }
}
