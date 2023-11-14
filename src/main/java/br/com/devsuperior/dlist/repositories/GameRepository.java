package br.com.devsuperior.dlist.repositories;

import br.com.devsuperior.dlist.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
