package ru.tikskit.remote.accesschecking;

import java.util.HashMap;
import java.util.Map;

public class UserExistsHandler extends BaseHandlerImpl {
    private final Map<String, String> userList;

    public UserExistsHandler() {
        userList = new HashMap<>();
        userList.put(ExpectingAuthData.USER, ExpectingAuthData.PASS);
    }

    @Override
    public boolean checkAccess(String user, String pass) {
        if (userExists(user, pass)) {
            return checkNext(user, pass);
        } else {
            return false;
        }
    }

    private boolean userExists(String user, String pass) {
        String userPass = userList.get(user);
        return (userPass != null) && userPass.equals(pass);
    }
}
