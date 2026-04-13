package com.MyMDentis.MyMDentistComerce.Controller;

import com.MyMDentis.MyMDentistComerce.DTO.DTOProductAdmin;
import com.MyMDentis.MyMDentistComerce.DTO.DTOProductClient;
import com.MyMDentis.MyMDentistComerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/MyMDentalCommerce/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    Logger log = Logger.getLogger("Debug log");


    @GetMapping(path = "/adminProducts")
    public ResponseEntity<List<DTOProductAdmin>> getAllAdminProducts(){
        log.info("getting admin products");
        return ResponseEntity.ok(productService.getAllAdminProducts());
    }

    @GetMapping(path = "/clientProducts")
    public ResponseEntity<List<DTOProductClient>> getAllClientProducts(){
        log.info("getting client products");
        return ResponseEntity.ok(productService.getAllClientProducts());
    }

    @GetMapping(path = "/adminProducts/page/{pageIndex}")
    public ResponseEntity<List<DTOProductAdmin>> getAdminProductsByPage(@PathVariable int pageIndex){
        log.info("getting admin products by page " + pageIndex);
        return ResponseEntity.ok(productService.getProductsAdminByPage(pageIndex));
    }

    @GetMapping(path = "/filterAdminProducts/{filter}")
    public ResponseEntity<List<DTOProductAdmin>> getFilterAdminProducts(@PathVariable String filter){
        log.info("getting filter admin products by " + filter);
        return ResponseEntity.ok(productService.filterAdminProducts(filter));
    }

    @GetMapping(path = "/filterClientProducts/{filter}")
    public ResponseEntity<List<DTOProductClient>> getFilterClientProducts(@PathVariable String filter){
        log.info("getting filter client products by " + filter);
        return ResponseEntity.ok(productService.filterClientProducts(filter));
    }

    @PostMapping(path = "/saveProduct")
    public ResponseEntity<DTOProductAdmin> saveNewProduct(@RequestBody DTOProductAdmin dtoProductAdmin){
        log.info("saving new product: " + dtoProductAdmin.toString());
        return ResponseEntity.ok(productService.saveNewProduct(dtoProductAdmin));
    }

    @PutMapping(path = "/editProduct/{productName}")
    public ResponseEntity<DTOProductAdmin> editProduct(@PathVariable String productName, @RequestBody DTOProductAdmin dtoProductAdmin){
        log.info("editing a product with name " + productName);
        log.info("the new product is " + dtoProductAdmin.toString());
        return ResponseEntity.ok(productService.editProduct(productName, dtoProductAdmin));
    }

    @DeleteMapping(path = "/deleteProduct/{productName}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productName){
        System.out.println("deleting product " + productName);
        return new ResponseEntity<>("Producto eliminado", HttpStatus.ACCEPTED);
    }



}
