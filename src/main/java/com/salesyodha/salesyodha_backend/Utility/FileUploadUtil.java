package com.salesyodha.salesyodha_backend.Utility;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtil {

    /// =========================================
    /// COMMON IMAGE UPLOAD
    /// =========================================
    public String saveFile(
            MultipartFile file
    ) {

        try {

            /// NULL CHECK
            if (file == null || file.isEmpty()) {
                return null;
            }

            /// CONTENT TYPE CHECK
            String contentType =
                    file.getContentType();

            if (contentType == null ||

                    !(contentType.equals("image/jpeg")
                            || contentType.equals("image/png")
                            || contentType.equals("image/jpg"))) {

                throw new RuntimeException(
                        "Only JPG, JPEG, PNG allowed"
                );
            }

            /// UPLOAD DIRECTORY
            String folder =
                    System.getProperty("user.dir")
                            + "/uploads/";

            java.io.File dir =
                    new java.io.File(folder);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            /// FILE EXTENSION
            String original =
                    file.getOriginalFilename();

            String ext =
                    (original != null &&
                            original.contains("."))

                            ? original.substring(
                            original.lastIndexOf("."))

                            : ".jpg";

            /// UNIQUE FILE NAME
            String fileName =
                    System.currentTimeMillis()
                            + ext;

            /// FINAL PATH
            String filePath =
                    folder + fileName;

            /// SAVE FILE
            file.transferTo(
                    new java.io.File(filePath)
            );

            return "uploads/" + fileName;

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "File upload failed"
            );
        }
    }
}