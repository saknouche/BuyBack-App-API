package com.projet.buyback.service.spectacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.projet.buyback.model.User;
import com.projet.buyback.model.sport.Sport;
import com.projet.buyback.schema.response.sport.SportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.projet.buyback.model.spectacle.Spectacle;
import com.projet.buyback.repository.spectacle.SpectacleRepository;
import com.projet.buyback.schema.response.spectacle.SpectacleResponse;

@Service
public class SpectacleService {

	@Autowired
	private SpectacleRepository spectacleRepository;

	public List<SpectacleResponse> getAllSpectacleTickets() {
		List<Spectacle> spectacleTickets = spectacleRepository.findAll();
		if(!spectacleTickets.isEmpty()) {
			
			List<SpectacleResponse> spectacleTicketsDto = new ArrayList<>();
			
			for (Spectacle spectacle : spectacleTickets) {
				spectacleTicketsDto.add(SpectacleResponse.createSpectacleResponse(spectacle));
			}
			return spectacleTicketsDto;
		}else {
			return null;
		}
	}

	public List<SpectacleResponse> getAllSpectacleTicketsWithoutSeller(User user, Integer limit, String like) {

//		List<Sport> sportTickets = sportRepository.findAll(PageRequest.of(0,limit)).stream().toList();
		List<Spectacle> spectacleTickets;
		if (limit != null)
			spectacleTickets = spectacleRepository.findAllBySellerIsNullOrSellerIsNotAndPurchaserIsNullOrderByStartDateAsc(
				user,
				PageRequest.of(0, limit)
			).stream().toList();
		else if(like != null)
			spectacleTickets = spectacleRepository.findAllBySellerIsNullOrSellerIsNotAndPurchaserIsNullOrderByStartDateAsc(
				user,
				like
			).stream().toList();
		else
			spectacleTickets = spectacleRepository.findAllBySellerIsNullOrSellerIsNotAndPurchaserIsNullOrderByStartDateAsc(
				user
			).stream().toList();


		if(!spectacleTickets.isEmpty()) {
			List<SpectacleResponse> spectacleTicketsDto = new ArrayList<>();
			for (Spectacle spectacle : spectacleTickets) {
				spectacleTicketsDto.add(SpectacleResponse.createSpectacleResponse(spectacle));
			}
			return spectacleTicketsDto;
		}else {
			return null;
		}
	}

	public SpectacleResponse getSpectacleTicketById(Long id) {
		Optional<Spectacle> optSpectacleTicket = spectacleRepository.findById(id);
		if (optSpectacleTicket.isPresent()) {
			Spectacle spectacleTicket = optSpectacleTicket.get();
			return SpectacleResponse.createSpectacleResponse(spectacleTicket);
		} else {
			return null;
		}
	}

	public SpectacleResponse createSpectacleTicket(Spectacle spectacleTicket) {
		Spectacle savedSpectacle = spectacleRepository.save(spectacleTicket);
        return SpectacleResponse.createSpectacleResponse(savedSpectacle);
    }

	public void deleteSpectacleTicketById(Long id) {
		spectacleRepository.deleteById(id);

	}
}
