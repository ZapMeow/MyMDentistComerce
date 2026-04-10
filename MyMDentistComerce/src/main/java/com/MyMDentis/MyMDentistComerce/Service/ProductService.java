package com.MyMDentis.MyMDentistComerce.Service;

import com.MyMDentis.MyMDentistComerce.DTO.DTOProductAdmin;
import com.MyMDentis.MyMDentistComerce.DTO.DTOProductClient;
import com.MyMDentis.MyMDentistComerce.Model.Department;
import com.MyMDentis.MyMDentistComerce.Model.Product;
import com.MyMDentis.MyMDentistComerce.Repository.DepartmentRepository;
import com.MyMDentis.MyMDentistComerce.Repository.ProductRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

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

    @Transactional
    public List<DTOProductAdmin> getProductsAdminByPage(int index){

        List<DTOProductAdmin> products = new ArrayList<>();

        //Spring use 0-based index and 20 is the pageSize
        Pageable pageable = PageRequest.of(index-1, 20);
        Page<Product> productPage = productRepository.findAll(pageable);

        for (Product product : productPage){
            products.add(dtoProductAdmin.parseDTOProductAdmin(product));

        }

        return products;
    }

    @Transactional
    public DTOProductAdmin saveNewProduct(DTOProductAdmin dtoProductAdmin) {
        Product product = new Product();
        Department department = departmentRepository.findByNameDepartment(dtoProductAdmin.getNameDepartment()).orElse(null);
        product.setCodeProduct(dtoProductAdmin.getCodeProduct());
        product.setCriticProduct(dtoProductAdmin.getCriticProduct());
        product.setCostPriceProduct(dtoProductAdmin.getCostPriceProduct());
        product.setDescriptionProduct(dtoProductAdmin.getDescriptionProduct());
        product.setPriceProduct(dtoProductAdmin.getPriceProduct());
        product.setProductName(dtoProductAdmin.getProductName());
        product.setStockProduct(dtoProductAdmin.getStockProduct());
        product.setDepartment(department);

        return dtoProductAdmin.parseDTOProductAdmin(productRepository.save(product));
    }

    public DTOProductAdmin editProduct(String productName, DTOProductAdmin dtoProductAdmin) {
        System.out.println("editing product");

        Product product = productRepository.findByProductName(productName).orElse(null);
        assert product != null;
        Department department = departmentRepository.findByNameDepartment(product.getDepartment().getNameDepartment()).orElse(null);


        product.setCodeProduct(dtoProductAdmin.getCodeProduct());
        product.setCriticProduct(dtoProductAdmin.getCriticProduct());
        product.setCostPriceProduct(dtoProductAdmin.getCostPriceProduct());
        product.setStockProduct(dtoProductAdmin.getStockProduct());
        product.setDescriptionProduct(dtoProductAdmin.getDescriptionProduct());
        product.setPriceProduct(dtoProductAdmin.getPriceProduct());
        product.setProductName(dtoProductAdmin.getProductName());
        product.setDepartment(department);

        productRepository.save(product);
        return dtoProductAdmin.parseDTOProductAdmin(product);

    }

    public String deleteProduct(String productName) {
        productRepository.deleteByProductName(productName);

        return "producto ha sido eliminado";
    }

    public List<DTOProductAdmin> filterAdminProducts(String filter) {
        Department department = departmentRepository.findByNameDepartment(filter).orElse(null);

        List<Product> products = productRepository.findByDepartment(department);
        List<DTOProductAdmin> dtoProductAdmins = new ArrayList<>();

        for (Product product : products){
            dtoProductAdmins.add(dtoProductAdmin.parseDTOProductAdmin(product));
        }
        return dtoProductAdmins;

    }

    public List<DTOProductClient> filterClientProducts(String filter) {
        Department department = departmentRepository.findByNameDepartment(filter).orElse(null);

        List<Product> products = productRepository.findByDepartment(department);
        List<DTOProductClient> dtoProductClients = new ArrayList<>();

        for (Product product : products){
            dtoProductClients.add(dtoProductClient.parseDTOProductClient(product));
        }
        return dtoProductClients;

    }
}
