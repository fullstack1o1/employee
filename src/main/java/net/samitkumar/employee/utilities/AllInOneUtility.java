package net.samitkumar.employee.utilities;

import org.springframework.http.MediaType;

public class AllInOneUtility {
    public static MediaType mediaTypeByFileExtension(String fileName) {
        return switch (getFileExtension(fileName)) {
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;
            case "pdf" -> MediaType.APPLICATION_PDF;
            // Add more cases for other file extensions as needed
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };

    }

    public static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }
}

