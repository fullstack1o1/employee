package net.samitkumar.employee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import net.samitkumar.employee.utilities.EmployeeUtility;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Table("employee_documents")
public record EmployeeDocument(
        @Id Integer documentId,
        String documentName,
        String documentType,
        //@ToString.Exclude
        //@JsonIgnore
        byte[] documentContent,
        Integer employeeId) {

    public static EmployeeDocument prepareFileType(FilePart filePart) {
        return new EmployeeDocument(
                null,
                filePart.filename(),
                EmployeeUtility.getDocumentsType(filePart),
                null,
                1
        );
    }
}
