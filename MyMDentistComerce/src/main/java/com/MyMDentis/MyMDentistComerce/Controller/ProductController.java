package com.MyMDentis.MyMDentistComerce.Controller;

import com.MyMDentis.MyMDentistComerce.DTO.DTOProductAdmin;
import com.MyMDentis.MyMDentistComerce.DTO.DTOProductClient;
import com.MyMDentis.MyMDentistComerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MyMDentalCommerce/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping(path = "/adminProducts")
    public ResponseEntity<List<DTOProductAdmin>> getAllAdminProducts(){
        return ResponseEntity.ok(productService.getAllAdminProducts());
    }

    @GetMapping(path = "/clientProducts")
    public ResponseEntity<List<DTOProductClient>> getAllClientProducts(){
        return ResponseEntity.ok(productService.getAllClientProducts());
    }

    @GetMapping(path = "/clientProducts/page/{pageIndex}")
    public ResponseEntity<List<DTOProductAdmin>> getAdminProductsByPage(@PathVariable int pageIndex){
        return ResponseEntity.ok(productService.getProductsAdminByPage(pageIndex));
    }

    @GetMapping(path = "/filterAdminProducts/{filter}")
    public ResponseEntity<List<DTOProductAdmin>> getFilterAdminProducts(@PathVariable String filter){
        return ResponseEntity.ok(productService.filterAdminProducts(filter));
    }

    @GetMapping(path = "/filterClientProducts/{filter}")
    public ResponseEntity<List<DTOProductClient>> getFilterClientProducts(@PathVariable String filter){
        return ResponseEntity.ok(productService.filterClientProducts(filter));
    }

    @PostMapping(path = "/saveProduct")
    public ResponseEntity<DTOProductAdmin> saveNewProduct(@RequestBody DTOProductAdmin dtoProductAdmin){
        return ResponseEntity.ok(productService.saveNewProduct(dtoProductAdmin));

    }

    @PutMapping(path = "/editProduct/{productName}")
    public ResponseEntity<DTOProductAdmin> editProduct(@PathVariable String productName, @RequestBody DTOProductAdmin dtoProductAdmin){
        System.out.println("edit petition");
        return ResponseEntity.ok(productService.editProduct(productName, dtoProductAdmin));
    }

    @DeleteMapping(path = "/deleteProduct/{productName}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productName){
        return ResponseEntity.ok(productService.deleteProduct(productName));
    }



}
