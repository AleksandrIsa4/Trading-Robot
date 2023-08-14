package org.Isa4.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
@Table(name = "information_tool", schema = "public")
public class InformationTool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "sec_code", nullable = false)
    String secCode;

    @Column(name = "class_code", nullable = false)
    String classCode;

    @Column(name = "bid", nullable = false)
    Float bid;

    @Column(name = "biddepth", nullable = false)
    Float biddepth;

    @Column(name = "biddeptht", nullable = false)
    Float biddeptht;

    @Column(name = "offer", nullable = false)
    Float offer;

    @Column(name = "offerdepth", nullable = false)
    Float offerdepth;

    @Column(name = "offerdeptht", nullable = false)
    Float offerdeptht;

    @Column(name = "last", nullable = false)
    Float last;

    @Column(name = "open", nullable = false)
    Float open;

    @Column(name = "created_time")
    LocalDateTime createdTime;
}
