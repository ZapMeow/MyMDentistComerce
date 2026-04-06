package com.MyMDentis.MyMDentistComerce.Service;

import com.MyMDentis.MyMDentistComerce.DTO.DTOProductAdmin;
import com.MyMDentis.MyMDentistComerce.DTO.DTOProductClient;
import com.MyMDentis.MyMDentistComerce.Model.Product;
import com.MyMDentis.MyMDentistComerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final DTOProductAdmin dtoProductAdmin = new DTOProductAdmin();
    private final DTOProductClient dtoProductClient = new DTOProductClient();

    @Transactional
    public List<DTOProductAdmin> getAllAdminProducts(){

        List<Product> products = productRepository.findAll();
        List<DTOProductAdmin> dtoProductAdmins = new ArrayList<>();
        for (Product product : products){
            dtoProductAdmins.add(dtoProductAdmin.parseDTOProductAdmin(product));
        }

        return dtoProductAdmins;
    }

    @Transactional
    public List<DTOProductClient> getAllClientProducts(){

        List<Product> products = productRepository.findAll();
        List<DTOProductClient> dtoProductsClient = new ArrayList<>();
        for (Product product : products){
            dtoProductsClient.add(dtoProductClient.parseDTOProductClient(product));
        }
        return dtoProductsClient;

    }

}
