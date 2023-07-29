package kr.pwner.fakegram.service;

import kr.pwner.fakegram.exception.ApiException;
import kr.pwner.fakegram.exception.ExceptionEnum;
import kr.pwner.fakegram.repository.AccountRepository;
import kr.pwner.fakegram.repository.UploadRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UploadService {
    private final UploadRepository uploadRepository;
    private final AccountRepository accountRepository;
    private final Path uploadLocation;

    private final static String uploadPath = "/uploads";

    public UploadService(
            AccountRepository accountRepository,
            UploadRepository uploadRepository
    ) throws IOException {
        this.accountRepository = accountRepository;
        this.uploadRepository = uploadRepository;
        this.uploadLocation = Paths.get("."+uploadPath).toAbsolutePath().normalize();

        Files.createDirectories(this.uploadLocation);
    }

    // * e.g. https://user-images.githubusercontent.com/40394063/182028559-6e115af2-e1c4-4fd3-afa0-b2e10501a66f.png
    // * /postid/uuid.extension
    public static String getFileUri(String fileFullName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(uploadPath+"/")
                .path(fileFullName)
                .toUriString();
    }

    public String SaveFile(MultipartFile file){
        // * Sanity check
        if (file.isEmpty())
            throw new ApiException(ExceptionEnum.EMPTY_FILE);
        // * if deploy on production, you have to make more filter
        if (StringUtils.cleanPath(file.getOriginalFilename()).contains(".."))
            throw new ApiException(ExceptionEnum.INVALID_FILE_NAME);

        // * for security and identify the file
        String fileName = UUID.randomUUID() + "." +
                FilenameUtils.getExtension(file.getOriginalFilename());
        Path filePath = this.uploadLocation.resolve(fileName);

        // * Save given file - don't have any attribute
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new ApiException(ExceptionEnum.COULD_NOT_SAVE_THE_FILE);
        }
        return fileName;
    }
}