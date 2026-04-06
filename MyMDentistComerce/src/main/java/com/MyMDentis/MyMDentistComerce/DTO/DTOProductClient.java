package com.MyMDentis.MyMDentistComerce.DTO;

import com.MyMDentis.MyMDentistComerce.Model.Product;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTOProductClient {

    private String productName;
    private String descriptionProduct;
    private Long priceProduct;
    private String nameDepartment;

    public DTOProductClient parseDTOProductClient(Product product){
        return DTOProductClient.builder()
                .productName(product.getProductName())
                .descriptionProduct(product.getDescriptionProduct())
                .priceProduct(product.getPriceProduct())
                .nameDepartment(product.getDepartment().getNameDepartment())
                .build();
    }
}
