package com.MyMDentis.MyMDentistComerce.Service;

import com.MyMDentis.MyMDentistComerce.DTO.DTOReserved;
import com.MyMDentis.MyMDentistComerce.Model.Product;
import com.MyMDentis.MyMDentistComerce.Model.Reserved;
import com.MyMDentis.MyMDentistComerce.Model.UserEntity;
import com.MyMDentis.MyMDentistComerce.Repository.ProductRepository;
import com.MyMDentis.MyMDentistComerce.Repository.ReservedRepository;
import com.MyMDentis.MyMDentistComerce.Repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservedService {

    @Autowired
    private ReservedRepository reservedRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserEntityRepository userEntityRepository;

    private final DTOReserved dtoReserved = new DTOReserved();

    public List<Reserved> getAllReserved(){
        return reservedRepository.findAll();
    }

    public List<Reserved> getUserReserved(Long idUserEntity){
        return reservedRepository.findByUserEntity(idUserEntity);
    }

    public List<Reserved> getNotAvailableReserved(){
        return reservedRepository.findByApproved(false);
    }

    public List<Reserved> getAvailableReserved(){
        return reservedRepository.findByApproved(true);
    }

    public DTOReserved saveReserved(DTOReserved dtoReserved){
        Product product = productRepository.findById(dtoReserved.getIdProduct()).orElse(null);
        UserEntity user = userEntityRepository.findById(dtoReserved.getIdUserEntity()).orElse(null);

        Reserved reserved = Reserved.builder()
                .product(product)
                .stockUsed(dtoReserved.getStockUsed())
                .userEntity(user)
                .priceProduct(dtoReserved.getPriceProduct())
                .reservedDate(dtoReserved.getReservedDate())
                .expirationDate(dtoReserved.getExpirationDate())
                .approved(dtoReserved.isApproved())
                .build();

        return dtoReserved.parseDTOReserved(reservedRepository.save(reserved));
    }

}
