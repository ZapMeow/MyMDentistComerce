package com.MyMDentis.MyMDentistComerce.DTO;

import com.MyMDentis.MyMDentistComerce.Model.Roles;
import com.MyMDentis.MyMDentistComerce.Model.UserEntity;
import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOUserEntity {

    private String nameUser;
    private String emailUser;
    private String passwordUser;
    private Long cellphoneUser;
    private Roles role;

    public DTOUserEntity parseDTOUserEntity(UserEntity userEntity){
        return DTOUserEntity.builder()
                .nameUser(userEntity.getNameUser())
                .emailUser(userEntity.getEmailUser())
                .passwordUser(userEntity.getPasswordUser())
                .cellphoneUser(userEntity.getCellphoneUser())
                .role(userEntity.getRole())
                .build();
    }
}
