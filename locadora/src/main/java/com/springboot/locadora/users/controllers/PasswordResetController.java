package com.springboot.locadora.users.controllers;


import com.springboot.locadora.users.entities.EmailEntity;
import com.springboot.locadora.users.entities.PasswordResetRequest;
import com.springboot.locadora.users.entities.TokenValidationRequest;
import com.springboot.locadora.users.services.EmailService;
import com.springboot.locadora.users.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PasswordResetController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @PostMapping("/forgot")
    public ResponseEntity<String> processForgotPassword(@RequestBody EmailEntity emailRequestDTO) {
        String email = emailRequestDTO.getEmail();

        String token = userServices.createPasswordResetToken(email);
        if (token == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        String userName = userServices.getUserNameByEmail(email);
        if (userName == null) {
            return ResponseEntity.badRequest().body("Nome do usuário não encontrado.");
        }

        String resetLink = token;

        emailService.sendCustomEmail(email, userName, resetLink);

        return ResponseEntity.ok("Instruções de redefinição de senha enviadas para " + email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        String token = passwordResetRequest.getToken();
        String newPassword = passwordResetRequest.getNewPassword();

        boolean result = userServices.resetPassword(token, newPassword);
        if (result) {
            return ResponseEntity.ok("Senha redefinida com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Falha ao redefinir a senha. Token inválido ou expirado.");
        }
    }


    @PostMapping("/reset-password/validate")
    public ResponseEntity<String> validateResetToken(@RequestBody TokenValidationRequest request) {
        String token = request.getToken();
        boolean isValid = userServices.validatePasswordResetToken(token);
        if (!isValid) {
            return ResponseEntity.badRequest().body("Token inválido ou expirado.");
        }
        return ResponseEntity.ok("Token válido.");
    }
}
