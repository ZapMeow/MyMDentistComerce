package com.MyMDentis.MyMDentistComerce.Service;

import com.MyMDentis.MyMDentistComerce.DTO.DTOProductAdmin;
import com.MyMDentis.MyMDentistComerce.DTO.DTOProductClient;
import com.MyMDentis.MyMDentistComerce.DTO.DTOUtilsProducts;
import com.MyMDentis.MyMDentistComerce.Exception.ExceptionValues;
import com.MyMDentis.MyMDentistComerce.Exception.InvalidValuesEntityException;
import com.MyMDentis.MyMDentistComerce.Exception.NotFoundEntityException;
import com.MyMDentis.MyMDentistComerce.Exception.NullValuesEntityException;
import com.MyMDentis.MyMDentistComerce.Model.Department;
import com.MyMDentis.MyMDentistComerce.Model.Product;
import com.MyMDentis.MyMDentistComerce.Repository.DepartmentRepository;
import com.MyMDentis.MyMDentistComerce.Repository.ProductRepository;
import com.MyMDentis.MyMDentistComerce.Verification.Entities;
import com.MyMDentis.MyMDentistComerce.Verification.ProductVerification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    private final int PAGE_SIZE = 20;

    private final DTOProductAdmin dtoProductAdmin = new DTOProductAdmin();
    private final DTOProductClient dtoProductClient = new DTOProductClient();
    private final ProductVerification productVerification = new ProductVerification();


    private static  final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Transactional
    public DTOProductClient getClientProductById(Long idProduct) {
        return dtoProductClient.parseDTOProductClient(productRepository.findById(idProduct)
                .orElseThrow(() ->
                new NotFoundEntityException
                        (ExceptionValues.PRODUCT_NOT_FOUND_CODE, Entities.PRODUCT, ExceptionValues.PRODUCT_NOT_FOUND_MESSAGE)));
    }

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
        if (index < 1) {
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_VALUES_EXCEPTION_CODE, "Indice de pagina", "El indice de pagina debe ser mayor a 0");
        }

        List<DTOProductAdmin> products = new ArrayList<>();

        //Spring use 0-based index and 20 is the pageSize
        Pageable pageable = PageRequest.of(index-1, PAGE_SIZE);
        Page<Product> productPage = productRepository.findAll(pageable);

        for (Product product : productPage){
            products.add(dtoProductAdmin.parseDTOProductAdmin(product));

        }

        return products;
    }

    @Transactional
    public List<DTOProductClient> getProductsClientByPage(int pageIndex) {
        if (pageIndex < 1) {
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_VALUES_EXCEPTION_CODE, "Indice de pagina", "El indice de pagina debe ser mayor a 0");
        }

        List<DTOProductClient> products = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageIndex-1, PAGE_SIZE);
        Page<Product> productPage = productRepository.findAll(pageable);

        for (Product product : productPage){
            products.add(dtoProductClient.parseDTOProductClient(product));
        }
        return products;
    }

    @Transactional
    public List<DTOProductClient> getFilterClientProductsByPage(String filter, int indexPage){
        if (indexPage < 1) {
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_VALUES_EXCEPTION_CODE, "Indice de pagina", "El indice de pagina debe ser mayor a 0");
        }
        
        Department department = departmentRepository.findByNameDepartment(filter).orElseThrow( () ->
                new NotFoundEntityException(ExceptionValues.DEPARTMENT_NOT_FOUND_EXCEPTION_CODE, Entities.DEPARTMENT, ExceptionValues.DEPARTMENT_NOT_FOUND_EXCEPTION_MESSAGE));

        List<DTOProductClient> products = new ArrayList<>();
        Pageable pageable = PageRequest.of(indexPage -1, PAGE_SIZE);
        Page<Product> productPage = productRepository.findByDepartment(department, pageable);

        for (Product product : productPage){
            products.add(dtoProductClient.parseDTOProductClient(product));
        }
        return products;
    }

    @Transactional
    public DTOProductAdmin saveNewProduct(DTOProductAdmin dtoProductAdmin) {

        String exception = productVerification.nullVerification(dtoProductAdmin);

        if (exception != null){
            throw new NullValuesEntityException(ExceptionValues.NULL_VALUES_EXCEPTION_CODE,
                    exception,
                    ExceptionValues.NULL_VALUES_EXCEPTION_MESSAGE);
        }

        exception = productVerification.validValues(dtoProductAdmin);

        if (exception != null){
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_VALUES_EXCEPTION_CODE,
                    exception,
                    ExceptionValues.INVALID_VALUES_EXCEPTION_MESSAGE);
        }
        if (productVerification.validPatter(dtoProductAdmin.getCodeProduct())){
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_PRODUCT_CODE_EXCEPTION_CODE,
                                                    "Codigo del producto",
                                                    ExceptionValues.INVALID_PRODUCT_CODE_EXCEPTION_MESSAGE);
        }
        if (productVerification.validPriceCorrelation(dtoProductAdmin)){
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_PRICE_PRODUCT_EXCEPTION_CODE,
                                                    "Precio del producto / Costo del producto",
                                                    ExceptionValues.INVALID_PRICE_PRODUCT_EXCEPTION_MESSAGE);
        }
        if (productVerification.validStockCorrelation(dtoProductAdmin)){
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_STOCK_PRODUCT_EXCEPTION_CODE,
                                                    "Stock del producto / Stock critico del producto",
                                                    ExceptionValues.INVALID_STOCK_PRODUCT_EXCEPTION_MESSAGE);
        }
        if (existCode(dtoProductAdmin.getCodeProduct())){
            throw new InvalidValuesEntityException(ExceptionValues.CODE_PRODUCT_ALREADY_EXIST_CODE,
                                                    "Codigo del producto",
                                                    ExceptionValues.CODE_PRODUCT_ALREADY_EXIST_MESSAGE);
        }
        if (existProductName(dtoProductAdmin.getProductName())){
            throw new InvalidValuesEntityException(ExceptionValues.NAME_PRODUCT_ALREADY_EXIST_CODE,
                                                    "Nombre del producto",
                                                    ExceptionValues.NAME_PRODUCT_ALREADY_EXIST_MESSAGE);
        }
        if (existDepartment(dtoProductAdmin.getNameDepartment()).isEmpty()){
            throw new InvalidValuesEntityException(ExceptionValues.DEPARTMENT_NOT_FOUND_EXCEPTION_CODE,
                                                    "Departamento del producto",
                                                    ExceptionValues.DEPARTMENT_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        Department department = departmentRepository.findByNameDepartment(dtoProductAdmin.getNameDepartment())
                .orElseThrow(() -> new NotFoundEntityException(ExceptionValues.DEPARTMENT_NOT_FOUND_CODE,
                        Entities.DEPARTMENT,
                        ExceptionValues.DEPARTMENT_NOT_FOUND_MESSAGE));

        Product newProduct = Product.builder()
                .codeProduct(dtoProductAdmin.getCodeProduct().trim())
                .productName(dtoProductAdmin.getProductName().trim())
                .descriptionProduct(dtoProductAdmin.getDescriptionProduct() != null ? dtoProductAdmin.getDescriptionProduct().trim() : null)
                .stockProduct(dtoProductAdmin.getStockProduct())
                .criticProduct(dtoProductAdmin.getCriticProduct())
                .priceProduct(dtoProductAdmin.getPriceProduct())
                .costPriceProduct(dtoProductAdmin.getCostPriceProduct())
                .department(department)
                .build();

        return dtoProductAdmin.parseDTOProductAdmin(productRepository.save(newProduct));

    }

    @Transactional
    public DTOProductAdmin editProduct(String productName, DTOProductAdmin dtoProductAdmin) {

        String exception = productVerification.nullVerification(dtoProductAdmin);

        if (exception != null){
            throw new NullValuesEntityException(ExceptionValues.NULL_VALUES_EXCEPTION_CODE,
                    exception,
                    ExceptionValues.NULL_VALUES_EXCEPTION_MESSAGE);
        }

        exception = productVerification.validValues(dtoProductAdmin);

        if (exception != null){
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_VALUES_EXCEPTION_CODE,
                    exception,
                    ExceptionValues.INVALID_VALUES_EXCEPTION_MESSAGE);
        }
        if (productVerification.validPatter(dtoProductAdmin.getCodeProduct())){
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_PRODUCT_CODE_EXCEPTION_CODE,
                    "Codigo del producto",
                    ExceptionValues.INVALID_PRODUCT_CODE_EXCEPTION_MESSAGE);
        }
        if (productVerification.validPriceCorrelation(dtoProductAdmin)){
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_PRICE_PRODUCT_EXCEPTION_CODE,
                    "Precio del producto / Costo del producto",
                    ExceptionValues.INVALID_PRICE_PRODUCT_EXCEPTION_MESSAGE);
        }
        if (productVerification.validStockCorrelation(dtoProductAdmin)){
            throw new InvalidValuesEntityException(ExceptionValues.INVALID_STOCK_PRODUCT_EXCEPTION_CODE,
                    "Stock del producto / Stock critico del producto",
                    ExceptionValues.INVALID_STOCK_PRODUCT_EXCEPTION_MESSAGE);
        }

        Product productToEdit = productRepository.findByProductName(productName).orElseThrow(
                () -> new NotFoundEntityException(ExceptionValues.PRODUCT_NOT_FOUND_CODE, Entities.PRODUCT, ExceptionValues.PRODUCT_NOT_FOUND_MESSAGE)
        );

        Optional<Product> existingCodeProduct = productRepository.findByCodeProduct(dtoProductAdmin.getCodeProduct());
        if (existingCodeProduct.isPresent() && !existingCodeProduct.get().getIdProduct().equals(productToEdit.getIdProduct())) {
            throw new InvalidValuesEntityException(ExceptionValues.CODE_PRODUCT_ALREADY_EXIST_CODE,
                    "Codigo del producto",
                    ExceptionValues.CODE_PRODUCT_ALREADY_EXIST_MESSAGE);
        }

        Optional<Product> existingNameProduct = productRepository.findByProductName(dtoProductAdmin.getProductName());
        if (existingNameProduct.isPresent() && !existingNameProduct.get().getIdProduct().equals(productToEdit.getIdProduct())) {
            throw new InvalidValuesEntityException(ExceptionValues.NAME_PRODUCT_ALREADY_EXIST_CODE,
                    "Nombre del producto",
                    ExceptionValues.NAME_PRODUCT_ALREADY_EXIST_MESSAGE);
        }

        if (existDepartment(dtoProductAdmin.getNameDepartment()).isEmpty()){
            throw new InvalidValuesEntityException(ExceptionValues.DEPARTMENT_NOT_FOUND_EXCEPTION_CODE,
                    "Departamento del producto",
                    ExceptionValues.DEPARTMENT_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        Department department = departmentRepository.findByNameDepartment(dtoProductAdmin.getNameDepartment())
                .orElseThrow(() -> new NotFoundEntityException(ExceptionValues.DEPARTMENT_NOT_FOUND_CODE,
                        Entities.DEPARTMENT,
                        ExceptionValues.DEPARTMENT_NOT_FOUND_MESSAGE));

        productToEdit.setProductName(dtoProductAdmin.getProductName());
        productToEdit.setCodeProduct(dtoProductAdmin.getCodeProduct());
        productToEdit.setDescriptionProduct(dtoProductAdmin.getDescriptionProduct());
        productToEdit.setPriceProduct(dtoProductAdmin.getPriceProduct());
        productToEdit.setCostPriceProduct(dtoProductAdmin.getCostPriceProduct());
        productToEdit.setStockProduct(dtoProductAdmin.getStockProduct());
        productToEdit.setCriticProduct(dtoProductAdmin.getCriticProduct());
        productToEdit.setDepartment(department);

        return dtoProductAdmin.parseDTOProductAdmin(productRepository.save(productToEdit));

    }

    @Transactional
    public void deleteProduct(String productName) {
        if (productName == null || productName.trim().isEmpty()){
            throw new NullValuesEntityException(ExceptionValues.NULL_VALUES_EXCEPTION_CODE, "Nombre del producto", ExceptionValues.NULL_VALUES_EXCEPTION_MESSAGE);
        }

        Product product = productRepository.findByProductName(productName).orElseThrow(
                () -> new NotFoundEntityException(ExceptionValues.PRODUCT_NOT_FOUND_CODE, Entities.PRODUCT, ExceptionValues.PRODUCT_NOT_FOUND_MESSAGE)
        );
        productRepository.deleteByProductName(productName);
    }

    @Transactional
    public List<DTOProductAdmin> filterAdminProducts(String filter) {
        Department department = departmentRepository.findByNameDepartment(filter).orElseThrow(
                () -> new NotFoundEntityException(ExceptionValues.DEPARTMENT_NOT_FOUND_CODE, Entities.DEPARTMENT, ExceptionValues.DEPARTMENT_NOT_FOUND_MESSAGE)
        );

        List<Product> products = productRepository.findByDepartment(department);
        List<DTOProductAdmin> dtoProductAdmins = new ArrayList<>();

        for (Product product : products){
            dtoProductAdmins.add(dtoProductAdmin.parseDTOProductAdmin(product));
        }
        return dtoProductAdmins;

    }

    @Transactional
    public List<DTOProductClient> filterClientProducts(String filter) {
        Department department = departmentRepository.findByNameDepartment(filter).orElseThrow(
                () -> new NotFoundEntityException(ExceptionValues.DEPARTMENT_NOT_FOUND_CODE, Entities.DEPARTMENT, ExceptionValues.DEPARTMENT_NOT_FOUND_MESSAGE)
        );

        List<Product> products = productRepository.findByDepartment(department);
        List<DTOProductClient> dtoProductClients = new ArrayList<>();

        for (Product product : products){
            dtoProductClients.add(dtoProductClient.parseDTOProductClient(product));
        }
        return dtoProductClients;

    }



    ////////////////////////////////////////////////verifications///////////////////////////////////////
    @Transactional
    public boolean existCode(String productCode){
        Product product = productRepository.findByCodeProduct(productCode).orElse(null);
        return product != null;
    }

    @Transactional
    public boolean existProductName(String productName){
        Product product = productRepository.findByProductName(productName).orElse(null);
        return product != null;
    }

    @Transactional
    public Optional<Department> existDepartment(String departmentName){
        return departmentRepository.findByNameDepartment(departmentName);
    }





    /////////////////////UTILS//////////////////////////////
    @Transactional
    public DTOUtilsProducts getMaxPages(){
        long total = productRepository.count();
        long totalPages = (total + PAGE_SIZE - 1) / PAGE_SIZE;
        return DTOUtilsProducts.builder()
                .totalProducts(total)
                .totalPages(totalPages)
                .build();
    }

    public DTOUtilsProducts getMaxPagesByDepartmentFilter(String filter){
        Department department = departmentRepository.findByNameDepartment(filter)
                .orElseThrow(() -> new NotFoundEntityException(ExceptionValues.DEPARTMENT_NOT_FOUND_EXCEPTION_CODE, Entities.DEPARTMENT, ExceptionValues.DEPARTMENT_NOT_FOUND_EXCEPTION_MESSAGE));
        long total = productRepository.countByDepartment(department);
        long totalPages = (total + PAGE_SIZE - 1) / PAGE_SIZE;
        return DTOUtilsProducts.builder()
                .totalProducts(total)
                .totalPages(totalPages)
                .build();
    }



}