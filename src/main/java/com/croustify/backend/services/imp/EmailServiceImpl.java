package com.croustify.backend.services.imp;

import com.croustify.backend.component.JwtTokenProvider;
import com.croustify.backend.dto.ContactDTO;
import com.croustify.backend.dto.NewFoodTruckOwnerDTO;
import com.croustify.backend.models.Company;
import com.croustify.backend.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class.getName());

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.from}")
    private String fromEmail;

    @Value("${spring.mail.admin}")
    private String adminEmail;


    @Autowired
    private JavaMailSenderImpl mailSender;

    @Async
    @Override
    public void sendCompanyInvitation(Company company, String email) {
        // TODO send email
    }

    @Async
    @Override
    public void sendPasswordResetToken(String email, String token) {
        final Context context = new Context();

        String url = "http://localhost:4200/reset-password?token=" + token;

        context.setVariable("url", url);
        context.setVariable("email", email);


        MimeMessage mimeMessage = mailSender.createMimeMessage();


        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, "utf-8");
            helper.setTo(email);
            helper.setSubject("Password Reset");
            String htmlContent = templateEngine.process("passwordReset", context);
            helper.setText(htmlContent, true);
            helper.addInline("logo", new ClassPathResource("static/images/logo_croustify.png"));
            helper.addInline("logoGrey", new ClassPathResource("static/images/LogoGrey.png"));
            helper.addInline("Facebook", new ClassPathResource("static/images/Facebook.png"));
            helper.addInline("Instagram", new ClassPathResource("static/images/Instagram.png"));
            helper.addInline("tiktok", new ClassPathResource("static/images/tiktok.png"));
            mimeMessage.setFrom(fromEmail);
            javaMailSender.send(mimeMessage);
            logger.info("Email sent for email {} " , email);
        } catch (Exception e) {
            logger.error("Error sending email for email {} ", email,e);
        }
    }

    @Override
    public void sendEmailConfirmationFoodTruckOwner(String email) {
        final Context context = new Context();

        context.setVariable("email", email);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, "utf-8");
            helper.setTo(email);
            helper.setSubject("Confirmation Email Food Truck Owner");
            String htmlContent = templateEngine.process("confirmationEmailFoodTruckOwner", context);
            helper.setText(htmlContent, true);
            helper.addInline("logo", new ClassPathResource("static/images/logo_croustify.png"));
            helper.addInline("logoGrey", new ClassPathResource("static/images/LogoGrey.png"));
            helper.addInline("Facebook", new ClassPathResource("static/images/Facebook.png"));
            helper.addInline("Instagram", new ClassPathResource("static/images/Instagram.png"));
            helper.addInline("tiktok", new ClassPathResource("static/images/tiktok.png"));
            mimeMessage.setFrom(fromEmail);
            javaMailSender.send(mimeMessage);
            logger.info("Email sent for email {} " , email);
        } catch (Exception e) {
            logger.error("Error sending email for email {} ", email,e);
        }
    }

    @Async
    @Override
    public void sendEmailNotificationContact(ContactDTO contactDTO) {

        final Context context = new Context();

        context.setVariable("email", contactDTO.getEmail());
        context.setVariable("name", contactDTO.getName());
        context.setVariable("phone", contactDTO.getPhone());
        context.setVariable("message", contactDTO.getMessage());

        MimeMessage mimeMessage = mailSender.createMimeMessage();


        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, "utf-8");
            helper.setTo(contactDTO.getEmail());
            helper.setSubject("Notification Email");
            String htmlContent = templateEngine.process("notificationEmailContact", context);
            helper.setText(htmlContent, true);
            helper.addInline("logo", new ClassPathResource("static/images/logo_croustify.png"));
            helper.addInline("logoGrey", new ClassPathResource("static/images/LogoGrey.png"));
            helper.addInline("Facebook", new ClassPathResource("static/images/Facebook.png"));
            helper.addInline("Instagram", new ClassPathResource("static/images/Instagram.png"));
            helper.addInline("tiktok", new ClassPathResource("static/images/tiktok.png"));
            mimeMessage.setFrom(fromEmail);
            javaMailSender.send(mimeMessage);
            logger.info("Email notification sent for email {} " , contactDTO.getEmail());

            final Context contextAdmin = new Context();

            contextAdmin.setVariable("email", contactDTO.getEmail());
            contextAdmin.setVariable("name", contactDTO.getName());
            contextAdmin.setVariable("phone", contactDTO.getPhone());
            contextAdmin.setVariable("message", contactDTO.getMessage());

            MimeMessage mimeMessageAdmin = mailSender.createMimeMessage();
            MimeMessageHelper helperAdmin = new MimeMessageHelper(mimeMessageAdmin,true, "utf-8");

            try {
                helperAdmin.setTo(adminEmail);
                helperAdmin.setSubject("Notification nouvelle demande de partenariat");
                String htmlContentAdmin = templateEngine.process("notificationEmailContactAdmin", contextAdmin);
                helperAdmin.setText(htmlContentAdmin, true);
                helperAdmin.addInline("logo", new ClassPathResource("static/images/logo_croustify.png"));
                mimeMessageAdmin.setFrom(fromEmail);
                javaMailSender.send(mimeMessageAdmin);
                logger.info("Email notification sent for email {} " , adminEmail);
            } catch (Exception e) {
                logger.error("Error sending email notification for email {} ", adminEmail,e);
            }
        } catch (Exception e) {
            logger.error("Error sending email notification for email {} ", contactDTO.getEmail(),e);
        }
    }




}
