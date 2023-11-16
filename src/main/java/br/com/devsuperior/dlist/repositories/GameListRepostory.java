package br.com.devsuperior.dlist.repositories;

import br.com.devsuperior.dlist.entities.GameList;
import br.com.devsuperior.dlist.projections.GameMinProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameListRepostory extends JpaRepository<GameList, Long> {

}
