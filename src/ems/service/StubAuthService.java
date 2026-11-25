package ems.service;

/**
 * Placeholder authentication service used until the backend is implemented.
 * Grants access to the default demonstration account only.
 */
public class StubAuthService implements AuthService {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    @Override
    public boolean authenticate(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}
