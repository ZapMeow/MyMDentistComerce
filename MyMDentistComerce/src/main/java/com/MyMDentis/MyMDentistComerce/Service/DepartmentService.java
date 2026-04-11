package com.MyMDentis.MyMDentistComerce.Service;

import com.MyMDentis.MyMDentistComerce.DTO.DTODepartment;
import com.MyMDentis.MyMDentistComerce.Model.Department;
import com.MyMDentis.MyMDentistComerce.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private final DTODepartment dtoDepartment = new DTODepartment();

    public List<DTODepartment> getAllDepartment(){

        List<Department> departments = departmentRepository.findAll();
        List<DTODepartment> dtoDepartments = new ArrayList<>();

        for (Department department : departments){
            dtoDepartments.add(dtoDepartment.parseToDTODepartment(department));
        }
        return dtoDepartments;
    }

}
