
package com.SistemaControlRentaIVAFx.BusinessProcess;

import java.util.HashMap;
import java.util.Map;

public class Authenticator {
    private static final Map<String, String> USERS = new HashMap<String, String>();
    static {
        USERS.put("freddy", "administrador");
    }
    public static boolean validate(String user, String password){
        String validUserPassword = USERS.get(user);
        return validUserPassword != null && validUserPassword.equals(password);
    }
}