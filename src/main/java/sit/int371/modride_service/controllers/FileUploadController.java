package sit.int371.modride_service.controllers;
//package sit.int371.modride_service.controllers;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import sit.int371.modride_service.entities.File;
//import sit.int371.modride_service.payload.Response;
//import sit.int371.modride_service.services.DBFileService;
//
//
//@RestController
//@RequestMapping("/api/fileUpload")
//public class FileUploadController {
//
//    @Autowired
//    private DBFileService dbFileService;
//
////    public FileUploadController(DBFileService dbFileService) {
////        this.dbFileService = dbFileService;
////    }
//
//    @PostMapping("")
//    public Response uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
//        File fileName = dbFileService.storeFile(file);
//
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/fileUpload/")
//                .path(fileName.getFileName())
//                .toUriString();
//
//        return new Response(fileName.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
//
//    }
//
//
//}
