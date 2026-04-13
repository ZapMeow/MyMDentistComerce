package com.MyMDentis.MyMDentistComerce.Repository;

import com.MyMDentis.MyMDentistComerce.Model.Department;
import com.MyMDentis.MyMDentistComerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCodeProduct(String codeProduct);
    Optional<Product> findByProductName(String productName);
    void deleteByProductName(String productName);
    List<Product> findByDepartment(Department department);

    // Page<Product> findByStatus(String status, Pageable pageable);

}
