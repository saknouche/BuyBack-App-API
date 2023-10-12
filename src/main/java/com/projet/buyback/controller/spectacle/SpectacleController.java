package com.projet.buyback.controller.spectacle;

import java.util.List;
import java.util.Optional;

import com.projet.buyback.schema.response.sport.SportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projet.buyback.model.Address;
import com.projet.buyback.model.spectacle.Spectacle;
import com.projet.buyback.model.spectacle.SpectacleCategory;
import com.projet.buyback.model.User;
import com.projet.buyback.repository.spectacle.SpectacleRepository;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.schema.response.spectacle.SpectacleResponse;
import com.projet.buyback.schema.request.spectacle.SpectacleRequest;
import com.projet.buyback.schema.response.security.MessageResponse;
import com.projet.buyback.service.spectacle.SpectacleCategoryService;
import com.projet.buyback.service.spectacle.SpectacleService;
import com.projet.buyback.utils.security.JwtUtils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.baseURL}/spectacles")
public class SpectacleController {

	@Autowired
	private SpectacleService spectacleService;
	@Autowired
	private SpectacleCategoryService spectacleCategoryService;
	@Autowired
	private SpectacleRepository spectacleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("")
	public ResponseEntity<?> getAllSportTickets(
		@RequestHeader(HttpHeaders.AUTHORIZATION) Optional<String> headerAuth,
		@RequestParam() Optional<Integer> nb,
		@RequestParam() Optional<String> like
	) {

		User user = null;
		if (headerAuth.isPresent()) {
			String email = jwtUtils.getEmailFromJwtToken(headerAuth.get().split(" ")[1]);
			if (userRepository.findByEmail(email).isPresent()) {
				user = userRepository.findByEmail(email).get();
			}
		}

		List<SpectacleResponse> spectacleTickets;
		if (like.isPresent())
			spectacleTickets = spectacleService.getAllSpectacleTicketsWithoutSeller(user,null, "%" + like.get() + "%");
		else if (nb.isPresent())
			spectacleTickets = spectacleService.getAllSpectacleTicketsWithoutSeller(user, nb.get(), null);
		else
			spectacleTickets = spectacleService.getAllSpectacleTicketsWithoutSeller(user, null, null);

		if (spectacleTickets != null) {
			return ResponseEntity.status(HttpStatus.OK).body(spectacleTickets);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Result is empty !"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getSpectacleTicketById(@PathVariable("id") Long spectacleId) {
		SpectacleResponse spectacleResponse = spectacleService.getSpectacleTicketById(spectacleId);
		if (spectacleResponse != null) {
			return ResponseEntity.status(HttpStatus.OK).body(spectacleResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
		}
	}

	@PostMapping("")
	public ResponseEntity<?> createSpectacleTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth, @RequestBody SpectacleRequest spectacleReq) {

		try {
			Spectacle newSpectacleTicket = new Spectacle();

			newSpectacleTicket.setDescription(spectacleReq.getDescription());

			if (spectacleReq.getName() != null && !spectacleReq.getName().isEmpty()) {
				newSpectacleTicket.setName(spectacleReq.getName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The name cannot be empty !"));
			}
			if (spectacleReq.getPrice() != null) {
				newSpectacleTicket.setPrice(spectacleReq.getPrice());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The price cannot be empty !"));
			}
			if (spectacleReq.getStartDate() != null && spectacleReq.getEndDate() != null) {
				if (spectacleReq.getStartDate().isAfter(spectacleReq.getEndDate())) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new MessageResponse("The start date cannot be after the end date !"));
				}
				newSpectacleTicket.setStartDate(spectacleReq.getStartDate());
				newSpectacleTicket.setEndDate(spectacleReq.getEndDate());

			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The start date and the end date cannot be empty !"));
			}
			Address address = new Address();
			if (spectacleReq.getAddressName() != null && !spectacleReq.getAddressName().isEmpty()) {
				address.setName(spectacleReq.getAddressName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address name cannot be empty !"));
			}
			if (spectacleReq.getAddressZipcode() != null && !spectacleReq.getAddressZipcode().isEmpty()) {
				address.setZipcode(spectacleReq.getAddressZipcode());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address zipcode cannot be empty !"));
			}
			newSpectacleTicket.setAddress(address);
			if (spectacleReq.getSpectacleCategoryId() != null) {
				Optional<SpectacleCategory> optSpectacleCategory = spectacleCategoryService
						.getSpectacleCategoryById(spectacleReq.getSpectacleCategoryId());
				if (optSpectacleCategory.isPresent()) {
					SpectacleCategory spectacleCategory = optSpectacleCategory.get();
					newSpectacleTicket.setSpectacleCategory(spectacleCategory);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(new MessageResponse("Category not found !"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The category cannot be empty !"));
			}

			String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
			if (userRepository.findByEmail(email).isPresent()) {
				User user = userRepository.findByEmail(email).get();
				newSpectacleTicket.setSeller(user);
			}

			SpectacleResponse spectacleResponse = spectacleService.createSpectacleTicket(newSpectacleTicket);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new MessageResponse("Ticket registered successfully !"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.internalServerError()
					.body(new MessageResponse("Problem encountred during creation !"));
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateSpectacleTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth, @PathVariable("id") Long idSpectacleTicket,
			@RequestBody SpectacleRequest spectacleReq) {
		try {
			Optional<Spectacle> spectacle = spectacleRepository.findById(idSpectacleTicket);
			if (spectacle.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
			}
			Spectacle updatedSpectacleTicket = spectacle.get();

			updatedSpectacleTicket.setDescription(spectacleReq.getDescription());


			if (spectacleReq.getName() != null && !spectacleReq.getName().isEmpty()) {
				updatedSpectacleTicket.setName(spectacleReq.getName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The name cannot be empty !"));
			}
			if (spectacleReq.getPrice() != null) {
				updatedSpectacleTicket.setPrice(spectacleReq.getPrice());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The price cannot be empty !"));
			}
			if (spectacleReq.getStartDate() != null && spectacleReq.getEndDate() != null) {
				if (spectacleReq.getStartDate().isAfter(spectacleReq.getEndDate())) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new MessageResponse("The start date cannot be after the end date !"));
				}
				updatedSpectacleTicket.setStartDate(spectacleReq.getStartDate());
				updatedSpectacleTicket.setEndDate(spectacleReq.getEndDate());

			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The start date and the end date cannot be empty !"));
			}
			Address address = new Address();
			if (spectacleReq.getAddressName() != null && !spectacleReq.getAddressName().isEmpty()) {
				address.setName(spectacleReq.getAddressName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address name cannot be empty !"));
			}
			if (spectacleReq.getAddressZipcode() != null && !spectacleReq.getAddressZipcode().isEmpty()) {
				address.setZipcode(spectacleReq.getAddressZipcode());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address zipcode name cannot be empty !"));
			}
			updatedSpectacleTicket.setAddress(address);
			if (spectacleReq.getSpectacleCategoryId() != null) {
				Optional<SpectacleCategory> optSpectacleCategory = spectacleCategoryService
						.getSpectacleCategoryById(spectacleReq.getSpectacleCategoryId());
				if (optSpectacleCategory.isPresent()) {
					SpectacleCategory spectacleCategory = optSpectacleCategory.get();
					updatedSpectacleTicket.setSpectacleCategory(spectacleCategory);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(new MessageResponse("Category not found !"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The category cannot be empty !"));
			}

			String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
			if (userRepository.findByEmail(email).isPresent()) {
				User user = userRepository.findByEmail(email).get();
				updatedSpectacleTicket.setSeller(user);
			}

			spectacleService.createSpectacleTicket(updatedSpectacleTicket);
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Ticket updated successfully !"));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(new MessageResponse("Problem encoured during updating !"));
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSpectacleTicketById(@PathVariable("id") Long idSpectacleTicket) {
		Optional<Spectacle> spectacle = spectacleRepository.findById(idSpectacleTicket);
		if (spectacle.isPresent()) {
			spectacleService.deleteSpectacleTicketById(idSpectacleTicket);
			return ResponseEntity.ok(new MessageResponse("Ticket deleted successfully !"));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
		}
	}
}
