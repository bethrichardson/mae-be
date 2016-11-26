package edu.maebe.utilities;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public final class SmtpMailSender {

    public static void SendText(String provider, String phone, String firstName, Date lastUpdate) {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String lastUpdateAsString = formatter.format(lastUpdate);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS


        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("maeApp@gmail.com", "PASSWORD_GOES_HERE");
            }
        };

        Session session = Session.getInstance(props, auth);

        sendEmail(session, "maeApp@gmail.com", phone + "@" + provider, "We've missed you!", "Hey " + firstName +
                "! We haven't had an update from you since " + lastUpdateAsString +
                ", want to let us know how things are going?");
    }

    private static void sendEmail(Session session, String fromEmail, String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));

            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace(); //should we skip this step? Do we have logging?
        }
    }
}
