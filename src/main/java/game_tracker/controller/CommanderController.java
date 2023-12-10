package game_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import game_tracker.exception.ResourceNotFoundException;
import game_tracker.model.Commander;
import game_tracker.service.CommanderService;

@RestController
@RequestMapping("/api")
public class CommanderController {
	
	@Autowired
	CommanderService service;
	
	@GetMapping("/commander")
	public List<Commander> getAllCommanders() {
		return service.getAllCommanders();
	}
	
	@GetMapping("/commander/search/{name}")
	public ResponseEntity<?> getCommandersBySearch(@PathVariable String name) {
		String apiUrl = "https://api.magicthegathering.io/v1/cards?name=" + name;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
		String responseBody = response.getBody();
		return ResponseEntity.status(response.getStatusCode()).body(responseBody);
	}
	
	@GetMapping("/commander/{id}")
	public ResponseEntity<?> getCommanderById(@PathVariable int id) throws ResourceNotFoundException {
		Commander found = service.getCommanderById(id);
		return ResponseEntity.status(200).body(found);
	}
}
