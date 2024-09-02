package com.springboot.locadora.rents.mappers;


import com.springboot.locadora.rents.DTOs.RentsRecordDTO;
import com.springboot.locadora.rents.entities.RentsEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentMapper {

    public List<RentsRecordDTO> toRentResponseList(List<RentsEntity> rentList){
        return rentList.stream().map(this::toRentResponse).toList();
    }

    public RentsRecordDTO toRentResponse(RentsEntity model){
        return RentsRecordDTO
                .builder()
                .id(model.getId())
                .book(model.getBook())
                .renter(model.getRenter())
                .deadLine(model.getDeadLine())
                .devolutionDate(model.getDevolutionDate())
                .rentDate(model.getRentDate())
                .status(model.getStatus())
                .build();
    }
}