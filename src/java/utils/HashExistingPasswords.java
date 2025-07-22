/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Admin
 */
import model.UserDAO;
import model.UserDTO;
import utils.PasswordUtils;

import java.util.List;

public class HashExistingPasswords {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        List<UserDTO> users = userDAO.getAllUserDTOs();

        for (UserDTO user : users) {
            String oldPassword = user.getPasswordHash();
            if (!oldPassword.startsWith("$2a$")) { // chưa mã hoá
                String hashed = PasswordUtils.hashPassword(oldPassword);
                user.setPasswordHash(hashed);
                userDAO.updateUserDTO(user);
                System.out.println("✅ Updated: " + user.getEmail());
            }
        }
    }
}