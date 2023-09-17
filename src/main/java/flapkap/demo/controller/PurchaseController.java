package flapkap.demo.controller;

import flapkap.demo.exceptions.NotExist;
import flapkap.demo.exceptions.NotValid;
import flapkap.demo.model.PurchaseDto;
import flapkap.demo.model.PurchaseResponseDto;
import flapkap.demo.model.User;
import flapkap.demo.service.PurchaseService;
import flapkap.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PurchaseController {
    private final UserService userService;
    private final PurchaseService purchaseService;

    public PurchaseController(UserService userService, PurchaseService purchaseService) {
        this.userService = userService;
        this.purchaseService=purchaseService;
    }

    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/deposit")
    public ResponseEntity<User> createUser(@RequestBody Integer amount) throws NotValid {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if (amount == 5 || amount == 10 || amount == 20 || amount == 50 || amount == 100) {
            user.setDeposit(user.getDeposit() + amount);
        } else {
            throw new NotValid("amount not valid");
        }
        return ResponseEntity.status(200).body(userService.saveUser(user));

    }

    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/buy")
    public ResponseEntity<PurchaseResponseDto> createUser(@RequestBody List<PurchaseDto> purchaseDto) throws NotValid, NotExist {
        return ResponseEntity.status(200).body(purchaseService.processPurchase(purchaseDto));
    }


}

