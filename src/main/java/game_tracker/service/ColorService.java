package game_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game_tracker.exception.ResourceNotFoundException;
import game_tracker.model.Color;
import game_tracker.repository.ColorRepository;

@Service
public class ColorService {

	@Autowired
	ColorRepository repo;
	
	public List<Color> getAllColors() {
		return repo.findAll();
	}
	
	public Color getColorById(int id) throws ResourceNotFoundException {
		Optional<Color> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Color", id);
		}
		return found.get();
	}
	
	// TODO: Implement the commented out code. Right now server 500s if same color tries to get re-added
	public Color createColor(Color color) {
//		Optional<Color> exists = repo.findByCode(color.getCode());
//		if (!exists.isEmpty()) {
//			throw new ResourceAlreadyExistsException("Color", color.getId());
//		}
		Color created = repo.save(color);
		return created;
	}
	
	public Color updateColor(Color color) throws ResourceNotFoundException {
		if (!repo.existsById(color.getId())) {
			throw new ResourceNotFoundException("Color", color.getId());
		}
		return repo.save(color);
	}
	
	public Color deleteColorById(int id) throws ResourceNotFoundException {
		Optional<Color> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Color", id);
		}
		repo.deleteById(id);
		return found.get();
	}
}
