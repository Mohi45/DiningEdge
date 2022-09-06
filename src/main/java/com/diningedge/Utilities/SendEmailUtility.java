package com.diningedge.Utilities;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static com.diningedge.Utilities.ConfigPropertyReader.getProperty;

import com.diningedge.resources.BaseUi;

public class SendEmailUtility extends BaseUi {

	/**
	 * This is used to create connection to the Gmail for specific user
	 * 
	 * @return
	 * @throws MessagingException
	 */

	public static Session createConnection() throws MessagingException {
		logMsg("Connecting to the Gmail ...");

		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", getProperty("email"));
		props.put("mail.smtp.password", getProperty("password"));
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getProperty("email"), getProperty("password"));// Specify the Username
																									// and the PassWord
			}
		});
		return session;
	}

	/**
	 * This is perform the actions related to the mail sending
	 * 
	 * @param PurveyorName
	 * @param RestaurantName
	 * @param fileType
	 */
	public static void sendMailAction(String PurveyorName, String RestaurantName, String fileType) {
		String to = getProperty("email");
		String user = "testprav59@gmail.com";// change accordingly
		try {
			// get connection
			Session session = createConnection();
			File GFS_OG = CustomFunctions.getLatestFilefromDir(System.getProperty("user.home") + "\\Downloads\\",
					fileType);
			String filename = GFS_OG.getAbsolutePath();
			System.out.println(filename);

			MimeMessage message = new MimeMessage(session);

			MimeMessage messageBodyPart1 = new MimeMessage(session);
			messageBodyPart1.setFrom(new InternetAddress(user));// change accordingly
			messageBodyPart1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("OnLineMacro :: " + PurveyorName + " :: " + RestaurantName);

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(filename);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart2);

			message.setContent(multipart);

			Transport.send(message, messageBodyPart1.getAllRecipients());

			System.out.println("Message send success for " + RestaurantName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * This is used for send reports for user
	 * 
	 * @param Subject
	 * @param filenames
	 */
	public static void sendReports(String Subject, String... filenames) {
		try {

			String user = "testprv59@gmail.com";
			String[] to = { "testprv59@gmail.com" };// list of users to keep in TO
			String cc = ""; // "any email to keep in cc"

			// get connection
			Session session = createConnection();

			MimeMessage message = new MimeMessage(session);

			MimeMessage messageBodyPart1 = new MimeMessage(session);
			messageBodyPart1.setFrom(new InternetAddress(user));// change accordingly

			InternetAddress[] recipientAddress = new InternetAddress[to.length];
			int counter = 0;
			for (String recipient : to) {
				recipientAddress[counter] = new InternetAddress(recipient.trim());
				counter++;
			}

			messageBodyPart1.addRecipients(Message.RecipientType.TO, recipientAddress);
			messageBodyPart1.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));

			// Subject of mails
			message.setSubject(Subject);
			// Body of mails
			String date = CustomFunctions.getCurrentTime();
			message.setContent("Attached is the report for the OG export on : " + date, "text");

			// 4) create new MimeBodyPart object and set DataHandler object to
			// this object
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			for (String filename : filenames) {
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(filename);
				logMsg("Attached file - " + filename);

			}
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart2);

			message.setContent(multipart);

			Transport.send(message, messageBodyPart1.getAllRecipients());

			logMsg("Message send success");

		} catch (Exception e) {
			logMsg(e.getMessage());
			logMsg("Technical issue in sending reporting");
		}

	}

}
