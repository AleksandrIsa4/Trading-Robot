package org.Isa4.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "position_instruments", schema = "public")
@Builder
public class PositionInstrument {

    @Column(name = "class_code")
    String classCode;

    @Id
    @Column(name = "sec_code", nullable = false)
    String secCode;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "average_price")
    Float averagePrice;

    @Column(name = "lot_size", nullable = false)
    Long lotSize;

    @Column(name = "sec_price_step", nullable = false)
    Float secPriceStep;
}
