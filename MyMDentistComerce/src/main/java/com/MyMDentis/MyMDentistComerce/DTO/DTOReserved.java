package com.MyMDentis.MyMDentistComerce.DTO;

import com.MyMDentis.MyMDentistComerce.Model.Reserved;
import lombok.*;

import java.util.Date;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTOReserved {

    private Long idReserved;

    //-----------------------Product--------------------//
    private Long idProduct;
    private Long stockUsed;

    //-----------------------User--------------------//
    private Long idUserEntity;

    //-----------------------Reserved Dates--------------------//

    private Long priceProduct;
    private Date reservedDate;
    private Date expirationDate;
    private boolean approved;



    public DTOReserved parseDTOReserved(Reserved reserved){
        return DTOReserved.builder()
                .idReserved(reserved.getIdReserved())
                .idProduct(reserved.getProduct().getIdProduct())
                .stockUsed(reserved.getStockUsed())
                .idUserEntity(reserved.getUserEntity().getIdUser())
                .priceProduct(reserved.getPriceProduct())
                .reservedDate(reserved.getReservedDate())
                .expirationDate(reserved.getExpirationDate())
                .approved(reserved.isApproved())
                .build();
    }
}
