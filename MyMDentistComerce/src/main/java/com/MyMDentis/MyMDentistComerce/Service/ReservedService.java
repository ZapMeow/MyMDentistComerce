package com.MyMDentis.MyMDentistComerce.Service;

import com.MyMDentis.MyMDentistComerce.DTO.DTOReserved;
import com.MyMDentis.MyMDentistComerce.Model.Product;
import com.MyMDentis.MyMDentistComerce.Model.Reserved;
import com.MyMDentis.MyMDentistComerce.Model.UserEntity;
import com.MyMDentis.MyMDentistComerce.Repository.ReservedRepository;
import com.MyMDentis.MyMDentistComerce.Repository.ProductRepository;
import com.MyMDentis.MyMDentistComerce.Repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Reserved saveNewOrder(DTOReserved order){
        UserEntity user = userEntityRepository.findById(order.getIdUserEntity()).orElseThrow(
                () -> new RuntimeException("xdddddd")
        );
        Product product = productRepository.findById(order.getIdProduct()).orElseThrow(
                () -> new RuntimeException("xd")
        );

        Reserved newOrder = Reserved.builder()
                .codeReserved(order.getCodeReserved())
                .quantityReserved(order.getQuantityReserved())
                .activeReserved(order.isActiveReserved())
                .product(product)
                .userEntity(user)
                .build();
        return reservedRepository.save(newOrder);
    }
}
