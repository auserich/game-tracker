package game_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game_tracker.exception.ResourceAlreadyExistsException;
import game_tracker.exception.ResourceNotFoundException;
import game_tracker.exception.ValidationException;
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
	
	public List<Deck> getAllDecksFromUser(int userId) {
		return repo.getAllDecksFromUserById(userId);
	}
	
	public Deck getDeckByUserAndCommander(int userId, int commanderId) {
		return repo.getDeckByUserAndCommander(userId, commanderId);
	}
	
	public Deck createDeck(User user, Commander commander, String name) throws ResourceAlreadyExistsException {
		
		//TODO: Instead, get user and commander separately. Then check if commander is in database or needs to be searched via API
		Deck found = repo.getDeckByUserAndCommander(user.getId(), commander.getId());
		if (found != null) {
			throw new ResourceAlreadyExistsException("Deck", found.getId());
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
	
	public Deck deleteDeck(int id) throws ResourceNotFoundException {
		Optional<Deck> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", id);
		}
		repo.deleteById(id);
		return found.get();
	}
}
