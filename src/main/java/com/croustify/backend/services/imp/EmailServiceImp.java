package com.croustify.backend.services.imp;

import com.croustify.backend.component.JwtTokenProvider;
import com.croustify.backend.dto.ContactDTO;
import com.croustify.backend.models.FoodTruckOwner;
import com.croustify.backend.models.UserCredential;
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
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



@Service
public class EmailServiceImp implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImp.class.getName());

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("info@croustify.com")
    private String fromEmail;

    @Value("admin@croustify.com")
    private String adminEmail;


    @Autowired
    private JavaMailSenderImpl mailSender;


    @Override
    public void sendEmailConfirmation(final FoodTruckOwner foodTruckOwnerDTO, final UserCredential userCredential) {
        logger.info("Sending email confirmation for email {} " + userCredential.getEmail());

        String uniqueToken = jwtTokenProvider.generateTokenForValidationAccount(userCredential);
        String validationUrl = "http://localhost:8686/api/validateAccount?token=" + uniqueToken;

        final Context context = new Context();



        context.setVariable("email", userCredential.getEmail());
        context.setVariable("firstname", foodTruckOwnerDTO.getFirstname());
        context.setVariable("lastname", foodTruckOwnerDTO.getLastname());
        context.setVariable("phone", foodTruckOwnerDTO.getPhoneNumber());
        context.setVariable("companyName", foodTruckOwnerDTO.getCompanyName());
        context.setVariable("tva", foodTruckOwnerDTO.getTva());
        context.setVariable("street", foodTruckOwnerDTO.getAddress().getStreet());
        context.setVariable("bankNumber", foodTruckOwnerDTO.getAddress().getStreetNumber());
        context.setVariable("city", foodTruckOwnerDTO.getAddress().getCity());
        context.setVariable("zipCode", foodTruckOwnerDTO.getAddress().getPostalCode());
        context.setVariable("country", foodTruckOwnerDTO.getAddress().getCountry());

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setTo(userCredential.getEmail());
            helper.setSubject("Email Confirmation");
            String htmlContent = templateEngine.process("confirmationEmail", context);
            helper.setText(htmlContent, true);
            mimeMessage.setFrom(fromEmail);
            javaMailSender.send(mimeMessage);


            final Context contextAdmin = new Context();

            contextAdmin.setVariable("email", userCredential.getEmail());
            contextAdmin.setVariable("firstname", foodTruckOwnerDTO.getFirstname());
            contextAdmin.setVariable("lastname", foodTruckOwnerDTO.getLastname());
            contextAdmin.setVariable("phone", foodTruckOwnerDTO.getPhoneNumber());
            contextAdmin.setVariable("companyName", foodTruckOwnerDTO.getCompanyName());
            contextAdmin.setVariable("tva", foodTruckOwnerDTO.getTva());
            contextAdmin.setVariable("street", foodTruckOwnerDTO.getAddress().getStreet());
            contextAdmin.setVariable("bankNumber", foodTruckOwnerDTO.getAddress().getStreetNumber());
            contextAdmin.setVariable("city", foodTruckOwnerDTO.getAddress().getCity());
            contextAdmin.setVariable("zipCode", foodTruckOwnerDTO.getAddress().getPostalCode());
            contextAdmin.setVariable("country", foodTruckOwnerDTO.getAddress().getCountry());
            contextAdmin.setVariable("validationUrl", validationUrl);

            MimeMessage mimeMessageAdmin = mailSender.createMimeMessage();
            MimeMessageHelper helperAdmin = new MimeMessageHelper(mimeMessageAdmin, "utf-8");

            helperAdmin.setTo(adminEmail);
            helperAdmin.setSubject("New Food Truck Owner");
            String htmlContentAdmin = templateEngine.process("confirmationEmailAdmin", contextAdmin);
            helperAdmin.setText(htmlContentAdmin, true);
            mimeMessageAdmin.setFrom(fromEmail);
            javaMailSender.send(mimeMessageAdmin);



        } catch (Exception e) {
            logger.info("Error sending email confirmation for email {} " , userCredential.getEmail());
        }
    }

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
            helper.addInline("logoCroustify", new ClassPathResource("static/images/LogoGrey.png"));
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
                logger.info("Error sending email notification for email {} ", adminEmail,e);
            }
        } catch (Exception e) {
            logger.info("Error sending email notification for email {} ", contactDTO.getEmail(),e);
        }
    }

}
