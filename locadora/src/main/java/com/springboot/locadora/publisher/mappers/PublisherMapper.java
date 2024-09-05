package com.springboot.locadora.publisher.mappers;

import com.springboot.locadora.publisher.DTOs.PublisherRecordDTO;
import com.springboot.locadora.publisher.entities.PublisherEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublisherMapper {

    public List<PublisherRecordDTO> toPublisherReponseList(List<PublisherEntity> publisherList) {
        return publisherList.stream().map(this::toPublisherResponse).toList();
    }

    public PublisherRecordDTO toPublisherResponse(PublisherEntity model){
        return PublisherRecordDTO
                .builder()
                .id(model.getId())
                .name(model.getName())
                .email(model.getEmail())
                .telephone(model.getTelephone())
                .site(model.getSite())
                .build();
    }
}
