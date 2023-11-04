package sit.int371.modride_service.controllers;
//package sit.int371.modride_service.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import sit.int371.modride_service.entities.File;
////ของที่ร่วมกับ mysql
//import sit.int371.modride_service.payload.Response;
//import sit.int371.modride_service.repositories.FileRepository;
//import sit.int371.modride_service.services.DBFileService;
//import sit.int371.modride_service.services.FileService;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@RequestMapping("/api")
//public class FileDownloadController {
//
//    @Autowired
//    private DBFileService dbFileService;
//
//    @Autowired
//    private FileRepository fileRepository;
//
//    // test post man ใส่ /downloadFile/<fileId>
//    @GetMapping("/downloadFile/{fileId:.+}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletRequest request) {
//        // Load file as Resource
//        File file = dbFileService.getFileById(fileId);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(file.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
//                .body(new ByteArrayResource(file.getData()));
//    }
//
//    //    Delete
//    @DeleteMapping("/deleteFile/{fileId}")
//    public void deleteFile(@PathVariable String fileId) {
//        fileRepository.findById(fileId).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, fileId + " does not exist !"));
//        fileRepository.deleteById(fileId);
//    }
//
//
//
//
//
// }
