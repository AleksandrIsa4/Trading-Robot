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

    @Column(name = "classCode")
    String classCode;

    @Column(name = "secCode")
    String secCode;

    @Column(name = "clientCode")
    String clientCode;

    @Column(name = "status")
    Status status;

    @Column(name = "operationType")
    String operationType;

    @Column(name = "price")
    Float price;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "quantityComplete")
    Long quantityComplete;
}
