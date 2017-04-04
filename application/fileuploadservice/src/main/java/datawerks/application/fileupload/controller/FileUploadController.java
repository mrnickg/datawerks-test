package datawerks.application.fileupload.controller;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import datawerks.application.fileupload.Application;
import datawerks.common.logging.Log;
import datawerks.common.messaging.MessagingService;
import datawerks.common.messaging.mq.messages.TransformMessage;
import datawerks.common.storage.FileSystemStorageService;
import datawerks.common.storage.StorageException;
import datawerks.common.storage.StorageFileNotFoundException;

@Controller
public class FileUploadController {

    private final FileSystemStorageService storageService;
    private final MessagingService messageService;
    
    @Autowired
    public FileUploadController(FileSystemStorageService storageService, MessagingService messageService) {
        this.storageService = storageService;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        return "uploadForm";
    }
    
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

    	Log.info("File Upload Controller processing file", this);
        Path p = storageService.store(file);
        if (p != null) {
        	redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
            TransformMessage msg = new TransformMessage();
            msg.setFileLocation(p.toUri().toString());
            messageService.publish(Application.queueName, msg);
            Log.info("File Upload Controller processed file and added message to MQ", this);
        }
        else {
        	redirectAttributes.addFlashAttribute("message",
                    "Unable to store file " + file.getOriginalFilename() + "!");
        	Log.error("Unable to store file", this);
        }
        return "redirect:/";
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}