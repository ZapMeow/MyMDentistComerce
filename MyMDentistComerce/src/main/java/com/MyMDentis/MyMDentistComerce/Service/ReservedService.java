package com.MyMDentis.MyMDentistComerce.Service;

import com.MyMDentis.MyMDentistComerce.DTO.DTOReservedPetition;
import com.MyMDentis.MyMDentistComerce.Model.Product;
import com.MyMDentis.MyMDentistComerce.Model.Reserved;
import com.MyMDentis.MyMDentistComerce.Model.UserEntity;
import com.MyMDentis.MyMDentistComerce.Repository.ReservedRepository;
import com.MyMDentis.MyMDentistComerce.Repository.ProductRepository;
import com.MyMDentis.MyMDentistComerce.Repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservedService {

    @Autowired
    private ReservedRepository reservedRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private ProductRepository productRepository;


    public List<Reserved> getAllOrders(){
        return reservedRepository.findAll();
    }

    public Reserved findOrderById(Long id){
        return reservedRepository.findById(id).orElseThrow(() -> new RuntimeException("xd"));
    }

    public List<Reserved> findByUser(Long idUserEntity){

        UserEntity user = userEntityRepository.findById(idUserEntity).orElseThrow(
                () -> new RuntimeException("xd1")
        );

        return reservedRepository.findByUserEntity(user);
    }

    public List<Reserved> findActivesOrders(){
        return reservedRepository.findByActiveReserved(true);
    }

    public List<Reserved> findNoActivesOrders(){
        return reservedRepository.findByActiveReserved(false);
    }

    public DTOReservedPetition saveNewOrder(DTOReservedPetition dtoReservedPetition){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication);


        /*Product product = productRepository.findById(dtoReservedPetition.getIdProduct()).orElseThrow(
                () -> new RuntimeException("xd")
        );

        Reserved newOrder = Reserved.builder()
                .codeReserved(dtoReservedPetition.getCodeReserved())
                .quantityReserved(dtoReservedPetition.getQuantityReserved())
                .activeReserved(true)
                .product(product)
                .userEntity(user)
                .build();
        return reservedRepository.save(newOrder);*/
        return DTOReservedPetition.builder().build();
    }
}
