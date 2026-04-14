package com.MyMDentis.MyMDentistComerce.Service;

import com.MyMDentis.MyMDentistComerce.DTO.DTOUserEntity;
import com.MyMDentis.MyMDentistComerce.Model.Roles;
import com.MyMDentis.MyMDentistComerce.Model.UserEntity;
import com.MyMDentis.MyMDentistComerce.Repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final DTOUserEntity dtoUserEntity = new DTOUserEntity();


    public List<DTOUserEntity> getAllUsers(){
        return userEntityRepository.findAll().stream().map(dtoUserEntity::parseDTOUserEntity).toList();
    }

    public DTOUserEntity findUserByUsername(String username){
        return dtoUserEntity.parseDTOUserEntity(Objects.requireNonNull(userEntityRepository.findByNameUser(username).orElse(null)));
    }

    public DTOUserEntity createUser(DTOUserEntity dtoUserEntity){
        UserEntity user = UserEntity.builder()
                .cellphoneUser(dtoUserEntity.getCellphoneUser())
                .emailUser(dtoUserEntity.getEmailUser())
                .nameUser(dtoUserEntity.getNameUser())
                .passwordUser(passwordEncoder.encode(dtoUserEntity.getPasswordUser()))
                .role(dtoUserEntity.getRole())
                .build();


        return dtoUserEntity.parseDTOUserEntity(userEntityRepository.save(user));
    }

    public DTOUserEntity createDefaultUser(DTOUserEntity dtoUserEntity){
        UserEntity user = UserEntity.builder()
                .cellphoneUser(dtoUserEntity.getCellphoneUser())
                .emailUser(dtoUserEntity.getEmailUser())
                .nameUser(dtoUserEntity.getNameUser())
                .passwordUser(passwordEncoder.encode(dtoUserEntity.getPasswordUser()))
                .role(Roles.CLIENT)
                .build();

        return dtoUserEntity.parseDTOUserEntity(userEntityRepository.save(user));
    }



}
