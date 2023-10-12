package com.projet.buyback.service.sport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.projet.buyback.model.User;
import com.projet.buyback.model.sport.Sport;
import com.projet.buyback.repository.sport.SportRepository;
import com.projet.buyback.schema.response.sport.SportResponse;

@Service
public class SportService {

	@Autowired
	private SportRepository sportRepository;

	public List<SportResponse> getAllSportTickets() {
		List<Sport> sportTickets = sportRepository.findAll();
		if (!sportTickets.isEmpty()) {

			List<SportResponse> sportTicketsDto = new ArrayList<>();

			for (Sport sport : sportTickets) {
				sportTicketsDto.add(SportResponse.createSportResponse(sport));
			}
			return sportTicketsDto;
		} else {
			return null;
		}
	}

	public List<SportResponse> getAllSportTicketsWithoutSeller(User user, Integer limit, String like) {

//		List<Sport> sportTickets = sportRepository.findAll(PageRequest.of(0,limit)).stream().toList();
		List<Sport> sportTickets;
		if (limit != null)
			sportTickets = sportRepository.findAllBySellerIsNullOrSellerIsNotAndPurchaserIsNullOrderByStartDateAsc(
				user,
				PageRequest.of(0, limit)
			).stream().toList();
		else if(like != null)
			sportTickets = sportRepository.findAllBySellerIsNullOrSellerIsNotAndPurchaserIsNullOrderByStartDateAsc(
				user,
				like
			).stream().toList();
		else
			sportTickets = sportRepository.findAllBySellerIsNullOrSellerIsNotAndPurchaserIsNullOrderByStartDateAsc(
				user
			).stream().toList();


		if(!sportTickets.isEmpty()) {
			List<SportResponse> sportTicketsDto = new ArrayList<>();
			for (Sport sport : sportTickets) {
				sportTicketsDto.add(SportResponse.createSportResponse(sport));
			}
			return sportTicketsDto;
		}else {
			return null;
		}
	}

	public SportResponse getSportTicketById(Long id) {
		Optional<Sport> optSportTicket = sportRepository.findById(id);
		if (optSportTicket.isPresent()) {
			Sport sportTicket = optSportTicket.get();
			return SportResponse.createSportResponse(sportTicket);
		} else {
			return null;
		}
	}

	public SportResponse createSportTicket(Sport sportTicket) {
		Sport savedSport = sportRepository.save(sportTicket);
		return SportResponse.createSportResponse(savedSport);
    }

	public void deleteSportTicket(Long id) {
		sportRepository.deleteById(id);
	}
}
