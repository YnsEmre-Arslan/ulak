package com.ars.message.business.concretes;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ars.message.business.abstracts.SentMailService;
import com.ars.message.business.request.DeleteMessageRequest;
import com.ars.message.business.request.SendEmailRequest;
import com.ars.message.business.request.UpdateMessageRequest;

import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Header;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.search.HeaderTerm;

@Service
public class SentMailManager implements SentMailService {

    public void sendEmail(SendEmailRequest emailRequest) throws MessagingException {
        // JavaMailSender yapılandırması
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailRequest.host());
        mailSender.setPort(emailRequest.port());
        mailSender.setUsername(emailRequest.from());
        mailSender.setPassword(emailRequest.password());

        // SMTP özellikleri
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        // E-posta mesajını oluşturma
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom(emailRequest.from());
        helper.setTo(emailRequest.to());
        helper.setSubject("Ulak Haberleşme"); // Başlık için de UTF-8 kullanılıyor
        helper.setText(emailRequest.body(), true);  // HTML içeriği UTF-8 olarak gönderiliyor

        // E-postayı gönderme
        mailSender.send(message);

        // IMAP bağlantısı kurma ve gönderilen e-postayı "Sent" klasörüne kaydetme
        saveToSentFolder(emailRequest, message);
    }

    private void saveToSentFolder(SendEmailRequest emailRequest, MimeMessage message) throws MessagingException {
        // IMAP üzerinden mail sunucusuna bağlanma
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imap");
        properties.put("mail.imap.host", emailRequest.host());
        properties.put("mail.imap.port", "993");  // IMAP SSL portu
        properties.put("mail.imap.ssl.enable", "true");

        // Kullanıcı adı ve şifre ile IMAP bağlantısı açma
        Session emailSession = Session.getInstance(properties);
        Store store = emailSession.getStore("imap");
        store.connect(emailRequest.host(), emailRequest.from(), emailRequest.password());

        // "Sent" klasörüne bağlanma
        Folder sentFolder = store.getFolder("Sent");
        if (!sentFolder.exists()) {
            sentFolder.create(Folder.HOLDS_MESSAGES);
        }
        sentFolder.open(Folder.READ_WRITE);

        // Gönderilen mesajı "Sent" klasörüne kaydetme
        sentFolder.appendMessages(new Message[]{message});

        // Bağlantıyı kapatma
        sentFolder.close(false);
        store.close();
    }

    public void deleteEmail(DeleteMessageRequest deleteMessageRequest) throws MessagingException {

        // IMAP yapılandırması
        Properties props = new Properties();
        props.put("mail.store.protocol", "imap");
        props.put("mail.imap.host", deleteMessageRequest.host());
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");

        // Oturum oluşturma
        Session session = Session.getInstance(props, null);
        Store store = session.getStore("imap");
        store.connect(deleteMessageRequest.username(), deleteMessageRequest.password());

        // Tüm klasörleri listeleme
        Folder[] folders = store.getDefaultFolder().list("*");
        
        

        boolean messageFoundAndDeleted = false;

        // Her klasörü kontrol etme
        for (Folder folder : folders) {
        	

        	
        	
        	
            if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) { // Sadece mesaj tutan klasörler
                folder.open(Folder.READ_WRITE);
                

                // Mesajı arama
                Message[] messages = folder.search(new HeaderTerm("Message-ID", deleteMessageRequest.messageId()));

                if (messages.length > 0) {
                    // Silme işlemi
                    for (Message message : messages) {
                        message.setFlag(Flags.Flag.DELETED, true);
                    }
                    System.out.println("Mesaj başarıyla silindi: " + folder.getFullName());
                    messageFoundAndDeleted = true;
                }

                folder.close(true); // Değişiklikleri kaydet ve kapat
            }
        }

        if (!messageFoundAndDeleted) {
            System.out.println("Belirtilen ID ile eşleşen mesaj bulunamadı. " + deleteMessageRequest.messageId());
        }

        // Bağlantıyı kapatma
        store.close();
    }

    
    public String updateEmail(UpdateMessageRequest updateMessageRequest) throws MessagingException, IOException {

    	
    	String newId = "";
    	
        // IMAP yapılandırması
        Properties props = new Properties();
        props.put("mail.store.protocol", "imap");
        props.put("mail.imap.host", updateMessageRequest.host());
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");

        // Oturum oluşturma
        Session session = Session.getInstance(props, null);
        Store store = session.getStore("imap");
        store.connect(updateMessageRequest.username(), updateMessageRequest.password());

        Folder folder = store.getFolder("Sent");
        folder.open(Folder.READ_WRITE);

        // Mesajı arama
        Message[] messages = folder.search(new HeaderTerm("Message-ID", updateMessageRequest.messageId()));

        if (messages.length > 0) {
            MimeMessage originalMessage = (MimeMessage) messages[0];

            // Yeni mesaj oluşturma
            MimeMessage updatedMessage = new MimeMessage(session);

            // Tüm header bilgilerini kopyalama
            for (Enumeration<?> headers = originalMessage.getAllHeaders(); headers.hasMoreElements(); ) {
                Header header = (Header) headers.nextElement();
                updatedMessage.setHeader(header.getName(), header.getValue());
            }

            // Yeni içerik ekleme
            updatedMessage.setContent(updateMessageRequest.newText(), "text/html; charset=utf-8");

            // Tarihi kopyalama
            updatedMessage.setSentDate(originalMessage.getSentDate());

            // "UPDATE" bayrağını ekliyoruz
            Flags updateFlag = new Flags();
            updateFlag.add("UPDATE");  // Burada "UPDATE" bayrağını kendiniz tanımlıyorsunuz
            updatedMessage.setFlags(updateFlag, true);

            // Özel başlık eklemek
            updatedMessage.setHeader("X-Update-Flag", "true");  // Özel başlık ekleyin
            updatedMessage.setHeader("X-Updated-By", "System");  // Başka bir örnek başlık

            // Yeni mesajı gönderme
            folder.appendMessages(new Message[]{updatedMessage});

            // Eski mesajı silme
            originalMessage.setFlag(Flags.Flag.DELETED, true);

            // Yeni mesaja "UPDATE" bayrağını ekleyin
            updatedMessage.setFlags(updateFlag, true);
            
            newId = originalMessage.getHeader("Message-ID")[0];

            
            

        } else {
            // Mesaj bulunamadı, yapılacak bir işlem yok
        }

        folder.close(true);
        store.close();
        
        return newId;

    }

    
}


