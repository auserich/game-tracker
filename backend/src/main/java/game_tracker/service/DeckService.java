package game_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game_tracker.exception.ResourceAlreadyExistsException;
import game_tracker.exception.ResourceNotFoundException;
import game_tracker.model.Commander;
import game_tracker.model.Deck;
import game_tracker.model.Game;
import game_tracker.model.Player;
import game_tracker.repository.DeckRepository;
import game_tracker.repository.GameRepository;

@Service
public class DeckService {
	
	@Autowired
	DeckRepository repo;
	
	@Autowired
	GameRepository gameRepository;
	
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
	
	public List<Deck> getAllDecksFromPlayerById(int playerId) {
		return repo.getAllDecksFromPlayerById(playerId);
	}
	
	public List<Deck> getAllDecksFromPlayerByPlayerName(String playerName) {
		return repo.getAllDecksFromPlayerByPlayerName(playerName);
	}
	
	public Deck getDeckByPlayerAndCommander(int playerId, int commanderId) throws ResourceNotFoundException {
		Optional<Deck> found = repo.getDeckByPlayerAndCommander(playerId, commanderId);
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
	
	public Integer getHighestWinStreakByDeckName(String name) throws ResourceNotFoundException {
		Optional<Deck> found = repo.findByName(name);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", -1);
		}
		
		List<Game> games = gameRepository.getOrderedGamesByDeckName(name);
		int currentWinStreak = 0;
		int highestWinStreak = 0;
		
		for (Game game : games) {
			if (name.equals(game.getWinner().getName()) ) {
				currentWinStreak++;
				highestWinStreak = Math.max(highestWinStreak, currentWinStreak);
			} else {
				currentWinStreak = 0;
			}
		}
		
		return highestWinStreak;
	}
	
	public Integer getHighestLoseStreakByDeckName(String name) throws ResourceNotFoundException {
		Optional<Deck> found = repo.findByName(name);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Deck", -1);
		}
		
		List<Game> games = gameRepository.getOrderedGamesByDeckName(name);
		int currentLoseStreak = 0;
		int highestLoseStreak = 0;
		
		for (Game game : games) {
			if (name.equals(game.getWinner().getName()) ) {
				currentLoseStreak = 0;
			} else {
				currentLoseStreak++;
				highestLoseStreak = Math.max(highestLoseStreak, currentLoseStreak);
			}
		}
		
		return highestLoseStreak;
	}
	
	public Deck createDeck(Player player, Commander commander, String name) throws ResourceAlreadyExistsException {
		
		//TODO: Instead, get player and commander separately. Then check if commander is in database or needs to be searched via API
		Optional<Deck> found = repo.getDeckByPlayerAndCommander(player.getId(), commander.getId());
		if (found.isPresent()) {
			throw new ResourceAlreadyExistsException("Deck", found.get().getId());
		}
		Deck created = new Deck(null, player, commander, name);
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
