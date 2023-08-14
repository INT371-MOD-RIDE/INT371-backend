package sit.int371.sixpack_overflow_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int371.sixpack_overflow_service.dtos.EmailDetail;
import sit.int371.sixpack_overflow_service.services.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetail details) {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }

//   เรายังไม่ได้กำหนด attachment ที่ model & interface เลยยังใช้ methodนี้ไม่ได้
    // Sending email with attachment
//    @PostMapping("/sendMailWithAttachment")
//    public String sendMailWithAttachment(
//            @RequestBody EmailDetail details)
//    {
//        String status
//                = emailService.sendMailWithAttachment(details);
//        return status;
//    }
}
