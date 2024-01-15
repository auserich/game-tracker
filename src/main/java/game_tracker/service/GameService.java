package game_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game_tracker.exception.ResourceNotFoundException;
import game_tracker.model.Game;
import game_tracker.repository.GameRepository;

@Service
public class GameService {

	@Autowired
	GameRepository repo;
	
	public List<Game> getAllGames() {
		return repo.findAll();
	}
	
	public Game getGameById(int id) throws ResourceNotFoundException {
		Optional<Game> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Game", id);
		}
		return found.get();
	}
	
	public Game createGame(Game game) {
		Game created = repo.save(game);
		return created;
	}
}
