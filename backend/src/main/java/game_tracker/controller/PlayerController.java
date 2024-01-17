package game_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import game_tracker.exception.UsernameNotFoundException;
import game_tracker.exception.UsernameTakenException;
import game_tracker.model.Player;
import game_tracker.model.User;
import game_tracker.service.PlayerService;
import game_tracker.service.UserService;

@RestController
@RequestMapping("/api")
public class PlayerController {

	@Autowired
	PlayerService service;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/player")
	public List<Player> getAllPlayers() {
		return service.getAllPlayers();
	}
	
	@GetMapping("/player/user")
	public List<Player> getAllPlayersFromUser() throws UsernameNotFoundException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User found = userService.getUserByUsername(username);
		return service.getAllPlayersFromUser(found.getId());
	}
	
	@GetMapping("/player/{id}")
	public ResponseEntity<?> getPlayerById(@PathVariable int id) throws ResourceNotFoundException {
		Player found = service.getPlayerById(id);
		return ResponseEntity.status(200).body(found);
	}
	
	@GetMapping("/player/name")
	public ResponseEntity<?> getPlayerByName(@RequestParam String name) throws UsernameNotFoundException {
		Player found = service.getPlayerByName(name);
		return ResponseEntity.status(200).body(found);
	}
	
	@PostMapping("/player")
	public ResponseEntity<?> createPlayer(@RequestParam String name) throws UsernameNotFoundException, UsernameTakenException {
		// Extract the User information from the authentication context
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User found = userService.getUserByUsername(username);
		
		Player created = service.createPlayer(name, found);
		return ResponseEntity.status(201).body(created);
	}
	
	@PutMapping("/player")
	public ResponseEntity<?> updatePlayer(@RequestBody Player player) throws ResourceNotFoundException {
		Player updated = service.updatePlayer(player);
		return ResponseEntity.status(200).body(updated);
	}
	
	@DeleteMapping("/player/id")
	public ResponseEntity<?> deletePlayerById(@RequestParam int id) throws ResourceNotFoundException {
		Player deleted = service.deletePlayerById(id);
		return ResponseEntity.status(200).body(deleted);
	}
	
	// TODO: Make it so it only deletes the name specified by a user
	@DeleteMapping("/player/name")
	public ResponseEntity<?> deletePlayerByName(@RequestParam String name) throws ResourceNotFoundException {
		Player deleted = service.deletePlayerByName(name);
		return ResponseEntity.status(200).body(deleted);
	}
}
