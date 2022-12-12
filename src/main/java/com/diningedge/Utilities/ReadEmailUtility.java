package com.diningedge.Utilities;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.SearchTerm;

import com.diningedge.common.ParsingEmails;
import com.diningedge.resources.BaseUi;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPSSLStore;

public class ReadEmailUtility extends BaseUi {
	Properties properties = null;
	IMAPSSLStore store = null;
	IMAPFolder folderInbox = null;
	IMAPFolder successFullyDone=null;
	String messageContent;
	String contentType;
	List<String> details = new ArrayList<>();

	private static IMAPSSLStore createConnection() throws MessagingException {
		// Create IMAPSSLStore object
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props, null);
		// URLName urlName = new URLName("imap.gmail.com");
		Store store = session.getStore("imaps");
		// IMAPSSLStore store = new IMAPSSLStore(session, urlName);

		// TODO: All sysout statements are used for testing, have to remove them
		// while implementation
		log("Connecting to gmail...");

		// Connect to GMail, enter user name and password here
		store.connect("imap.gmail.com", getProperty("email"), getProperty("gmailPassword"));

		log("Connected to - " + store);
		return (IMAPSSLStore) store;
	}

	public List<String> readMail() {
		try {
			store = createConnection();
			folderInbox = (IMAPFolder) store.getFolder("INBOX");
			successFullyDone = (IMAPFolder) store.getFolder("Successfully Done");
			List<Message> tempSuccessList = new ArrayList<>();
			
			folderInbox.open(Folder.READ_WRITE);
			SearchTerm rawTerm = new SearchTerm() {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean match(Message message) {
					try {
						log("Subject is :-" + message.getSubject());
						if (message.getSubject().contains("order")||message.getSubject().contains("Order")) {
							return true;
						}
					} catch (MessagingException ex) {
						ex.printStackTrace();
					}
					return false;
				}
			};
			Message[] messagesFound = folderInbox.search(rawTerm);
			System.out.println("Number of mails = " + messagesFound.length);
			for (int i = 0; i < messagesFound.length; i++) {
				Message message = messagesFound[i];
				Address[] from = message.getFrom();
				System.out.println("-------------------------------");
				System.out.println("Date : " + message.getSentDate());
				System.out.println("From : " + from[0]);
				System.out.println("Subject: " + message.getSubject());
				System.out.println("Content :");
				System.out.println("--------------------------------");
				contentType = message.getContentType();
				if (contentType.contains("text/plain") || contentType.contains("text/html")) {
					Object content = message.getContent();
					if (content != null) {
						messageContent = content.toString();
					}
				} else if (contentType.contains("multipart")) {
					Multipart multiPart = (Multipart) message.getContent();
					int numberOfParts = multiPart.getCount();
					for (int partCount = 0; partCount < numberOfParts; partCount++) {
						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
						if (!Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
							messageContent = getTextFromMimeMultipart(part);
						}
					}
				}
				details = ParsingEmails.parseCurrentVersion(messageContent);
				tempSuccessList.add(message);
				message.setFlag(Flags.Flag.DELETED, true);
				logMessage(message.getSubject() + " :: email deleted successfully !!");
			}
			 Message[] tempSuccessMessageArray = tempSuccessList.toArray(new Message[tempSuccessList.size()]);
            folderInbox.copyMessages(tempSuccessMessageArray, successFullyDone);
            logMessage(" email successfully Done!!");
            folderInbox.close(true);
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}

	private String getTextFromMimeMultipart(MimeBodyPart bodyPart) {
		String messageContent = null;
		InputStream inputStream;
		StringBuilder responseBuffer = new StringBuilder();
		try {
			inputStream = bodyPart.getInputStream();
			byte[] temp = new byte[1024];

			int countCurrentRead;
			while ((countCurrentRead = inputStream.read(temp)) > 0) {
				responseBuffer.append(new String(temp, 0, countCurrentRead, StandardCharsets.UTF_8));
			}
		} catch (IOException | MessagingException e) {
			e.printStackTrace();
		}

		messageContent = responseBuffer.toString();
		return messageContent;
	}

	public static void main(String... strings) {
		ReadEmailUtility rd = new ReadEmailUtility();
		rd.readMail();
	}
}
