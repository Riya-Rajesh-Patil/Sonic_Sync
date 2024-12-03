package application;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;

public class EventSendEmail { // Controller class that sends an email to the user

    // Method 'sendEmail()' creates a booking confirmation email and sends it to the user
    static void sendEmail(String recipient, String type) {

        // Initializes 'username' and 'password' variables that cannot be reassigned
        final String username = "sonicsyncevents@gmail.com";
        final String password = "SonicSync@2024";

        /* JavaMail Properties is used to set in the session objects and to create the session object.
         * SMTP - Simple Mail Transfer Protocol is used */
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Creates 'Session' object that provides access to JavaMail Protocols
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        ByteArrayOutputStream outputStream = null;

        // Store the message content in the 'String' variable 'content'
        String content = "Hello " + EventConfirmation.name + ",\n\n" +
                "Thank you for choosing our event booking system. Your booking for the event " + Main.getSelectedEventTitle() +
                " has been confirmed. Please, keep this email with the PDF receipt as proof of your booking.\n"
                + "\nLooking forward to seeing you on " + EventConfirmation.finalDate + ", at "
                + EventConfirmation.finalTime + "!\n\nEnjoy the event!\n\nAdmin\nCEO of Event Booking System";

        try {
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);

            outputStream = new ByteArrayOutputStream();
            writePdf(outputStream); // Calls the 'writePdf()' method
            byte[] bytes = outputStream.toByteArray();

            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("BookingConfirmation.pdf");

            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sonicsyncevents@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            if (type.equals("confirmation")) {
                message.setSubject(EventConfirmation.bookingId + " - Booking Confirmation for " + Main.getSelectedEventTitle());
                message.setContent(mimeMultipart);
            }

            Transport.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (Exception ex) {
                }
            }
        }
    }

    // Method 'writePdf' creates the 'BookingConfirmation.pdf' to be emailed to the user
    public static void writePdf(OutputStream outputStream) throws Exception {
        Font title = FontFactory.getFont(FontFactory.HELVETICA, 36f, Font.BOLD);
        Font subtitle = FontFactory.getFont(FontFactory.HELVETICA, 16f, Font.BOLD);
        Font italics = FontFactory.getFont(FontFactory.HELVETICA, 12f, Font.ITALIC);

        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        document.addTitle("Booking Confirmation PDF");
        document.addSubject("Receipt PDF");
        document.addAuthor("Admin");
        document.addCreator("Event Booking System");

        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk("Your Booking Receipt\n\n", title));
        paragraph.add(new Chunk("Booking ID: " + EventConfirmation.bookingId + "\n" +
                "Event: " + Main.getSelectedEventTitle() + "\n" +
                "Screen: " + EventBooking.screenNum + "\n" +
                "Date: " + EventConfirmation.finalDate + "\n" +
                "Time: " + EventConfirmation.finalTime + "\n" +
                "Tickets: " + EventBooking.adultTickets + " x Adult, " + EventBooking.childTickets + " x Child, " +
                EventBooking.seniorTickets + " x Senior\n" +
                "Seats: " + EventSeatBooking.userSeats + "\n" +
                "VIP: " + EventConfirmation.vipConf + "\n\n" +
                "Total Payment: $" + String.format("%.2f", EventBooking.total) + "\n\n"));

        BarcodeQRCode barcodeQRCode = new BarcodeQRCode("Valid Booking - " + EventConfirmation.bookingId, 1000, 1000, null);
        Image codeQrImage = barcodeQRCode.getImage();
        codeQrImage.scaleAbsolute(200, 200);
        document.add(codeQrImage);

        Image img = Image.getInstance("./Images/PCR Logo.png");
        img.scaleAbsolute(180f, 179.5f);
        img.setAbsolutePosition(4, 22);
        document.add(img);

        document.close();
    }
}
