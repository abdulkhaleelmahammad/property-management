package com.mycompany.property_management.service;

import com.mycompany.property_management.converter.PropertyConverter;
import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.entity.PropertyEntity;
import com.mycompany.property_management.entity.UserEntity;
import com.mycompany.property_management.exception.BusinessException;
import com.mycompany.property_management.exception.ErrorModel;
import com.mycompany.property_management.repository.PropertyRepository;
import com.mycompany.property_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService{

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyConverter propertyConverter;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO){

        Optional<UserEntity> optionalUserEntity = userRepository.findById(propertyDTO.getUserId());

        if(optionalUserEntity.isPresent()) {
            PropertyEntity pe = propertyConverter.convertDTOtoEntity(propertyDTO);
            //propertyRepository.save(pe);
            pe.setUserEntity(optionalUserEntity.get());
            pe = propertyRepository.save(pe);
            propertyDTO = propertyConverter.convertEntitytoDTO(pe);
        }else{
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("USER_ID_NOT_EXIST");
            errorModel.setMessage("User does not exists");
            errorModelList.add(errorModel);

            throw new BusinessException(errorModelList);
        }
        return propertyDTO;

    }

    @Override
    public List<PropertyDTO> getAllProperties() {
        List<PropertyEntity> listOfProperties = (List<PropertyEntity>)propertyRepository.findAll();
        List<PropertyDTO> propList = new ArrayList<>();

        for(PropertyEntity pe : listOfProperties ){
           PropertyDTO dto =  propertyConverter.convertEntitytoDTO(pe);
           propList.add(dto);
        }
        return propList;
    }

    @Override
    public List<PropertyDTO> getAllPropertiesByUser(Long userId) {
        List<PropertyEntity> listOfProperties = (List<PropertyEntity>)propertyRepository.findAllByUserEntityId(userId);
        List<PropertyDTO> propList = new ArrayList<>();

        for(PropertyEntity pe : listOfProperties ){
            PropertyDTO dto =  propertyConverter.convertEntitytoDTO(pe);
            propList.add(dto);
        }
        return propList;
    }

    @Override
    public PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyId) {
        PropertyDTO dto = null;
        Optional<PropertyEntity> optEnt =  propertyRepository.findById(propertyId);
        if(optEnt.isPresent()){
            PropertyEntity pe = optEnt.get();
            pe.setTitle(propertyDTO.getTitle());
            pe.setAddress(propertyDTO.getAddress());
            pe.setPrice(propertyDTO.getPrice());
            pe.setDescription(propertyDTO.getDescription());

            dto = propertyConverter.convertEntitytoDTO(pe);
            propertyRepository.save(pe);
        }

        return dto;
    }

    @Override
    public PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyId) {
        PropertyDTO dto = null;
        Optional<PropertyEntity> optEnt =  propertyRepository.findById(propertyId);
        if(optEnt.isPresent()){
            PropertyEntity pe = optEnt.get();
            pe.setDescription(propertyDTO.getDescription());

            dto = propertyConverter.convertEntitytoDTO(pe);
            propertyRepository.save(pe);
        }

        return dto;
    }

    @Override
    public PropertyDTO updatePropertyPrice(PropertyDTO propertyDTO, Long propertyId) {
        PropertyDTO dto = null;
        Optional<PropertyEntity> optEnt =  propertyRepository.findById(propertyId);
        if(optEnt.isPresent()){
            PropertyEntity pe = optEnt.get();
            pe.setPrice(propertyDTO.getPrice());

            dto = propertyConverter.convertEntitytoDTO(pe);
            propertyRepository.save(pe);
        }

        return dto;
    }

    @Override
    public void deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
    }
}
