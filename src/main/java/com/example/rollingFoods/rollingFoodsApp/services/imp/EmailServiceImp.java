package com.example.rollingFoods.rollingFoodsApp.services.imp;

import com.example.rollingFoods.rollingFoodsApp.component.JwtTokenProvider;
import com.example.rollingFoods.rollingFoodsApp.dto.FoodTruckOwnerDTO;
import com.example.rollingFoods.rollingFoodsApp.dto.UserCredentialDTO;
import com.example.rollingFoods.rollingFoodsApp.models.FoodTruckOwner;
import com.example.rollingFoods.rollingFoodsApp.models.UserCredential;
import com.example.rollingFoods.rollingFoodsApp.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;
import java.util.logging.Logger;

@Service
public class EmailServiceImp implements EmailService {

    private static final Logger logger = Logger.getLogger(EmailServiceImp.class.getName());

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("info@rollingFoods")
    private String fromEmail;

    @Value("admin@rollingFoods")
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
            logger.info("Error sending email confirmation for email {} " + userCredential.getEmail());
        }
    }

}
