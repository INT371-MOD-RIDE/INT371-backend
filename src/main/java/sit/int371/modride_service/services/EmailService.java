package sit.int371.modride_service.services;

import sit.int371.modride_service.dtos.EmailDetail;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetail details);

    // Method
    // To send an email with attachment
//    String sendMailWithAttachment(EmailDetail details);
}
