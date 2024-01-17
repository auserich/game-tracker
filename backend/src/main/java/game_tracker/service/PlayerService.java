package game_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game_tracker.exception.ResourceNotFoundException;
import game_tracker.exception.UsernameNotFoundException;
import game_tracker.exception.UsernameTakenException;
import game_tracker.model.Player;
import game_tracker.model.User;
import game_tracker.repository.PlayerRepository;

@Service
public class PlayerService {

	@Autowired
	PlayerRepository repo;
	
	public List<Player> getAllPlayers() {
		return repo.findAll();
	}
	
	public List<Player> getAllPlayersFromUser(int userId) {
		return repo.getAllPlayersByUser(userId);
	}
	
	public Player getPlayerById(int id) throws ResourceNotFoundException {
		Optional<Player> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Player", id);
		}
		return found.get();
	}
	
	public Player getPlayerByName(String name) throws UsernameNotFoundException {
		Optional<Player> found = repo.findByName(name);
		if (found.isEmpty()) {
			throw new UsernameNotFoundException(name);
		}
		return found.get();
	}
	
	//TODO: Determine if I want to enforce unique names
	public Player createPlayer(String playerName, User user) throws UsernameTakenException {
		Optional<Player> found = repo.findPlayerByUserAndName(user.getId(), playerName);
		if (found.isPresent()) {
			throw new UsernameTakenException(playerName);
		}
		Player player = new Player();
		player.setName(playerName);
		player.setUser(user);
		return repo.save(player);
	}
	
	public Player updatePlayer(Player player) throws ResourceNotFoundException {
		if (repo.existsById(player.getId())) {
			return repo.save(player);
		}
		throw new ResourceNotFoundException("Player", player.getId());
	}
	
	public Player deletePlayerById(int id) throws ResourceNotFoundException {
		Optional<Player> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Player", id);
		}
		repo.deleteById(found.get().getId());
		return found.get();
	}
	
	public Player deletePlayerByName(String name) throws ResourceNotFoundException {
		Optional<Player> found = repo.findByName(name);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Player", -1);
		}
		repo.deleteById(found.get().getId());
		return found.get();
	}
}
