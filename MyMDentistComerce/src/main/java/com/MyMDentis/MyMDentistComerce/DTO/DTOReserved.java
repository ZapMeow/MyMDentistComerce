package com.MyMDentis.MyMDentistComerce.DTO;

import com.MyMDentis.MyMDentistComerce.Model.Reserved;
import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
public class DTOReserved {

    private Long idReserved;
    private String codeReserved;
    private Long quantityReserved;
    private Long idProduct;
    private Long idUserEntity;
    private boolean activeReserved;

    public DTOReserved parseDTOOrder(Reserved reserved){
        return DTOReserved.builder()
                .idReserved(reserved.getIdReserved())
                .codeReserved(reserved.getCodeReserved())
                .quantityReserved(reserved.getQuantityReserved())
                .idProduct(reserved.getProduct().getIdProduct())
                .idUserEntity(reserved.getUserEntity().getIdUser())
                .build();
    }

}


