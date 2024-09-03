package com.springboot.locadora.dashboard.services;

import com.springboot.locadora.books.repositories.BooksRepository;
import com.springboot.locadora.dashboard.DTOs.BooksMoreRentedDTO;
import com.springboot.locadora.dashboard.DTOs.RentsPerRenterRecordDTO;
import com.springboot.locadora.dashboard.mappers.BookRentMapper;
import com.springboot.locadora.renters.entities.RenterEntity;
import com.springboot.locadora.renters.repositories.RenterRepository;
import com.springboot.locadora.rents.entities.RentsEntity;
import com.springboot.locadora.rents.enums.RentStatusEnum;
import com.springboot.locadora.rents.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServices {

    @Autowired
    RentRepository rentRepository;

    @Autowired
    RenterRepository renterRepository;

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    private BookRentMapper bookRentMapper;

    public int getNumberOfRental() {
        List<RentsEntity> totalRentals = rentRepository.findAll();
        int rentsQuantity = totalRentals.size();

        return rentsQuantity;
    }

    public int getNumberOfRentalsLate(){
        List<RentsEntity> totalRentalsLate = rentRepository.findAllByStatus(RentStatusEnum.LATE);
        int rentsLateQuantity = totalRentalsLate.size();

        return rentsLateQuantity;
    }

    public int getDeliveredInTime(){
        List<RentsEntity> totalRentalsInTime = rentRepository.findAllByStatus(RentStatusEnum.IN_TIME);
        int rentsInTime = totalRentalsInTime.size();

        return rentsInTime;
    }

    public int getDeliveredWithDelay(){
        List<RentsEntity> totalRentalsDelay = rentRepository.findAllByStatus(RentStatusEnum.DELIVERED_WITH_DELAY);
        int rentWithDelay = totalRentalsDelay.size();

        return rentWithDelay;
    }

    public List<RentsPerRenterRecordDTO> getRentsPerRenter(){
        List<RenterEntity> renters = renterRepository.findAll();
        List<RentsPerRenterRecordDTO> renterRentList = new ArrayList<>();

        for (RenterEntity renter : renters){
            List<RentsEntity> rents = rentRepository.findAllByRenterId(renter.getId());
            List<RentsEntity> rentsActive = rentRepository.findAllByRenterIdAndStatus(renter.getId(), RentStatusEnum.RENTED);
            renterRentList.add(new RentsPerRenterRecordDTO(renter.getName(), rents.size(), rentsActive.size()));
        }

        return renterRentList;
    }

    public List<BooksMoreRentedDTO> getBooksMoreRented(){
        return bookRentMapper.toBooksMoreRentedList(booksRepository.findAll());
    }
}
