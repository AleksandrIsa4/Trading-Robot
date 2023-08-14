package org.Isa4.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
@Table(name = "trade_akzii", schema = "public")
@Builder
public class TradeAkzii {

    @Id
    @Column(name = "sec_code", nullable = false)
    String secCode;

    @Column(name = "buy_price", nullable = false)
    float buyPrice;

    @Column(name = "quantity_first", nullable = false)
    long quantityFirst;

    @Column(name = "quantity_second", nullable = false)
    long quantitySecond;

    @OneToOne()
    @JoinColumn(name = "position_instrument_sec_code")
    PositionInstrument positionInstrument;
}