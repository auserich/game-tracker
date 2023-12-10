package game_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game_tracker.exception.ResourceNotFoundException;
import game_tracker.model.Commander;
import game_tracker.repository.CommanderRepository;

@Service
public class CommanderService {
	
	@Autowired
	CommanderRepository repo;
	
	public List<Commander> getAllCommanders() {
		return repo.findAll();
	}
	
	public Commander getCommanderById(int id) throws ResourceNotFoundException {
		Optional<Commander> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Commander", id);
		}
		return found.get();
	}
}
