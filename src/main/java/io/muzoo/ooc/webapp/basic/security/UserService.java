package io.muzoo.ooc.webapp.basic.security;


import io.muzoo.ooc.webapp.basic.db.Database;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    private static Map<String, String> dbList = new Database().getDbList();
//    {
//        users.put("gigadot", new User("gigadot", "12345"));
//        users.put("admin", new User("admin", "12345"));
//    }
    private Map<String, User> users = new HashMap<String, User>(){{
        for(Map.Entry<String,String> entry : dbList.entrySet()){
            System.out.println("User " +  entry.getKey() + " " +  entry.getValue());
            put(entry.getKey(),new User(entry.getKey(), entry.getValue()));
    }
    }};
    void refreshDb(){
        dbList = new Database().getDbList();
        users = new HashMap<String, User>(){{
            for(Map.Entry<String,String> entry : dbList.entrySet()){
//                System.out.println("Customer " +  entry.getKey() + " " +  entry.getValue());
                put(entry.getKey(),new User(entry.getKey(), entry.getValue()));
            }
        }};
    }
    public User findByUsername(String username) {
        refreshDb();
        return users.get(username);
    }

    public boolean checkIfUserExists(String username) {
        refreshDb();
        return users.containsKey(username);
    }


}
