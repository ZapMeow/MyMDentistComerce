package com.MyMDentis.MyMDentistComerce.Controller;

import com.MyMDentis.MyMDentistComerce.Model.Reserved;
import com.MyMDentis.MyMDentistComerce.Service.ReservedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/MyMDentalCommerce/reserved")
public class ReservedController {

    @Autowired
    private ReservedService reservedService;

    @GetMapping(path = "/getAllReserved")
    public ResponseEntity<List<Reserved>> getAllReserved() throws InterruptedException{
        Thread.sleep(2000L);
        return ResponseEntity.ok(reservedService.getAllReserved());
    }

    @GetMapping(path = "/getAvailableReserved")
    public ResponseEntity<List<Reserved>> getAvailableReserved() throws InterruptedException{
        Thread.sleep(2000L);
        return ResponseEntity.ok(reservedService.getAvailableReserved());
    }

    @GetMapping(path = "/getNotAvailableReserved")
    public ResponseEntity<List<Reserved>> getNotAvailableReserved() throws InterruptedException{
        Thread.sleep(2000L);
        return ResponseEntity.ok(reservedService.getNotAvailableReserved());
    }

    @GetMapping(path = "/getUserReserved/{idUser}")
    public ResponseEntity<List<Reserved>> getProductReserved(@PathVariable Long idUser) throws InterruptedException{
        Thread.sleep(2000L);
        return ResponseEntity.ok(reservedService.getUserReserved(idUser));
    }


}
