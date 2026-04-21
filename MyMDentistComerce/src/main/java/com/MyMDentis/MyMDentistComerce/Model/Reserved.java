package com.MyMDentis.MyMDentistComerce.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Entity
@Table(name = "reserved_product")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserved {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserved", nullable = false, unique = true)
    private Long idReserved;

    //-----------------------Product--------------------//
    @ManyToOne(targetEntity = Product.class)
    private Product product;
    @Column(name = "stock_used_product", length = 10, nullable = false, unique = false)
    private Long stockUsed;

    //-----------------------User--------------------//
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity userEntity;

    //-----------------------Reserved Dates--------------------//

    @Column(name = "price_product", length = 10, nullable = false, unique = false)
    private Long priceProduct;
    @Column(name = "date_reserved", nullable = false, unique = false)
    private Date reservedDate;
    @Column(name = "expiration_date", nullable = false, unique = false)
    private Date expirationDate;
    @Column(name = "approved_reserved", nullable = false, unique = false)
    private boolean approved;


}
