package org.Isa4.exceptions.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiError {

    List<String> errors;
    HttpStatus status;
    String reason;
    String message;
    String timestamp;
}