package com.projet.buyback.controller.sport;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projet.buyback.model.Address;
import com.projet.buyback.model.sport.Sport;
import com.projet.buyback.model.sport.SportCategory;
import com.projet.buyback.model.User;
import com.projet.buyback.repository.sport.SportRepository;
import com.projet.buyback.repository.UserRepository;
import com.projet.buyback.schema.response.sport.SportResponse;
import com.projet.buyback.schema.request.sport.SportRequest;
import com.projet.buyback.schema.response.security.MessageResponse;
import com.projet.buyback.service.sport.SportCategoryService;
import com.projet.buyback.service.sport.SportService;
import com.projet.buyback.utils.security.JwtUtils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.baseURL}/sports")
public class SportController {

	@Autowired
	private SportService sportService;
	@Autowired
	private SportCategoryService sportCategoryService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SportRepository sportRepository;
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

		List<SportResponse> sportTickets;
		if (like.isPresent())
			sportTickets = sportService.getAllSportTicketsWithoutSeller(user,null, "%" + like.get() + "%");
		else if (nb.isPresent())
			sportTickets = sportService.getAllSportTicketsWithoutSeller(user, nb.get(), null);
		else
			sportTickets = sportService.getAllSportTicketsWithoutSeller(user, null, null);


		if (sportTickets != null) {
			return ResponseEntity.status(HttpStatus.OK).body(sportTickets);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("Result is empty !"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getSportTicketById(@PathVariable("id") Long sportId) {
		SportResponse sportResponse = sportService.getSportTicketById(sportId);
		if (sportResponse != null) {
			return ResponseEntity.status(HttpStatus.OK).body(sportResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
		}
	}

	@PostMapping("")
	public ResponseEntity<?> createSportTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth, @RequestBody SportRequest sportReq) {
		try {
			Sport newSportTicket = new Sport();

			newSportTicket.setDescription(sportReq.getDescription());


			if (sportReq.getName() != null && !sportReq.getName().isEmpty()) {
				newSportTicket.setName(sportReq.getName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The name cannot be empty !"));
			}


			if (sportReq.getPrice() != null) {
				newSportTicket.setPrice(sportReq.getPrice());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The price cannot be empty !"));
			}

			if (sportReq.getStartDate() != null && sportReq.getEndDate() != null) {
				if (sportReq.getStartDate().isAfter(sportReq.getEndDate())) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new MessageResponse("The start date cannot be after the end date !"));
				}
				newSportTicket.setStartDate(sportReq.getStartDate());
				newSportTicket.setEndDate(sportReq.getEndDate());

			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The start date and the end date cannot be empty !"));
			}

			if (sportReq.getEndDate() != null) {
				newSportTicket.setEndDate(sportReq.getEndDate());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The end date cannot be empty !"));
			}

			Address address = new Address();
			if (sportReq.getAddressName() != null && !sportReq.getAddressName().isEmpty()) {
				address.setName(sportReq.getAddressName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address name cannot be empty !"));
			}

			if (sportReq.getAddressZipcode() != null && !sportReq.getAddressZipcode().isEmpty()) {
				address.setZipcode(sportReq.getAddressZipcode());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address zipcode cannot be empty !"));
			}

			newSportTicket.setAddress(address);

			if (sportReq.getSportCategoryId() != null) {
				Optional<SportCategory> optSportCategory = sportCategoryService
						.getSportCategoryById(sportReq.getSportCategoryId());
				if (optSportCategory.isPresent()) {
					SportCategory sportCategory = optSportCategory.get();
					newSportTicket.setSportCategory(sportCategory);
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
				newSportTicket.setSeller(user);
			}

			SportResponse sportResponse = sportService.createSportTicket(newSportTicket);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new MessageResponse("Ticket registered successfully !"));

		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError()
					.body(new MessageResponse("Problem encountred during creation !"));
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateSportTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth, @PathVariable("id") Long idSportTicket,
											   @RequestBody SportRequest sportReq) {

		try {
			Optional<Sport> sport = sportRepository.findById(idSportTicket);
			if (sport.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
			}
			Sport updatedSportTicket = sport.get();

			updatedSportTicket.setDescription(sportReq.getDescription());


			if (sportReq.getName() != null && !sportReq.getName().isEmpty()) {
				updatedSportTicket.setName(sportReq.getName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The name cannot be empty !"));
			}
			if (sportReq.getPrice() != null) {
				updatedSportTicket.setPrice(sportReq.getPrice());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The price cannot be empty !"));
			}
			if (sportReq.getStartDate() != null && sportReq.getEndDate() != null) {
				if (sportReq.getStartDate().isAfter(sportReq.getEndDate())) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new MessageResponse("The start date cannot be after the end date !"));
				}
				updatedSportTicket.setStartDate(sportReq.getStartDate());
				updatedSportTicket.setEndDate(sportReq.getEndDate());

			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The start date and the end date cannot be empty !"));
			}
			Address address = new Address();
			if (sportReq.getAddressName() != null) {
				address.setName(sportReq.getAddressName());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address name cannot be empty !"));
			}
			if (sportReq.getAddressZipcode() != null && !sportReq.getAddressName().isEmpty()) {
				address.setZipcode(sportReq.getAddressZipcode());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("The address zipcode cannot be empty !"));
			}
			updatedSportTicket.setAddress(address);
			if (sportReq.getSportCategoryId() != null && !sportReq.getAddressZipcode().isEmpty()) {
				Optional<SportCategory> optSportCategory = sportCategoryService
						.getSportCategoryById(sportReq.getSportCategoryId());
				if (optSportCategory.isPresent()) {
					SportCategory sportCategory = optSportCategory.get();
					updatedSportTicket.setSportCategory(sportCategory);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(new MessageResponse("Category not found !"));
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse("Category cannot be empty !"));
			}

			String email = jwtUtils.getEmailFromJwtToken(headerAuth.split(" ")[1]);
			if (userRepository.findByEmail(email).isPresent()) {
				User user = userRepository.findByEmail(email).get();
				updatedSportTicket.setSeller(user);
			}

			sportService.createSportTicket(updatedSportTicket);
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Ticket updated successfully !"));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.internalServerError().body(new MessageResponse("Problem encoured during updating !"));
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSportTicketById(@PathVariable("id") Long idSportTicket) {
		Optional<Sport> sport = sportRepository.findById(idSportTicket);
		if (sport.isPresent()) {
			sportService.deleteSportTicket(idSportTicket);
			return ResponseEntity.ok(new MessageResponse("Ticket deleted successfully !"));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Ticket not found !"));
		}
	}
}
