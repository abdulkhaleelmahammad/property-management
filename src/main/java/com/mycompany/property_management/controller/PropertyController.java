package com.mycompany.property_management.controller;

import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.dto.UserDTO;
import com.mycompany.property_management.service.PropertyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @PostMapping("/properties")
    public ResponseEntity<PropertyDTO> saveProperty(@RequestBody PropertyDTO propertyDTO) {
        propertyService.saveProperty(propertyDTO);
        ResponseEntity<PropertyDTO> responseEntity = new ResponseEntity<>(propertyDTO, HttpStatus.CREATED);

        return responseEntity;
    }

    @GetMapping("/properties")
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        List<PropertyDTO> propertyDTOList = propertyService.getAllProperties();

        ResponseEntity<List<PropertyDTO>> responseEntites = new ResponseEntity<>(propertyDTOList, HttpStatus.OK);

        return responseEntites;
    }
    @GetMapping("/properties/users/{userId}")
    public ResponseEntity<List<PropertyDTO>> getAllPropertiesByUser(@PathVariable Long userId) {

        List<PropertyDTO> propertyDTOList = propertyService.getAllPropertiesByUser(userId);

        ResponseEntity<List<PropertyDTO>> responseEntites = new ResponseEntity<>(propertyDTOList, HttpStatus.OK);

        return responseEntites;
    }

    @PutMapping("/properties/{propertyId}")
    public ResponseEntity<PropertyDTO> updateProperty(@RequestBody PropertyDTO propertyDTO,@PathVariable Long propertyId){
        propertyDTO = propertyService.updateProperty(propertyDTO, propertyId);
        ResponseEntity<PropertyDTO> responseEntity = new ResponseEntity<>(propertyDTO, HttpStatus.CREATED);
        return responseEntity;
    }

    @PatchMapping("/properties/update-description/{propertyId}")
    public ResponseEntity<PropertyDTO> updatePropertyDescription(@RequestBody PropertyDTO propertyDTO,@PathVariable Long propertyId){
        propertyDTO = propertyService.updatePropertyDescription(propertyDTO, propertyId);
        ResponseEntity<PropertyDTO> responseEntity = new ResponseEntity<>(propertyDTO, HttpStatus.OK);
        return responseEntity;
    }

    @PatchMapping("/properties/update-price/{propertyId}")
    public ResponseEntity<PropertyDTO> updatePropertyPrice(@RequestBody PropertyDTO propertyDTO,@PathVariable Long propertyId){
        propertyDTO = propertyService.updatePropertyPrice(propertyDTO, propertyId);
        ResponseEntity<PropertyDTO> responseEntity = new ResponseEntity<>(propertyDTO, HttpStatus.OK);
        return responseEntity;
    }
    @DeleteMapping("/properties/{propertyId}")
    public ResponseEntity deleteProperty(@PathVariable Long propertyId){
        propertyService.deleteProperty(propertyId);
        ResponseEntity<Void> responseEntity = new ResponseEntity<>((HttpHeaders) null, HttpStatus.NO_CONTENT);
        return responseEntity;
    }

}
