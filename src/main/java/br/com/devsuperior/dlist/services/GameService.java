package br.com.devsuperior.dlist.services;

import br.com.devsuperior.dlist.dto.GameMinDTO;
import br.com.devsuperior.dlist.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

  @Autowired
  private GameRepository gameRepository;

  public List<GameMinDTO> getAll() {
    var games = this.gameRepository.findAll();
    List<GameMinDTO> gamesMin = games.stream().map(GameMinDTO::new).toList();

    return gamesMin;
  }
}
