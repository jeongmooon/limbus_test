package com.example.test_web.global.util;

import com.example.test_web.global.exception.BizException;
import com.example.test_web.global.exception.ErrorCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUtil {
    private static final String uploadDir = "C:\\Users\\jeongmoon\\Desktop\\노말틱해킹\\test-web\\test-web\\src\\main\\resources\\static\\image\\prisoner\\";

    public static String saveImage(MultipartFile image, String SinnerDir, String fileName) throws IOException {
        if(image.isEmpty()){
            throw new BizException(ErrorCode.NOT_IMAGE_FILE);
        }

        Path filePath = Paths.get(uploadDir + SinnerDir + "\\" + fileName + ".webp");

        // 파일을 지정된 경로에 저장
        Files.write(filePath, image.getBytes());

        return "success";
    }
}
