package com.mm.library.domain.user.email;

import com.mm.library.domain.user.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private final JavaMailSender enviadorEmail;
    private static final String EMAIL_ORIGEM = "vollmed@email.com";
    private static final String NOME_ENVIADOR = "Clínica Voll Med";

    public static final String URL_SITE = "http://localhost:8080"; //"voll.med.com.br"

    public EmailService(JavaMailSender enviadorEmail) {
        this.enviadorEmail = enviadorEmail;
    }
    @Async
    private void sentEmail(String userEmail, String subject, String content) {
        MimeMessage message = enviadorEmail.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(EMAIL_ORIGEM, NOME_ENVIADOR);
            helper.setTo(userEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch(MessagingException | UnsupportedEncodingException e){
//            throw new RegraDeNegocioException("Erro ao enviar email");
            throw new RuntimeException(e);
        }

        enviadorEmail.send(message);
    }

    public void sentEmailWithPassword(User user) {
        String subject = "Aqui está seu link para alterar a senha";
        String content = createEmailContent("Olá [[name]],<br>"
                + "Por favor clique no link abaixo para alterar a senha:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ALTERAR</a></h3>"
                + "Obrigado,<br>"
                + "Clínica Voll Med.", user.getName(), URL_SITE + "/recuperar-conta?codigo=" + user.getToken());

        sentEmail(user.getUsername(), subject, content);
    }

    private String createEmailContent(String template, String name, String url) {
        return template.replace("[[name]]", name).replace("[[URL]]", url);
    }
}