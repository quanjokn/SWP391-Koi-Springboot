package SWP391.Fall24.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setFrom("koifarmshopswp391@gmail.com");
        mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(subject);
        mimeMessage.setContent(text, "text/html;charset=utf-8");
        mailSender.send(mimeMessage);
    }

    public String subjectOrder(String fullName) {
        return "Hi, " + fullName + ", thanks for your order from Shoe For Men Shop!";
    }

    public String subjectForgotPass() {
        return "Support forgot password";
    }

    public String messageDecline(String reason, int orderID, String name) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Order Declined Notification</title>\n" +
                "    <style>\n" +
                "        /* General Reset */\n" +
                "        body, table, td, a { \n" +
                "            text-size-adjust: 100%;\n" +
                "            font-family: Arial, sans-serif;\n" +
                "        }\n" +
                "        table, td {\n" +
                "            border-collapse: collapse;\n" +
                "        }\n" +
                "        img {\n" +
                "            line-height: 100%;\n" +
                "            outline: none;\n" +
                "            text-decoration: none;\n" +
                "            display: block;\n" +
                "        }\n" +
                "\n" +
                "        /* Container */\n" +
                "        .email-container {\n" +
                "            width: 100%;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .email-content {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        /* Header */\n" +
                "        .email-header {\n" +
                "            background-color: #e53e3e;\n" +
                "            color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .email-header h1 {\n" +
                "            margin: 0;\n" +
                "            font-size: 24px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        /* Body */\n" +
                "        .email-body {\n" +
                "            padding: 20px 30px;\n" +
                "        }\n" +
                "        .email-body h2 {\n" +
                "            color: #333333;\n" +
                "            font-size: 20px;\n" +
                "            font-weight: 600;\n" +
                "        }\n" +
                "        .email-body p {\n" +
                "            color: #555555;\n" +
                "            line-height: 1.6;\n" +
                "            font-size: 16px;\n" +
                "        }\n" +
                "\n" +
                "        /* Rejection Reason */\n" +
                "        .reason-box {\n" +
                "            background-color: #ffe8e8;\n" +
                "            border-left: 4px solid #e53e3e;\n" +
                "            padding: 15px;\n" +
                "            margin-top: 20px;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "        .reason-box p {\n" +
                "            color: #e53e3e;\n" +
                "            font-size: 16px;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "\n" +
                "        /* Footer */\n" +
                "        .email-footer {\n" +
                "            background-color: #f4f4f7;\n" +
                "            color: #888888;\n" +
                "            text-align: center;\n" +
                "            padding: 15px 30px;\n" +
                "            font-size: 14px;\n" +
                "        }\n" +
                "        .email-footer p {\n" +
                "            margin: 5px 0;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"email-container\">\n" +
                "        <!-- Email Content -->\n" +
                "        <div class=\"email-content\">\n" +
                "            \n" +
                "            <!-- Header -->\n" +
                "            <div class=\"email-header\">\n" +
                "                <h1>Order Update</h1>\n" +
                "            </div>\n" +
                "            \n" +
                "            <!-- Body -->\n" +
                "            <div class=\"email-body\">\n" +
                "                <h2>Dear "+ name +",</h2>\n" +
                "                <p>\n" +
                "                    We regret to inform you that your order (Order No. <strong>"+orderID+"</strong>) has been declined. We understand that this may be disappointing, and we want to assure you that we’re here to help.\n" +
                "                </p>\n" +
                "\n" +
                "                <!-- Reason for Rejection -->\n" +
                "                <div class=\"reason-box\">\n" +
                "                    <p><strong>Reason for Decline:</strong> "+reason+"</p>\n" +
                "                </div>\n" +
                "\n" +
                "                <p>If you have any questions or need further assistance, please don’t hesitate to reach out to our support team.</p>\n" +
                "            </div>\n" +
                "\n" +
                "            <!-- Footer -->\n" +
                "            <div class=\"email-footer\">\n" +
                "                <p>Thank you for choosing us,</p>\n" +
                "                <p><strong>Koi Farm Shop</strong></p>\n" +
                "                <p>Email: support@koifarmshop.com | Phone: +123-456-7890</p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    public String messageOrder(LocalDate date, double totalMoney, String address, String status) {
        String step1 = status.equals("Đợi Xác Nhận") || status.equals("Đang Chuẩn Bị") || status.equals("Đang Vận Chuyển") || status.equals("Đã Hoàn Thành") ? "completed" : "";
        String step2 = status.equals("Đang Chuẩn Bị") || status.equals("Đang Vận Chuyển") || status.equals("Đã Hoàn Thành") ? "completed" : "";
        String step3 = status.equals("Đang Vận Chuyển") || status.equals("Đã Hoàn Thành") ? "completed" : "";
        String step4 = status.equals("Đã Hoàn Thành") ? "completed" : "";

        return "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "  <meta charset=\"utf-8\">\n"
                + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "  <style>\n"
                + "    body {\n"
                + "      font-family: Arial, sans-serif;\n"
                + "      background-color: #f9f9f9;\n"
                + "      margin: 0;\n"
                + "      padding: 0;\n"
                + "      color: #333;\n"
                + "    }\n"
                + "    .email-container {\n"
                + "      max-width: 600px;\n"
                + "      margin: 20px auto;\n"
                + "      background-color: #ffffff;\n"
                + "      border-radius: 8px;\n"
                + "      overflow: hidden;\n"
                + "      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);\n"
                + "    }\n"
                + "    .header {\n"
                + "      background-color: #3498db;\n"
                + "      color: #ffffff;\n"
                + "      padding: 20px;\n"
                + "      text-align: center;\n"
                + "      font-size: 24px;\n"
                + "    }\n"
                + "    .content {\n"
                + "      padding: 20px;\n"
                + "      text-align: center;\n"
                + "      font-size: 16px;\n"
                + "    }\n"
                + "    .progress-container {\n"
                + "      display: flex;\n"
                + "      justify-content: space-between;\n"
                + "      align-items: center;\n"
                + "      padding: 20px 0;\n"
                + "    }\n"
                + "    .progress-step {\n"
                + "      text-align: center;\n"
                + "      flex: 1;\n"
                + "      padding: 0 10px;\n"
                + "      position: relative;\n"
                + "    }\n"
                + "    .progress-step::after {\n"
                + "      content: '';\n"
                + "      position: absolute;\n"
                + "      top: 20px;\n"
                + "      left: 50%;\n"
                + "      transform: translateX(-50%);\n"
                + "      width: 80%;\n"
                + "      height: 4px;\n"
                + "      background-color: #ddd;\n"
                + "      z-index: -1;\n"
                + "    }\n"
                + "    .progress-step.completed::after {\n"
                + "      background-color: #28a745;\n"
                + "    }\n"
                + "    .progress-icon {\n" +
                "    width: 40px;\n" +
                "    height: 40px;\n" +
                "    border-radius: 50%;\n" +
                "    background-color: #ddd;\n" +
                "    display: flex;\n" +
                "    align-items: center;\n" +
                "    justify-content: center;\n" +
                "    color: #fff;\n" +
                "    font-size: 18px;\n" +
                "    margin: 0 auto 10px;\n" +
                "    padding: auto;\n"+
                "    line-height: 1; /* Ensures the icon stays centered vertically */\n" +
                "}\n"
                + "    .progress-step.completed .progress-icon {\n"
                + "      background-color: #28a745;\n"
                + "    }\n"
                + "    .progress-text {\n"
                + "      font-weight: 600;\n"
                + "      color: #555;\n"
                + "      margin-top: 5px;\n"
                + "    }\n"
                + "    .footer {\n"
                + "      background-color: #3498db;\n"
                + "      color: #ffffff;\n"
                + "      padding: 15px;\n"
                + "      text-align: center;\n"
                + "      font-size: 14px;\n"
                + "      border-radius: 0 0 8px 8px;\n"
                + "    }\n"
                + "    .footer-button {\n"
                + "      display: inline-block;\n"
                + "      padding: 10px 20px;\n"
                + "      color: #ffffff;\n"
                + "      background-color: #3498db;\n"
                + "      border-radius: 5px;\n"
                + "      text-decoration: none;\n"
                + "      font-weight: bold;\n"
                + "    }\n"
                + "  </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <div class=\"email-container\">\n"
                + "    <div class=\"header\">\n"
                + "      <h1>Koi Farm Shop</h1>\n"
                + "      <h2>Your order is on its way!</h2>\n"
                + "    </div>\n"
                + "    <div class=\"content\">\n"
                    + "      <h3>ORDER SUMMARY</h3>\n"
                + "      <p>Order Date: " + date + "</p>\n"
                + "      <p>Order Total: VND" + totalMoney + "</p>\n"
                + "      <p>Shipping address: " + address + "</p>\n"
                    + "      <h3>ORDER PROCESS</h3>\n"
                + "      <div class=\"progress-container\">\n"
                + "        <div class=\"progress-step " + step1 + "\">\n"
                + "          <div class=\"progress-icon\">✔</div>\n"
                + "          <div class=\"progress-text\">Đợi Xác Nhận</div>\n"
                + "        </div>\n"
                + "        <div class=\"progress-step " + step2 + "\">\n"
                + "          <div class=\"progress-icon\">✔</div>\n"
                + "          <div class=\"progress-text\">Đang Chuẩn Bị</div>\n"
                + "        </div>\n"
                + "        <div class=\"progress-step " + step3 + "\">\n"
                + "          <div class=\"progress-icon\">✔</div>\n"
                + "          <div class=\"progress-text\">Đang Vận Chuyển</div>\n"
                + "        </div>\n"
                + "        <div class=\"progress-step " + step4 + "\">\n"
                + "          <div class=\"progress-icon\">✔</div>\n"
                + "          <div class=\"progress-text\">Đã Hoàn Thành</div>\n"
                + "        </div>\n"
                + "      </div>\n"
                + "      <p>If you have any questions, contact us here or call us on 0773363666.</p>\n"
                + "      <p>We are here to help!</p>\n"
                + "    </div>\n"
                + "    <div class=\"footer\">\n"
                + "      <a href=\"#\" class=\"footer-button\">Thank you for placing your order!</a>\n"
                + "    </div>\n"
                + "  </div>\n"
                + "</body>\n"
                + "</html>";
    }





    public String messageForgotPass(String name, int code) {
        return "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Password Reset Code</title>\n"
                + "</head>\n"
                + "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;\">\n"
                + "\n"
                + "    <table style=\"width: 100%; max-width: 600px; margin: 20px auto; background-color: #ffffff; border-collapse: collapse;\">\n"
                + "        <tr>\n"
                + "            <td style=\"padding: 20px; text-align: center; background-color: #4CAF50; color: #ffffff; font-size: 24px;\">\n"
                + "                Password Reset Code\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td style=\"padding: 20px;\">\n"
                + "                <p>Hello " + name + ",</p>\n"
                + "                <p>You have requested to reset your password. Please use the following code to reset your password:</p>\n"
                + "                <p style=\"text-align:center;font-size: 28px; font-weight: bold; color: #4CAF50;\">" + code + "</p>\n"
                + "                <p>If you didn't request this, you can safely ignore this email.</p>\n"
                + "                <p>Thank you!</p>\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td style=\"padding: 20px; text-align: center; background-color: #4CAF50; color: #ffffff;\">\n"
                + "                &copy; 2024 Koi Paradise\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "    </table>\n"
                + "\n"
                + "</body>\n"
                + "</html>";
    }
}
