package org.Isa4.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "position_instruments", schema = "public")
@SuperBuilder
public class PositionInstrument {

    @EmbeddedId
    BaseInfoToolKey pk;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "average_price")
    Float averagePrice;

    @Column(name = "lot_size", nullable = false)
    Long lotSize;

    @Column(name = "sec_price_step", nullable = false)
    Float secPriceStep;
}
