package com.ars.message.business.concretes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.ars.message.business.response.ContactResponse;
import com.ars.message.business.response.MessageResponse;
import com.ars.message.business.response.UserInfoResponse;

import jakarta.mail.Address;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailReaderManager {

	public List<ContactResponse> readAndGroupMails(UserInfoResponse userInfoResponse) throws Exception {

		String userMailAddress = userInfoResponse.mailUser();

		List<ContactResponse> contacts = new ArrayList<>();

		Properties props = new Properties();
		props.put("mail.store.protocol", userInfoResponse.protocol());
		props.put("mail.imap.host", userInfoResponse.host());
		props.put("mail.imap.port", userInfoResponse.port());

		if ("imaps".equalsIgnoreCase(userInfoResponse.protocol())) {
			props.put("mail.imap.ssl.enable", "true");
			props.put("mail.imap.ssl.trust", "*");
		}

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(false);

		Store store = session.getStore(userInfoResponse.protocol());
		store.connect(userInfoResponse.host(), userInfoResponse.mailUser(), userInfoResponse.password());

		// Gelen ve gönderilen mailleri işlemek için INBOX ve SENT klasörlerini açalım
		Folder inboxFolder = store.getFolder("INBOX");
		inboxFolder.open(Folder.READ_ONLY);

		Folder sentFolder = store.getFolder("Sent");
		sentFolder.open(Folder.READ_ONLY);

		// Gelen mailleri işleme
		processMails(inboxFolder.getMessages(), contacts, userMailAddress, false);

		// Gönderilen mailleri işleme
		processMails(sentFolder.getMessages(), contacts, userMailAddress, true);

		inboxFolder.close(false);
		sentFolder.close(false);
		store.close();

		// Mesajları zaman sırasına göre sıralama
		for (ContactResponse contact : contacts) {
			contact.getMessages().sort(Comparator.comparing(MessageResponse::getDate));
		}

		return contacts;
	}

	private void processMails(Message[] messages, List<ContactResponse> contacts, String userMailAddress,
			boolean isSent) throws Exception {

		for (Message message : messages) {
			// Gönderenin e-posta adresini al
			String sender = getSenderEmail(message);
			// Alıcıların e-posta adreslerini al
			List<String> recipients = getRecipientEmails(message);

			// Karşı tarafın mail adresini belirle
			String contactMail = isSent ? recipients.get(0) : sender;

			// Mesaj oluştur
			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setId(message.getHeader("Message-ID")[0]);
			messageResponse.setText(getTextFromMessage(message));
			messageResponse.setDate(message.getSentDate());
			messageResponse.setTime(message.getSentDate());
			messageResponse.setSent(isSent);

			// "X-Update-Flag" başlığını kontrol et
			String[] updateFlagHeaderArray = message.getHeader("X-Update-Flag");
			if (updateFlagHeaderArray != null && updateFlagHeaderArray.length > 0) {
				String updateFlagHeader = updateFlagHeaderArray[0];
				if (updateFlagHeader != null && updateFlagHeader.equals("true")) {
					// Eğer "X-Update-Flag" başlığı varsa ve değeri "true" ise, isEdit'i true yap
					messageResponse.setIsEdit(true);
				} else {
					// Aksi takdirde, isEdit'i false yap
					messageResponse.setIsEdit(false);
				}
			} else {
				// Eğer "X-Update-Flag" başlığı yoksa, isEdit'i false yap
				messageResponse.setIsEdit(false);
			}

			// ContactResponse bul veya oluştur
			ContactResponse contactResponse = findOrCreateContactResponse(contacts, contactMail);
			// Mesajı ilgili contactResponse'a ekle
			contactResponse.getMessages().add(messageResponse);
		}

	}

	private String getSenderEmail(Message message) throws Exception {
		Address[] fromAddresses = message.getFrom();
		if (fromAddresses != null && fromAddresses.length > 0) {
			return ((InternetAddress) fromAddresses[0]).getAddress();
		}
		return null;
	}

	private List<String> getRecipientEmails(Message message) throws Exception {

		List<String> recipientEmails = new ArrayList<>();
		Address[] toAddresses = message.getRecipients(Message.RecipientType.TO);
		if (toAddresses != null) {
			for (Address address : toAddresses) {
				recipientEmails.add(((InternetAddress) address).getAddress());
			}
		}
		return recipientEmails;
	}

	private ContactResponse findOrCreateContactResponse(List<ContactResponse> contacts, String contactMail) {
		for (ContactResponse contact : contacts) {
			if (contact.getMailAddress().equals(contactMail)) {
				return contact;
			}
		}

		// Eğer yoksa yeni bir ContactResponse oluştur
		ContactResponse newContact = new ContactResponse();
		newContact.setMailAddress(contactMail);
		newContact.setMessages(new ArrayList<>());
		contacts.add(newContact);
		return newContact;
	}

	private String getTextFromMessage(Message message) throws Exception {
		Object content = message.getContent();
		if (content instanceof String) {
			return Jsoup.parse((String) content).text();
		} else if (content instanceof MimeMultipart) {
			return extractContentFromMimeMultipart((MimeMultipart) content);
		}
		return "";
	}

	private String extractContentFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
		StringBuilder result = new StringBuilder();
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			var bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				// result.append(bodyPart.getContent());
			} else if (bodyPart.isMimeType("text/html")) {
				result.append(Jsoup.parse((String) bodyPart.getContent()).text());
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result.append(extractContentFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
			}
		}
		return result.toString();
	}
}
