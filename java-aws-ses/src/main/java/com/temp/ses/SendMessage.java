package com.temp.ses;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SesException;


public class SendMessage {

    public static void main(String[] args) throws IOException {

        String sender = args[0];
        String recipient = args[1];
        String subject = args[2];

        Region region = Region.AP_SOUTH_1;
        SesClient client = SesClient.builder()
                .region(region)
                .build();

        // The email body for non-HTML email clients
        String bodyText = "Hello,\r\n" + "See the list of customers. ";

        // The HTML body of the email
        String bodyHTML = "<html>" + "<head></head>" + "<body>" + "<h1>Hello!</h1>"
                + "<p> See the list of customers.</p>" + "</body>" + "</html>";

    try {
         send(client, sender, recipient, subject, bodyText, bodyHTML);
         client.close();
         System.out.println("Done");

    } catch (IOException | MessagingException e) {
        e.getStackTrace();
    }
  }

    public static void send(SesClient client,
                            String sender,
                            String recipient,
                            String subject,
                            String bodyText,
                            String bodyHTML
                            ) throws AddressException, MessagingException, IOException {

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);

        // subject, from and to lines
        message.setSubject(subject, "UTF-8");
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

        // multipart child container
        MimeMultipart msgBody = new MimeMultipart("alternative");

        // Wrapper for the HTML and text parts
        MimeBodyPart wrap = new MimeBodyPart();

        // Text part
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(bodyText, "text/plain; charset=UTF-8");

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(bodyHTML, "text/html; charset=UTF-8");

        msgBody.addBodyPart(textPart);
        msgBody.addBodyPart(htmlPart);

        // Child container to the wrapper object
        wrap.setContent(msgBody);

        // multipart/mixed parent container
        MimeMultipart msg = new MimeMultipart("mixed");

        // parent container to the message
        message.setContent(msg);

        // multipart part to the message
        msg.addBodyPart(wrap);

        try {
            System.out.println("Attempting to send an email through Amazon SES ");

             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             message.writeTo(outputStream);
             ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

             byte[] arr = new byte[buf.remaining()];
             buf.get(arr);

             SdkBytes data = SdkBytes.fromByteArray(arr);
             RawMessage rawMessage = RawMessage.builder()
                    .data(data)
                    .build();

             SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage)
                    .build();

             client.sendRawEmail(rawEmailRequest);

         } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
         }

    }
}
