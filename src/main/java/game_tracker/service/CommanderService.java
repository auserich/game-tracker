package game_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game_tracker.exception.ResourceNotFoundException;
import game_tracker.exception.UsernameTakenException;
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
	
	// TODO: Figure out better solution that passing -1 as an id to exception
	public Commander getCommanderByName(String name) throws ResourceNotFoundException {
		Optional<Commander> found = repo.findByName(name);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Commander", -1);
		}
		return found.get();
	}
	
	// TODO: Probably make a CommanderNameTakenException
	public Commander createCommander(Commander commander) throws UsernameTakenException {
		Optional<Commander> exists = repo.findByName(commander.getName());
		if (!exists.isEmpty()) {
			throw new UsernameTakenException(commander.getName());
		}
		commander.setId(null);
		Commander created = repo.save(commander);
		return created;
	}
	
	public Commander updateCommander(Commander commander) throws ResourceNotFoundException {
		if (!repo.existsById(commander.getId())) {
			throw new ResourceNotFoundException("Commander", commander.getId());
		}
		return repo.save(commander);
	}
	
	public Commander deleteCommanderById(int id) throws ResourceNotFoundException {
		Optional<Commander> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Commander", id);
		}
		repo.deleteById(id);
		return found.get();
	}
}
