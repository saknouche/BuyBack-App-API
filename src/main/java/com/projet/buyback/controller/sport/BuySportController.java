package com.projet.buyback.controller.sport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet.buyback.model.User;
import com.projet.buyback.model.sport.Sport;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.repository.sport.SportRepository;
import com.projet.buyback.schema.response.security.MessageResponse;
import com.projet.buyback.schema.response.sport.SportResponse;
import com.projet.buyback.service.sport.SportService;
import com.projet.buyback.service.user.UserService;
import com.projet.buyback.utils.security.JwtUtils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.baseURL}/buySportTicket")
public class BuySportController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private SportRepository sportRepository;
	@Autowired
	private SportService sportService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getSportTicketWithoutPurshaser(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth,
			@PathVariable("id") Long sportId) {
		String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
		User user = null;
		if (userRepository.findByEmail(email) != null) {
			user = userRepository.findByEmail(email).get();
		}
		SportResponse sportResponse = sportService.getSportTicketById(sportId);
		if (sportResponse != null) {
			if (sportResponse.getPurchaser() != null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
			} else if (user.getId() == sportResponse.getSeller().getId()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("You cannot buy your own ticket !"));
			}
			return ResponseEntity.status(HttpStatus.OK).body(sportResponse);

		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));

	}

	@PostMapping("/{id}")
	public ResponseEntity<?> buySportTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth,
			@PathVariable("id") Long id) {

		String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
		User user = null;
		if (userRepository.findByEmail(email) != null) {
			user = userRepository.findByEmail(email).get();
		}
		Sport sport = null;
		if (id != null) {
			sport = sportRepository.findById(id).get();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
		}

		sport.setPurchaser(user);
		sportRepository.save(sport);
		return ResponseEntity.ok(sportRepository.save(sport));

	}

}