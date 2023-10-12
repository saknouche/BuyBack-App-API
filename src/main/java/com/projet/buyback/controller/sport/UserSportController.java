package com.projet.buyback.controller.sport;

import com.projet.buyback.model.User;
import com.projet.buyback.model.sport.Sport;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.repository.sport.SportRepository;
import com.projet.buyback.schema.response.security.MessageResponse;
import com.projet.buyback.schema.response.sport.SportResponse;
import com.projet.buyback.schema.response.user.UserPublicResponse;
import com.projet.buyback.service.user.UserService;
import com.projet.buyback.utils.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${api.baseURL}/user/tickets/sport")
public class UserSportController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private SportRepository sportRepository;

    @GetMapping("/purchased")
    public ResponseEntity<?> getPurchasedTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        try {
            String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
            if (userRepository.findByEmail(email).isPresent()) {
                User user = userRepository.findByEmail(email).get();

                List<SportResponse> sportResponses = new ArrayList<>();
                for (Sport sport: sportRepository.findByPurchaser(user)) {
                    sportResponses.add(SportResponse.createSportResponse(sport));
                }

                return ResponseEntity.ok(
                    sportResponses
                );
            }
        }
        catch(Exception e) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse("Error: Could not recuperate tickets!"));
        }
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new MessageResponse("Error: Could not recuperate tickets!"));
    }

    @GetMapping("/for-sale")
    public ResponseEntity<?> getForSaleTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        try {
            String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
            if (userRepository.findByEmail(email).isPresent()) {
                User user = userRepository.findByEmail(email).get();

                List<SportResponse> sportResponses = new ArrayList<>();
                for (Sport sport: sportRepository.findBySellerAndPurchaserIsNull(user)) {
                    sportResponses.add(SportResponse.createSportResponse(sport));
                }

                return ResponseEntity.ok(
                    sportResponses
                );
            }
        }
        catch(Exception e) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse("Error: Could not recuperate tickets!"));
        }
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new MessageResponse("Error: Could not recuperate tickets!"));
    }

    @GetMapping("/sold")
    public ResponseEntity<?> getSoldTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        try {
            String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
            if (userRepository.findByEmail(email).isPresent()) {
                User user = userRepository.findByEmail(email).get();

                List<SportResponse> sportResponses = new ArrayList<>();
                for (Sport sport: sportRepository.findBySellerAndPurchaserIsNotNull(user)) {
                    sportResponses.add(SportResponse.createSportResponse(sport));
                }

                return ResponseEntity.ok(
                    sportResponses
                );
            }
        }
        catch(Exception e) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse("Error: Could not recuperate tickets!"));
        }
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new MessageResponse("Error: Could not recuperate tickets!"));
    }
}
