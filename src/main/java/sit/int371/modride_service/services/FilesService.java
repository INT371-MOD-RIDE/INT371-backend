package sit.int371.modride_service.services;

import java.io.File;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesService {

    @Value("${upload_path}")
    public String upload_uri;

    public boolean checkTypeFileUpload(String filext) throws Exception {
        switch (filext.toLowerCase()) {
            case "jpg":
                return true;
            case "jpeg":
                return true;
            case "png":
                return true;
            case "zip":
                return true;
            case "xls":
                return true;
            case "xlsx":
                return true;
            case "xlsm":
                return true;
            case "doc":
                return true;
            case "docx":
                return true;
            case "pdf":
                return true;
            case "result":
                return true;
            case "tar":
                return true;
            default:
                return false;
        }
    }

    public void createAttachmentContent(String userId, MultipartFile file)
            throws Exception {
        try {
            String[] nameSplit = file.getOriginalFilename().split(Pattern.quote("."));
            String fileName = "";
            for (int i = 0; i < nameSplit.length; i++) {
                if (i == (nameSplit.length - 1)) {
                    fileName += ".";
                    fileName += nameSplit[i];
                } else {
                    fileName += nameSplit[i];
                }
            }
            String path = "";

            // path = attachmentBean.getAttachment_dir();

            // attachmentBean.setAttachment_name(fileName);
            // attachmentBean.setAttachment_path(path + fileName);
            // System.out.println("after-bean: " + attachmentBean);
            fileName = 'u' + userId + "_" + fileName; // setfileNameWithUserId
            File uploadDir = new File(upload_uri + path);
            File target = new File(upload_uri + path + fileName);
            System.out.println("target:::::> " + target);
            System.out.println("targettttttt: " + target);
            if (uploadDir.mkdir()) {
                file.transferTo(target);
                System.out.println("if : " + target);
            } else {
                file.transferTo(target);
                System.out.println("else : " + target);
            }

        } catch (Exception e) {
            System.out.println("Got an exception. {}" + e.getMessage());
            throw e;
        }
    }
}
