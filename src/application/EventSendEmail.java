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
        final String sonic_sync_Email = "sonicsyncevents@gmail.com";
        final String sonic_sync_Password = "SonicSync@2024";

        /* JavaMail Properties is used to set in the session objects and to create the session object.
         * SMTP - Simple Mail Transfer Protocol is used */
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Creates 'Session' object that provides access to JavaMail Protocols
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sonic_sync_Email, sonic_sync_Password);
            }
        });

        ByteArrayOutputStream pdfStream = null;

        // Store the message content in the 'String' variable 'content'
        String content = "Hello " + EventConfirmation.name + ",\n\n" +
                "Thank you for choosing our event booking system. Your booking for the event " + Main.getSelectedEventTitle() +
                " has been confirmed. Please, keep this email with the PDF receipt as proof of your booking.\n"
                + "\nLooking forward to seeing you on " + EventConfirmation.finalDate + ", at "
                + EventConfirmation.finalTime + "!\n\nEnjoy the event!\n\nAdmin\nCEO of Event Booking System";

        try {
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(content);

            pdfStream = new ByteArrayOutputStream();
            writePdf(pdfStream); // Calls the 'writePdf()' method
            byte[] bytes = pdfStream.toByteArray();

            DataSource pdfDataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfPart = new MimeBodyPart();
            pdfPart.setDataHandler(new DataHandler(pdfDataSource));
            pdfPart.setFileName("BookingConfirmation.pdf");

            MimeMultipart emailContent = new MimeMultipart();
            emailContent.addBodyPart(textPart);
            emailContent.addBodyPart(pdfPart);

            Message emailMessage = new MimeMessage(mailSession);
            emailMessage.setFrom(new InternetAddress("eventbooking@gmail.com"));
            emailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            if (type.equals("confirmation")) {
            	emailMessage.setSubject(EventConfirmation.bookingId + " - Booking Confirmation for " + Main.getSelectedEventTitle());
            	emailMessage.setContent(emailContent);
            }

            Transport.send(emailMessage);

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            if (pdfStream != null) {
                try {
                	pdfStream.close();
                	pdfStream = null;
                } catch (Exception ex) {
                }
            }
        }
    }

    // Method 'writePdf' creates the 'BookingConfirmation.pdf' to be emailed to the user
    public static void writePdf(OutputStream outputStream) throws Exception {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 36f, Font.BOLD);
  

        Document pdfDocument = new Document();
        PdfWriter.getInstance(pdfDocument, outputStream);

        pdfDocument.open();

        pdfDocument.addTitle("Booking Confirmation PDF");
        pdfDocument.addSubject("Receipt PDF");
        pdfDocument.addAuthor("Admin");
        pdfDocument.addCreator("Event Booking System");

        Paragraph receiptContent = new Paragraph();
        receiptContent.add(new Chunk("Your Booking Receipt\n\n", titleFont));
        receiptContent.add(new Chunk("Booking ID: " + EventConfirmation.bookingId + "\n" +
                "Event: " + Main.getSelectedEventTitle() + "\n" +
                "Screen: " + EventBooking.screenNum + "\n" +
                "Date: " + EventConfirmation.finalDate + "\n" +
                "Time: " + EventConfirmation.finalTime + "\n" +
                "Tickets: " + EventBooking.adultTickets + " x Adult, " + EventBooking.childTickets + " x Child, " +
                EventBooking.seniorTickets + " x Senior\n" +
                "Seats: " + EventSeatBooking.user_selected_Seats + "\n" +
                "VIP: " + EventConfirmation.vipConference + "\n\n" +
                "Total Payment: $" + String.format("%.2f", EventBooking.total) + "\n\n"));

        BarcodeQRCode qrCode = new BarcodeQRCode("Valid Booking - " + EventConfirmation.bookingId, 1000, 1000, null);
        Image qrCodeImage = qrCode.getImage();
        qrCodeImage.scaleAbsolute(250, 250);
        pdfDocument.add(qrCodeImage);

        Image img = Image.getInstance("./Images/eventLogo.png");
        img.scaleAbsolute(180f, 179.5f);
        img.setAbsolutePosition(4, 22);
        pdfDocument.add(img);

        pdfDocument.close();
    }
}
