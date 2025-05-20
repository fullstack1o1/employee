package net.samitkumar.employee.utilities;

import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;

import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public class EmployeeUtility {
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

    public static String getDocumentsType(FilePart filePart) {
        return (nonNull(filePart) && !isEmpty(filePart.headers())) ? filePart.headers().getContentType().toString() : null;
    }
}

