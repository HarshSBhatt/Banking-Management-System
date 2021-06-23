package asd.group2.bms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class CustomEmail {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendResetPasswordEmail(String email, String forgotPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("dalbank07@gmail.com", "Group 2 Bank");
        helper.setTo(email);

        String subject = "Reset Password Link";
        String content = "<p>Hello Customer,</p>" +
                "<p>You have requested to reset the password</p>" +
                "<p>Click below link to reset your password</p>" +
                "<b><a href=" + forgotPasswordLink + ">Reset my password</a></b>" +
                "<p>Ignore this email if you remember your password or you have not made this request</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }

    public void sendAccountCreationMail(String email, String firstName) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("dalbank07@gmail.com", "Group 2 Bank Account Team");
        helper.setTo(email);

        String subject = "Successful Account Creation";
        String content = "<p>Dear " + firstName + ",</p>" +
                "<p>We have successfully created your account.</p>" +
                "<p>You can use your login credentials to utilise our banking services.</p>" +
                "<p>Thank you.</p>" +
                "<p>Happy Banking!</p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }
}
