/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author Admin
 */
public class PasswordUtils {
    // Băm mật khẩu thuần
    public static String hashPassword(String plainPassword) {
        // gensalt(12) => cost 12 (2^12 vòng lặp)
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Kiểm tra mật khẩu nhập vào với hash lưu DB
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2"))
            throw new IllegalArgumentException("Hash không hợp lệ");
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
