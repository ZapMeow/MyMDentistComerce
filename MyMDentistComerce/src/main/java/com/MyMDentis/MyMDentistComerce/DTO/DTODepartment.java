package com.MyMDentis.MyMDentistComerce.DTO;

import com.MyMDentis.MyMDentistComerce.Model.Department;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTODepartment {

    private String nameDepartment;

    public DTODepartment parseToDTODepartment(Department department){
        return new DTODepartment(department.getNameDepartment());
    }
}
