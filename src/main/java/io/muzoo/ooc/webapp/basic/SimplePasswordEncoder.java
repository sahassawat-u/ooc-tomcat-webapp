package io.muzoo.ooc.webapp.basic;

public class SimplePasswordEncoder {
    public static String encoder(String password) {
        StringBuilder newPass = new StringBuilder();
        for(int i=0;i<password.length();i++){
            newPass.append(password.charAt(i) + 1);
        }
        return newPass.toString();
    }
    public static String decoder(String password) {
        StringBuilder newPass = new StringBuilder();
        for(int i=0;i<password.length();i++){
            newPass.append(password.charAt(i) - 1);
        }
        return newPass.toString();
    }
}
