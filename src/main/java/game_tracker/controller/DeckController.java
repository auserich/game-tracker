package game_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import game_tracker.model.User;
import game_tracker.service.CommanderService;
import game_tracker.service.DeckService;
import game_tracker.service.UserService;

@RestController
@RequestMapping("/api")
public class DeckController {
	
	@Autowired
	DeckService service;
	
	@Autowired
	UserService userService;
	
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
	
	@GetMapping("/deck/user")
	public List<Deck> getAllDecksFromUser(@RequestParam int userId) throws ResourceNotFoundException {
		User found = userService.getUserById(userId);
		return service.getAllDecksFromUser(found.getId());
	}
	
	@GetMapping("/deck/user/commander")
	public ResponseEntity<Deck> getDeckByUserAndCommander(@RequestParam int userId, @RequestParam int commanderId) throws ResourceNotFoundException {
		User foundUser = userService.getUserById(userId);
		Commander foundCommander = commanderService.getCommanderById(commanderId);
		Deck found = service.getDeckByUserAndCommander(foundUser.getId(), foundCommander.getId());
		return ResponseEntity.status(200).body(found);
	}
	
	@PostMapping("/deck")
	public ResponseEntity<Deck> createDeck(@RequestParam String username, @RequestParam String commanderName, @RequestParam String deckName) throws ResourceNotFoundException, ResourceAlreadyExistsException, UsernameNotFoundException {
		User foundUser = userService.getUserByUsername(username);
		Commander foundCommander = commanderService.getCommanderByName(commanderName);
		Deck created = service.createDeck(foundUser, foundCommander, deckName);
		return ResponseEntity.status(201).body(created);
	}
	
	@PutMapping("/deck")
	public ResponseEntity<Deck> updateDeck(@RequestBody Deck deck) throws ResourceNotFoundException {
		// Ensure user/commander associated with deck exists
		userService.getUserById(deck.getUser().getId());
		commanderService.getCommanderById(deck.getCommander().getId());
		
		Deck updated = service.updateDeck(deck);
		return ResponseEntity.status(200).body(updated);
	}
	
	@DeleteMapping("/deck")
	public ResponseEntity<Deck> deleteDeck(@RequestParam int id) throws ResourceNotFoundException {
		Deck deleted = service.deleteDeck(id);
		return ResponseEntity.status(200).body(deleted);
	}
}
