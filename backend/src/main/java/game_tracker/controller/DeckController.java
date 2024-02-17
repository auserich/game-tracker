package game_tracker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import game_tracker.exception.ResourceAlreadyExistsException;
import game_tracker.exception.ResourceNotFoundException;
import game_tracker.exception.UsernameNotFoundException;
import game_tracker.model.Commander;
import game_tracker.model.Deck;
import game_tracker.model.Player;
import game_tracker.service.CommanderService;
import game_tracker.service.DeckService;
import game_tracker.service.PlayerService;

@RestController
@RequestMapping("/api")
public class DeckController {
	
	@Autowired
	DeckService service;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	CommanderService commanderService;
	
	@GetMapping("/deck")
	public List<Deck> getAllDecks() {
		return service.getAllDecks();
	}
	
	@GetMapping("/deck/id")
	public ResponseEntity<Deck> getDeckById(@RequestParam int id) throws ResourceNotFoundException {
		Deck found = service.getDeckById(id);
		return ResponseEntity.status(200).body(found);
	}
	
	@GetMapping("/deck/name/{name}")
	public ResponseEntity<Deck> getDeckByName(@PathVariable String name) throws ResourceNotFoundException {
		Deck found = service.getDeckByName(name);
		return ResponseEntity.status(200).body(found);
	}
	
	@GetMapping("/deck/playerId")
	public List<Deck> getAllDecksFromPlayerById(@RequestParam int playerId) throws ResourceNotFoundException {
		Player found = playerService.getPlayerById(playerId);
		return service.getAllDecksFromPlayerById(found.getId());
	}
	
	@GetMapping("/deck/playerName")
	public List<Deck> getAllDecksFromUserByPlayerName(@RequestParam String playerName) throws ResourceNotFoundException, UsernameNotFoundException {
		Player found = playerService.getPlayerByName(playerName);
		return service.getAllDecksFromPlayerByPlayerName(found.getName());
	}
	
	@GetMapping("/deck/popular/player/{id}")
	public List<Deck> getAllDecksFromPlayerOrderByMostPlayed(@PathVariable int id) throws ResourceNotFoundException {
		Player found = playerService.getPlayerById(id);
		return service.getAllDecksFromPlayerOrderedByMostPlayed(found.getId());
	}
	
	@GetMapping("/deck/player/commander")
	public ResponseEntity<Deck> getDeckByPlayerAndCommander(@RequestParam int playerId, @RequestParam int commanderId) throws ResourceNotFoundException {
		Player foundPlayer = playerService.getPlayerById(playerId);
		Commander foundCommander = commanderService.getCommanderById(commanderId);
		Deck found = service.getDeckByPlayerAndCommander(foundPlayer.getId(), foundCommander.getId());
		return ResponseEntity.status(200).body(found);
	}
	
	@GetMapping("/deck/wins/name")
	public Integer getWinsByDeckName(@RequestParam String name) throws ResourceNotFoundException  {
		Deck found = service.getDeckByName(name);
		return service.getWinsByDeckId(found.getId());
	}
	
	@GetMapping("deck/losses/name")
	public Integer getLossesByDeckName(@RequestParam String name) throws ResourceNotFoundException {
		Deck found = service.getDeckByName(name);
		return service.getLossesByDeckId(found.getId());
	}
	
	@GetMapping("deck/games/name")
	public Integer getGameCountByDeckName(@RequestParam String name) throws ResourceNotFoundException {
		Deck found = service.getDeckByName(name);
		return service.getGameCountByDeckId(found.getId());
	}
	
	@GetMapping("deck/winrate/{name}")
	public Double getWinRateByDeckName(@PathVariable String name) throws ResourceNotFoundException {
		Deck found = service.getDeckByName(name);
		Integer wins = service.getWinsByDeckId(found.getId());
		Integer gameCount = service.getGameCountByDeckId(found.getId());
		return (double) wins / gameCount;
	}
	
	@GetMapping("deck/winstreak")
	public Integer getHighestWinStreakByDeckName(@RequestParam String name) throws ResourceNotFoundException {
		Deck found = service.getDeckByName(name);
		return service.getHighestWinStreakByDeckId(found.getId());
	}
	
	@GetMapping("deck/losestreak")
	public Integer getHighestLoseStreakByDeckName(@RequestParam String name) throws ResourceNotFoundException {
		Deck found = service.getDeckByName(name);
		return service.getHighestLoseStreakByDeckName(found.getName());
	}
	
	@GetMapping("deck/winrate")
	public HashMap<Integer, Double> getAllDecksOrderedByWinrate() throws ResourceNotFoundException {
		List<Deck> decks = service.getAllDecks();
		HashMap<Integer, Double> winrates = new HashMap<>();
		for (Deck deck : decks) {
			Integer deckWins = service.getWinsByDeckId(deck.getId());
			Integer gameCount = service.getGameCountByDeckId(deck.getId());
			Double winrate = (double) deckWins / gameCount;
			winrates.put(deck.getId(), winrate);
		}
		return winrates;
	}
	
	@GetMapping("deck/player/{name}/winrate")
	public HashMap<String, Double> getAllDecksFromPlayerOrderByWinRate(@PathVariable String name) throws UsernameNotFoundException, ResourceNotFoundException {
		Player found = playerService.getPlayerByName(name);
		List<Deck> decks = service.getAllDecksFromPlayerById(found.getId());
		HashMap<String, Double> winrates = new HashMap<>();
		for (Deck deck : decks) {
			Integer deckWins = service.getWinsByDeckId(deck.getId());
			Integer gameCount = service.getGameCountByDeckId(deck.getId());
			Double winrate = (double) deckWins / gameCount;
			winrates.put(deck.getName(), winrate);
		}
		return winrates;
	}
	
	@PostMapping("/deck")
	public ResponseEntity<Deck> createDeck(@RequestParam String playerName, @RequestParam String commanderName, @RequestParam String deckName) throws ResourceNotFoundException, ResourceAlreadyExistsException, UsernameNotFoundException {
		Player foundPlayer = playerService.getPlayerByName(playerName);
		Commander foundCommander = commanderService.getCommanderByName(commanderName);
		Deck created = service.createDeck(foundPlayer, foundCommander, deckName);
		return ResponseEntity.status(201).body(created);
	}
	
	@PutMapping("/deck")
	public ResponseEntity<Deck> updateDeck(@RequestBody Deck deck) throws ResourceNotFoundException {
		// Ensure user/commander associated with deck exists
		playerService.getPlayerById(deck.getPlayer().getId());
		commanderService.getCommanderById(deck.getCommander().getId());
		
		Deck updated = service.updateDeck(deck);
		return ResponseEntity.status(200).body(updated);
	}
	
	@DeleteMapping("/deck/id")
	public ResponseEntity<Deck> deleteDeckById(@RequestParam int id) throws ResourceNotFoundException {
		Deck deleted = service.deleteDeckById(id);
		return ResponseEntity.status(200).body(deleted);
	}
	
	@DeleteMapping("/deck/name")
	public ResponseEntity<Deck> deleteDeckByName(@RequestParam String name) throws ResourceNotFoundException {
		Deck deleted = service.deleteDeckByName(name);
		return ResponseEntity.status(200).body(deleted);
	}
}
