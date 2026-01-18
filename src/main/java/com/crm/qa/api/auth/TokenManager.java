package com.crm.qa.api.auth;

/**
 * TokenManager
 * ----------------
 * Centralized in-memory storage and lifecycle manager
 * for authentication tokens.
 *
 * DESIGN PRINCIPLES:
 * - Token is generated ONLY when required
 * - Token is generated ONLY once per test run
 * - Thread-safe for parallel execution
 * - Used ONLY by secured APIs
 *
 * This class ensures that non-auth APIs
 * remain completely unaffected.
 */
public class TokenManager {

    /**
     * Stored authentication token
     * Volatile ensures visibility across threads
     */
    private static volatile String token;

    /**
     * Private constructor
     * Prevents object instantiation
     */
    private TokenManager() {
    }

    /**
     * Returns a valid authentication token.
     *
     * 🔹 If token already exists → reused
     * 🔹 If token does not exist → generated once
     *
     * @return valid auth token
     */
    public static String getToken() {

        // -------------------------------
        // Double-checked locking
        // Ensures thread safety + performance
        // -------------------------------
        if (token == null || token.isEmpty()) {
            synchronized (TokenManager.class) {
                if (token == null || token.isEmpty()) {
                    token = AuthApiClient.generateToken();
                }
            }
        }
        return token;
    }

    /**
     * Clears stored token
     * Useful for:
     * - Logout flows
     * - Token expiration scenarios
     * - Fresh re-authentication
     */
    public static void clearToken() {
        token = null;
    }

    /**
     * Checks whether token already exists
     *
     * @return true if token is available
     */
    public static boolean hasToken() {
        return token != null && !token.isEmpty();
    }
}

