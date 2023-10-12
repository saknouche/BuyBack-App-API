package com.projet.buyback.controller.spectacle;

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
import com.projet.buyback.model.spectacle.Spectacle;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.repository.spectacle.SpectacleRepository;
import com.projet.buyback.schema.response.security.MessageResponse;
import com.projet.buyback.schema.response.spectacle.SpectacleResponse;
import com.projet.buyback.service.spectacle.SpectacleService;
import com.projet.buyback.service.user.UserService;
import com.projet.buyback.utils.security.JwtUtils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.baseURL}/buySpectacleTicket")
public class BuySpectacleController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private SpectacleRepository spectacleRepository;
	@Autowired
	private SpectacleService spectacleService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getSpectacleTicketWithoutPurshaser(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth,
			@PathVariable("id") Long sportId) {
		String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
		User user = null;
		if (userRepository.findByEmail(email) != null) {
			user = userRepository.findByEmail(email).get();
		}
		SpectacleResponse spectacleResponse = spectacleService.getSpectacleTicketById(sportId);
		if (spectacleResponse != null) {
			if (spectacleResponse.getPurchaser() != null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
			} else if (user.getId() == spectacleResponse.getSeller().getId()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("You cannot buy your own ticket !"));
			}
			return ResponseEntity.status(HttpStatus.OK).body(spectacleResponse);

		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));

	}

	@PostMapping("/{id}")
	public ResponseEntity<?> buySpectacleTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth,
			@PathVariable("id") Long id) {

		String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
		User user = null;
		if (userRepository.findByEmail(email) != null) {
			user = userRepository.findByEmail(email).get();
		}
		Spectacle spectacle = null;
		if (id != null) {
			spectacle = spectacleRepository.findById(id).get();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
		}

		spectacle.setPurchaser(user);
		spectacleRepository.save(spectacle);
		return ResponseEntity.ok(spectacleRepository.save(spectacle));

	}
}
