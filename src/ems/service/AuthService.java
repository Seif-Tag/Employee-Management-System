package ems.service;

/**
 * Temporary authentication service contract. The real backend implementation
 * will replace the stub without changing the UI code.
 */
public interface AuthService {

    boolean authenticate(String username, String password);
}
