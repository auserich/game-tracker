package game_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game_tracker.exception.ResourceAlreadyExistsException;
import game_tracker.exception.ResourceNotFoundException;
import game_tracker.model.Commander;
import game_tracker.model.Deck;
import game_tracker.model.User;
import game_tracker.repository.DeckRepository;

@Service
public class DeckService {
	
	@Autowired
	DeckRepository repo;
	
	public List<Deck> getAllDecks() {
		return repo.findAll();
	}
	
	public Deck getDeckById(int id) throws ResourceNotFoundException {
		Optional<Deck> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", id);
		}
		return found.get();
	}
	
	public Deck getDeckByName(String name) throws ResourceNotFoundException {
		Optional<Deck> found = repo.findByName(name);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", -1);
		}
		return found.get();
	}
	
	public List<Deck> getAllDecksFromUserById(int userId) {
		return repo.getAllDecksFromUserById(userId);
	}
	
	public List<Deck> getAllDecksFromUserByUsername(String username) {
		return repo.getAllDecksFromUserByUsername(username);
	}
	
	public Deck getDeckByUserAndCommander(int userId, int commanderId) throws ResourceNotFoundException {
		Optional<Deck> found = repo.getDeckByUserAndCommander(userId, commanderId);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", -1);
		}
		return found.get();
	}
	
	public Integer getWinsByDeckName(String name) throws ResourceNotFoundException {
		Optional<Deck> found = repo.findByName(name);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", -1);
		}
		return repo.countWinsForDeckByName(name);
	}
	
	// TODO: Figure out why countLossesForDeckByName isn't working and call that instead
	public Integer getLossesByDeckName(String name) throws ResourceNotFoundException {
		Optional<Deck> found = repo.findByName(name);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", -1);
		}
		
		/* return repo.countLossesForDeckByName(name); */
		
		Integer totalGames = repo.countTotalGamesForDeckByName(name);
		Integer wins = repo.countWinsForDeckByName(name);
		return totalGames - wins;
	}
	
	public Integer getGameCountByDeckName(String name) throws ResourceNotFoundException {
		Optional<Deck> found = repo.findByName(name);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", -1);
		}
		return repo.countTotalGamesForDeckByName(name);
	}
	
	// TODO: Left off here. Move this to GameService and it's respective function to GameRepo. It's
	// 		 returning a list of Games that a deckname was present in. Autowire GameService and
	//		 call that function from that. Also just add a getOrderedListOfGamesByDeckname or something
	//		 then just call that function from in here since other functions would want that, too
	/*
	 * public List<> getHighestWinstreakByDeckName(String name) {
	 * 
	 * }
	 */
	
	public Deck createDeck(User user, Commander commander, String name) throws ResourceAlreadyExistsException {
		
		//TODO: Instead, get user and commander separately. Then check if commander is in database or needs to be searched via API
		Optional<Deck> found = repo.getDeckByUserAndCommander(user.getId(), commander.getId());
		if (found.isPresent()) {
			throw new ResourceAlreadyExistsException("Deck", found.get().getId());
		}
		Deck created = new Deck(null, user, commander, name);
		return repo.save(created);
	}
	
	public Deck updateDeck(Deck deck) throws ResourceNotFoundException {
		if (repo.existsById(deck.getId())) {
			return repo.save(deck);
		}
		throw new ResourceNotFoundException("Deck", deck.getId());
	}
	
	public Deck deleteDeckById(int id) throws ResourceNotFoundException {
		Optional<Deck> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", id);
		}
		repo.deleteById(id);
		return found.get();
	}
	
	public Deck deleteDeckByName(String name) throws ResourceNotFoundException {
		Optional<Deck> found = repo.findByName(name);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", -1);
		}
		repo.deleteById(found.get().getId());
		return found.get();
	}
}
