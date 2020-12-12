package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {
    private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";

    @Value("${config.mail.host}")
    private String host;
    @Value("${config.mail.port}")
    private String port;
    @Value("${config.mail.username}")
    private String email;
    @Value("${config.mail.password}")
    private String password;

    @Autowired
    ThymeleafService thymeleafService;

    public void sendMail() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

  
        
        //TLS Authencation sendmail
        /*
    	Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		*/
        //SSL
        /*
        Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		};
		Session session = Session.getDefaultInstance(props, auth);
		*/
        //TLS Authencation
        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("thenshinhan204@gmail.com")});

            message.setFrom(new InternetAddress(email));
            message.setSubject("Spring-email-with-thymeleaf subject");
            message.setContent(thymeleafService.getContent(), CONTENT_TYPE_TEXT_HTML);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}