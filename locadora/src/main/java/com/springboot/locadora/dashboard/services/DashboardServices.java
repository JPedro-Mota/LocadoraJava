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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public int getNumberOfRentalsLate() {
        updateLateRentals();
        List<RentsEntity> totalRentalsLate = rentRepository.findAllByStatus(RentStatusEnum.LATE);
        int rentsLateQuantity = totalRentalsLate.size();
        return totalRentalsLate.size();
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

        return renterRentList.stream()
                .sorted(Comparator.comparingInt(RentsPerRenterRecordDTO::rentsQuantity).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<BooksMoreRentedDTO> getBooksMoreRented(){
        return bookRentMapper.toBooksMoreRentedList(booksRepository.findAll());
    }

    public void updateLateRentals() {
        List<RentsEntity> rentedRents = rentRepository.findAllByStatus(RentStatusEnum.RENTED);
        LocalDate today = LocalDate.now();

        for (RentsEntity rent : rentedRents) {
            if (rent.getDeadLine().isBefore(today)) {
                rent.setStatus(RentStatusEnum.LATE);
                rentRepository.save(rent);
            }
        }
    }

}
