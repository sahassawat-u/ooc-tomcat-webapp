package io.muzoo.ooc.webapp.basic.servlets;

import io.muzoo.ooc.webapp.basic.db.Database;
import io.muzoo.ooc.webapp.basic.security.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class LoginServlet extends AbstractRoutableHttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = "";
//        System.out.println("hi x");
//        Database db = new Database();
        // authentication

        if (securityService.login(request)) {
            HttpSession session = request.getSession();
            Map<String, String> dbList = new Database().getDbList();
            ArrayList users = (ArrayList)session.getAttribute("users");
            users = new ArrayList<User>(){{
                for(Map.Entry<String,String> entry : dbList.entrySet()){
//            System.out.println("Customer " +  entry.getKey() + " " +  entry.getValue());
                    add(new User(entry.getKey(), entry.getValue()));
                }
            }};
            session.setAttribute("users", users);
//            System.out.println(db.getDbList());
             response.sendRedirect("/");
        } else {
            error = "Username or password incorrect. Please try again.";

            request.setAttribute("error", error);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/login.jsp");
            requestDispatcher.include(request, response);
        }

    }

    @Override
    public String getPattern() {
        return "/login";
    }
}
