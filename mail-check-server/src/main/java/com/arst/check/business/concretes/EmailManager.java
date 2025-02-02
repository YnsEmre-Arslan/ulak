package com.arst.check.business.concretes;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.arst.check.business.abstracts.EmailService;
import com.arst.check.business.request.LoginCheckRequest;
import com.arst.check.business.request.SupportRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailManager implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void checkEmail(LoginCheckRequest loginCheckRequest) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Thymeleaf template için context oluşturuluyor
                        
            Context context = new Context();
            context.setVariable("name", loginCheckRequest.name());
            context.setVariable("code", loginCheckRequest.code());

            // Şablon ile HTML içeriği oluşturuluyor
            String htmlContent = templateEngine.process(loginCheckRequest.template(), context);

            // E-posta ayarları
            helper.setFrom(new InternetAddress("info@arst.tr", "Ulak Haberleşme"));
            helper.setTo(loginCheckRequest.to());
            helper.setSubject("Doğrulama kodu");
            helper.setText(htmlContent, true);  // true: HTML e-posta

            // E-posta gönderimi
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    @Override
    public void sendSupportEmail(SupportRequest supportRequest) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Thymeleaf template için context oluşturuluyor
            Context context = new Context();
            context.setVariable("userName", supportRequest.name());
            context.setVariable("supportTitle", supportRequest.title());  // Destek başlığı eklendi
            context.setVariable("supportMessage", supportRequest.subject());

            // Şablon ile HTML içeriği oluşturuluyor
            String htmlContent = templateEngine.process("support-template.html", context);

            // E-posta ayarları
            helper.setFrom(new InternetAddress("info@arst.tr", "Ulak Haberleşme"));
            helper.setTo(supportRequest.email());
            helper.setSubject("Destek Talebiniz Alındı");
            helper.setText(htmlContent, true);  // true: HTML e-posta

            // E-posta gönderimi
            mailSender.send(message);
            
            // Destek ekibine gönderilecek e-posta
            this.sendEmailToSupportTeam(supportRequest, "Dikkatli şekilde inceleyelim ve geri dönüşü 24 saat içinde olsun");
            
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
 // Destek Ekibine Gönderilecek E-posta Fonksiyonu
    @Override
    public void sendEmailToSupportTeam(SupportRequest supportRequest, String additionalInfo) {
        try {
            MimeMessage supportTeamMessage = mailSender.createMimeMessage();
            MimeMessageHelper supportTeamHelper = new MimeMessageHelper(supportTeamMessage, true, "UTF-8");

            // Thymeleaf template için context oluşturuluyor
            Context supportTeamContext = new Context();
            supportTeamContext.setVariable("supportTitle", supportRequest.title());
            supportTeamContext.setVariable("supportMessage", supportRequest.subject());
            supportTeamContext.setVariable("userName", supportRequest.name());
            supportTeamContext.setVariable("userEmail", supportRequest.email());
            supportTeamContext.setVariable("additionalInfo", additionalInfo); // Ekstra bilgi

            // Destek ekibine gönderilecek HTML içeriği
            String supportTeamHtmlContent = templateEngine.process("support-team-template.html", supportTeamContext);

            // Destek ekibine gönderim ayarları
            supportTeamHelper.setFrom(new InternetAddress("info@arst.tr", "Ulak Haberleşme"));
            supportTeamHelper.setTo("yns.emre.arslann@gmail.com");  // Destek ekibi e-posta adresi
            supportTeamHelper.setSubject("Yeni Destek Talebi: " + supportRequest.title());
            supportTeamHelper.setText(supportTeamHtmlContent, true);  // true: HTML e-posta

            // Destek ekibine e-posta gönderimi
            mailSender.send(supportTeamMessage);

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


	

}
