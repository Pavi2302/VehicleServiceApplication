package Req;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {
    public static void sendEmail(String receiver, String message, String subject) throws Exception {
        System.out.println("Start");
        Properties props = new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");

        Session sess = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bikecarebatch23@gmail.com","parvathynathan1");
            }
        });
        Message mess = prepareMessage(sess,receiver,message,subject);
        Transport.send(mess);
        System.out.println("Success");
    }
    private static Message prepareMessage(Session sess, String receiver,String message, String subject)  {
        Message mess = new MimeMessage(sess);
        try {
            mess.setFrom(new InternetAddress("bikecarebatch23@gmail.com"));
            mess.setRecipient(Message.RecipientType.TO,new InternetAddress(receiver));
            mess.setSubject(subject);
            mess.setText(message);
            return mess;
        }catch (Exception E){
            E.printStackTrace();
        }
        return null;
    }
}
