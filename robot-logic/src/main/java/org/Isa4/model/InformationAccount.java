package org.Isa4.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "account", schema = "public")
@Builder
public class InformationAccount {

    @Id
    @Column(name = "account", nullable = false)
    String account;

    @Column(name = "client_code")
    String clientCode;

    @Column(name = "class_code", nullable = false)
    String classCode;
}
