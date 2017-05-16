package com.bizprout.web.app.resource;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Email {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public void sendEmail(String toaddress, String user, String password){

		// Recipient's email ID needs to be mentioned.
		String to = toaddress;

		// Sender's email ID needs to be mentioned
		String from = "epacc_hrms@bizprout.com";

		// Assuming you are sending email from localhost
		String host = "103.224.23.175";

		String wlink="http://localhost:9090/perpetuity/#/";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.user", from);
		properties.setProperty("mail.password", "Rqt45@2013");
		properties.setProperty("mail.smtp.port", "587");
		

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			 protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(properties.getProperty("mail.user"), properties.getProperty("mail.password"));
		   }
		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Welcome to Perpetuity");

			// Now set the actual message
			message.setContent("Dear "+ user + ",<br><br> Your Username and Password for Perpetuity Login: <br><br> "
					+ "<b>Your Username: </b>"+ user + "<br>"
					+ "<b>Your Password: </b>"+ password+ "<br><br>"
					+ "Please login to "+ wlink +" to access Perpetuity.<br/><br/>"
					+ "To ensure confidentiality, Do not share your username and password with others.<br>"
					+ "Looking forward to more opportunities to be of service to you.<br><br>"
					+ "Regards,<br>"
					+ "<b>Team Bizprout.</b>"
					+ "<p>This is an auto-generated e-mail. Please do not reply to this email.</p>", "text/html");

			// Send message
			Transport.send(message);
			
			logger.debug("Email Sent Successfully to "+ to);
			
		}catch (MessagingException e) {
			logger.error(e.getMessage());
		}

	}

}
