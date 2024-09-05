package com.springboot.locadora.dashboard.controllers;

import com.springboot.locadora.dashboard.services.DashboardServices;
import com.springboot.locadora.renters.DTOs.CreateRenterRecordDTO;
import com.springboot.locadora.renters.DTOs.RenterRecordDTO;
import com.springboot.locadora.renters.DTOs.UpdateRenterRecordDTO;
import com.springboot.locadora.renters.mappers.RenterMapper;
import com.springboot.locadora.renters.services.RenterServices;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("dashboard")
public class DashboardController {

    @Autowired
    DashboardServices dashboardServices;

    @GetMapping("/rentsQuantity")
    public ResponseEntity<Integer> getRentsQuantity() {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardServices.getNumberOfRental());
    }

    @GetMapping("/rentsLateQuantity")
    public ResponseEntity<Integer> getById(@PathVariable(value = "id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardServices.getNumberOfRentalsLate());
    }

    @GetMapping("/deliveredWithDelayQuantity")
    public ResponseEntity<Integer> getRentsDeliveredWithDelay(){
        return ResponseEntity.status(HttpStatus.OK).body(dashboardServices.getDeliveredWithDelay());
    }

    @GetMapping("/rentsPerRenter")
    public ResponseEntity<Object> getRentsPerRenter(){
        return ResponseEntity.status(HttpStatus.OK).body(dashboardServices.getRentsPerRenter());
    }

    @GetMapping("/bookMoreRented")
    public ResponseEntity<Object> getBooksMoreRented(){
        return ResponseEntity.status(HttpStatus.OK).body(dashboardServices.getBooksMoreRented());
    }
}