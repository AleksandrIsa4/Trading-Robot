package org.Isa4.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

// TODO: объединить с BaseInfoTool
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@SuperBuilder
@Embeddable
public class BaseInfoToolKey implements Serializable {

    @Column(name = "class_code", nullable = false)
    String classCode;

    @Column(name = "sec_code", nullable = false)
    String secCode;
}
