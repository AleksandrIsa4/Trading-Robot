package org.Isa4.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.Isa4.model.enumeration.Status;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "transactions", schema = "public")
@Builder
public class Transaction {

    @Id
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "class_code")
    String classCode;

    @Column(name = "sec_code")
    String secCode;

    @Column(name = "client_code")
    String clientCode;

    @Column(name = "status")
    Status status;

    @Column(name = "operation_type")
    String operationType;

    @Column(name = "price")
    Float price;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "quantity_complete")
    Long quantityComplete;
}
