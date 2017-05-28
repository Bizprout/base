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
	
	// Sender's email ID needs to be mentioned	
	public static final String from = "epacc_hrms@bizprout.com";
	
	// Assuming you are sending email from localhost
	public static final String host = "103.224.23.175";

	public void sendEmail(String toaddress, String user, String password){
		
		logger.debug("inside sendEmail method ......"+this.getClass());

		// Recipient's email ID needs to be mentioned.
		String to = toaddress;

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
			logger.error(e.getMessage()+"..."+this.getClass());
		}

	}
	
	public void usernameChangeEmail(String user, String password){
		
		logger.debug("inside usernameChangeEmail method ......"+this.getClass());

		// Recipient's email ID needs to be mentioned.
		String to = user;

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
			message.setSubject("Perpetuity Password Reset");

			// Now set the actual message
			message.setContent("Dear "+ user + ",<br><br>Your Username has been Changed by Superadmin on Perpetuity<br> Please use the below Username and Password for Perpetuity Login: <br><br> "
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
			logger.error(e.getMessage()+"..."+this.getClass());
		}

	}
	
	public void sendForgotPasswordEmail(String user, String password){
		
		logger.debug("inside usernameChangeEmail method ......"+this.getClass());

		// Recipient's email ID needs to be mentioned.
		String to = user;

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
			message.setSubject("Perpetuity Password Reset");

			// Now set the actual message
			message.setContent("Dear "+ user + ",<br><br>We Received a Request to Change Your Password on Perpetuity and we changed it for you.<br> Your can use the below Password for Perpetuity Login: <br><br> "
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
			logger.error(e.getMessage()+"..."+this.getClass());
		}

	}

}
