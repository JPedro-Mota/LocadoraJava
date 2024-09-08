package com.springboot.locadora.users.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendCustomEmail(String to, String name, String resetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Redefinição de Senha");
            helper.setText(getHtmlContent(name, resetToken), true);

            mailSender.send(message);
            System.out.println("E-mail enviado com sucesso para " + name + ".");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private String getHtmlContent(String name, String resetToken) {
        if (name == null || name.trim().isEmpty()) {
            name = "Usuário";
        }

        return "<!DOCTYPE html>"
                + "<html><head><style>"
                + "body { font-family: Arial, sans-serif; color: #333; background-color: #f4f4f4; padding: 20px; margin: 0; }"
                + ".container { max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }"
                + ".header { background-color: #007bff; color: #fff; padding: 10px; text-align: center; border-radius: 8px 8px 0 0; }"
                + ".token { display: flex; justify-content: center; align-items: center; text-align: center; font-weight: bold; color: black; background-color: #f0f0f0; padding: 10px; border: 1px solid #ddd; border-radius: 4px; margin-top: 30px; word-wrap: break-word; width: fit-content; margin-left: auto; margin-right: auto; }"
                + ".content { padding: 20px; color: #666666; font-size: 16px; }"
                + ".texto { margin: 30px 0 20px 0; }"
                + ".footer { text-align: center; padding: 10px; font-size: 12px; color: #888; margin-top: 10px; }"
                + "</style></head><body>"
                + "<div class='container'>"
                + "<div class='content'>"
                + "<p>Olá " + name + ",</p>"
                + "<p class='texto'>Recebemos uma solicitação para redefinir sua senha. Copie o token abaixo e cole-o na página de redefinição de senha:</p>"
                + "<div class='token'>" + resetToken + "</div>"
                + "</div>"
                + "<div class='footer'>Todos os direitos reservados a @JPedro-Mota MotaDev</div>"
                + "</div>"
                + "</body></html>";
    }



}