package lk.ijse.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    // Hash a password
    public static String hashPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }

    // Verify a password
    public static boolean isPasswordMatch(String plainPassword, String hashedPassword) {
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword).verified;
    }
}

