package game_tracker.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import game_tracker.model.Deck;
import game_tracker.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

	@Query(value = "SELECT * FROM game WHERE game.deck1_name = ?1 OR game.deck2_name = ?1 OR game.deck3_name = ?1 OR game.deck4_name = ?1 ORDER BY game.date ASC, game.game_number ASC", nativeQuery = true)
	public List<Game> getOrderedGamesByDeckName(String name);
	
	boolean existsByDateAndGameNumber(Date date, Integer gameNumber);
}
