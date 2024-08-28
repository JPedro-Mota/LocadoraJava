package com.springboot.locadora.renters.mappers;

import com.springboot.locadora.renters.DTOs.RenterRecordDTO;
import com.springboot.locadora.renters.entities.RenterEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RenterMapper {

    public List<RenterRecordDTO> toRenterRecordList(List<RenterEntity> renterList){
        return renterList.stream().map(this::toRenterResponse).toList();
    }

    public RenterRecordDTO toRenterResponse(RenterEntity model){
        return RenterRecordDTO
                .builder()
                .id(model.getId())
                .name(model.getName())
                .email(model.getEmail())
                .telephone(model.getTelephone())
                .address(model.getAddress())
                .cpf(model.getCpf())
                .build();
    }
}
