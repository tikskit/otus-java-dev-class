package ru.tikskit.remote.accesschecking;

public class RoleHandler extends BaseHandlerImpl {
    private final String[] remoteUsers;

    public RoleHandler() {
        remoteUsers = ExpectingAuthData.REMOTE_USER_LIST;
    }

    @Override
    public boolean checkAccess(String user, String pass) {
        if (isRemoteUser(user)) {
            return checkNext(user, pass);
        } else {
            return false;
        }
    }

    private boolean isRemoteUser(String user) {
        for (String u : remoteUsers) {
            if (u.equals(user)) {
                return true;
            }
        }
        return false;
    }


}
