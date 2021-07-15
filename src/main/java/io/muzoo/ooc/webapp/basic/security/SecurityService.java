package io.muzoo.ooc.webapp.basic.security;

import io.muzoo.ooc.webapp.basic.SimplePasswordEncoder;
import io.muzoo.ooc.webapp.basic.db.Database;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class  SecurityService {

    private UserService userService;
    private static Map<String, String> dbList = new Database().getDbList();
    private List<User> users = new ArrayList<User>(){{
        for(Map.Entry<String,String> entry : dbList.entrySet()){
//            System.out.println("Customer " +  entry.getKey() + " " +  entry.getValue());
            add(new User(entry.getKey(), entry.getValue()));
        }
    }};
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getCurrentUsername(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object usernameObject = session.getAttribute("username");
        return (String) usernameObject;
    }

    public boolean isAuthorized(HttpServletRequest request) {
        String username = getCurrentUsername(request);
        return userService.checkIfUserExists(username);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.invalidate();
    }
    void refreshDb(){
        users = new ArrayList<User>(){{
            for(Map.Entry<String,String> entry : dbList.entrySet()){
                add(new User(entry.getKey(), entry.getValue()));
            }
        }};
    }
    public boolean login(HttpServletRequest request) {
//        System.out.println(users.get(0).getUsername());

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.findByUsername(username);
//        System.out.println( SimplePasswordEncoder.decoder(password));
//        String originalInput = "test input";
//        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        System.out.println(encodedPassword);
//        pstm.setString(2, encodedPassword);
        if (user != null && Objects.equals(user.getPassword(), encodedPassword)) {

            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("users", users);
            return true;
        } else {
            return false;
        }

    }

}
