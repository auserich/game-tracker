package game_tracker.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<?> createGame(
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
			@RequestParam Integer gameNumber,
			@RequestParam String deckName1,
			@RequestParam String deckName2,
			@RequestParam String deckName3,
			@RequestParam(required = false) String deckName4,
			@RequestParam String winnerName
			) throws ResourceNotFoundException {
		
		Deck deck4 = null;
		try {
		    if (deckName4 != null) {
		        deck4 = deckService.getDeckByName(deckName4);
		    }
		} catch (ResourceNotFoundException e) {
			// TODO: Figure out what to do here
		    // Do nothing or log the exception (based on your preference)
		    // You can log the exception for debugging purposes
		    // logger.error("Deck not found for deckName4: {}", deckName4);
		}
		
		Game created = new Game(
				null,
				date,
				gameNumber,
				deckService.getDeckByName(deckName1),
				deckService.getDeckByName(deckName2),
				deckService.getDeckByName(deckName3),
				deck4,
				deckService.getDeckByName(winnerName));
		
		service.createGame(created);
		return ResponseEntity.status(201).body(created);
	}
}
