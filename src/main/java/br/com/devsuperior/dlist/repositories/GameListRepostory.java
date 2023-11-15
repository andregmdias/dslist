package br.com.devsuperior.dlist.repositories;

import br.com.devsuperior.dlist.entities.GameList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameListRepostory extends JpaRepository<GameList, Long> {
}
