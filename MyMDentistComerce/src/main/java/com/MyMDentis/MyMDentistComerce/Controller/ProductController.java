package com.MyMDentis.MyMDentistComerce.Controller;

import com.MyMDentis.MyMDentistComerce.DTO.DTOProductAdmin;
import com.MyMDentis.MyMDentistComerce.DTO.DTOProductClient;
import com.MyMDentis.MyMDentistComerce.Model.Product;
import com.MyMDentis.MyMDentistComerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/1")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping(path = "/adminPath")
    public ResponseEntity<List<DTOProductAdmin>> getAllAdminProducts(){
        return ResponseEntity.ok(productService.getAllAdminProducts());
    }

    @GetMapping(path = "/clientPath")
    public ResponseEntity<List<DTOProductClient>> getAllClientProducts(){
        return ResponseEntity.ok(productService.getAllClientProducts());
    }

}
