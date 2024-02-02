package game_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import game_tracker.exception.ResourceNotFoundException;
import game_tracker.model.Deck;
import game_tracker.model.Game;
import game_tracker.service.DeckService;
import game_tracker.service.GameService;

@RestController
@RequestMapping("/api")
public class GameController {

	@Autowired
	GameService service;
	
	@Autowired
	DeckService deckService;
	
	@GetMapping("/game")
	public List<Game> getAllGames()  {
		return service.getAllGames();
	}
	
	@GetMapping("/game/id")
	public ResponseEntity<?> getGameById(@RequestParam int id) throws ResourceNotFoundException  {
		Game found = service.getGameById(id);
		return ResponseEntity.status(200).body(found);
	}
	
	@PostMapping("/game")
	public ResponseEntity<?> createGame(@RequestBody Game game) throws ResourceNotFoundException {

		// TODO: Figure out if this needs to stay or be removed
		// Validate that winnerName is one of the deck names
		
		Deck deck4 = null;
		if (game.getDeck4().getId() != null) {
			deck4 = deckService.getDeckById(game.getDeck4().getId());
		}
		
		Game created = new Game(
				null,
				game.getDate(),
				game.getGameNumber(),
				deckService.getDeckById(game.getDeck1().getId()),
				deckService.getDeckById(game.getDeck2().getId()),
				deckService.getDeckById(game.getDeck3().getId()),
				deck4,
				deckService.getDeckById(game.getWinner().getId()));
		System.out.println("DATE: " + game.getDate());
		try {
			service.createGame(created);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.status(201).body(created);
	}
	
	// TODO: Probably need input validation similar to creating a game
	@PutMapping("/game")
	public ResponseEntity<?> updateGame(@RequestBody Game game) throws ResourceNotFoundException {
		Game updated = service.updateGame(game);
		return ResponseEntity.status(200).body(updated);
	}
	
	@DeleteMapping("/game/{id}")
	public ResponseEntity<?> deleteGameById(@PathVariable int id) throws ResourceNotFoundException {
		Game deleted = service.deleteGameById(id);
		return ResponseEntity.status(200).body(deleted);
	}
}
