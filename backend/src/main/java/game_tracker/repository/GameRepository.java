package game_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import game_tracker.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

	/*
	 * @Query("SELECT g FROM Game g WHERE g.deck1Name = :deckName OR g.deck2Name = :deckName OR g.deck3Name = :deckName OR g.deck4Name = :deckName ORDER BY g.date ASC, g.gameNumber ASC"
	 * ) List<Game> findGamesByDeckName(@Param("deckName") String deckName);
	 */

}
