package org.Isa4.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@SuperBuilder
@Embeddable
@MappedSuperclass
public class BaseInfoTool implements Serializable {

    @Column(name = "class_code", nullable = false)
    String classCode;

    @Column(name = "sec_code", nullable = false)
    String secCode;
}
