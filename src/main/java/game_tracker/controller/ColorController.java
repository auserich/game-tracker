package game_tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import game_tracker.exception.ResourceNotFoundException;
import game_tracker.model.Color;
import game_tracker.service.ColorService;

@RestController
@RequestMapping("/api")
public class ColorController {
	
	@Autowired
	ColorService service;
	
	@GetMapping("/color")
	public List<Color> getAllColors() {
		return service.getAllColors();
	}
	
	@GetMapping("/color/{id}")
	public ResponseEntity<?> getColorById(@PathVariable int id) throws ResourceNotFoundException {
		Color found = service.getColorById(id);
		return ResponseEntity.status(200).body(found);
	}
	
	@PostMapping("/color")
	public ResponseEntity<?> createColor(@RequestBody Color color) {
		Color created = service.createColor(color);
		return ResponseEntity.status(201).body(created);
	}
	
	@PutMapping("/color")
	public ResponseEntity<?> updateColor(@RequestBody Color color) throws ResourceNotFoundException {
		Color updated = service.updateColor(color);
		return ResponseEntity.status(200).body(updated);
	}
	
	@DeleteMapping("/color/{id}")
	public ResponseEntity<?> deleteColor(@PathVariable int id) throws ResourceNotFoundException {
		Color deleted = service.deleteColorById(id);
		return ResponseEntity.status(200).body(deleted);
	}
}
