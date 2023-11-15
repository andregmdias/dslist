package br.com.devsuperior.dlist.services;

import br.com.devsuperior.dlist.dto.GameDTO;
import br.com.devsuperior.dlist.dto.GameMinDTO;
import br.com.devsuperior.dlist.repositories.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

  @Autowired
  private GameRepository gameRepository;

  @Transactional(readOnly = true)
  public List<GameMinDTO> getAll() {
    var games = this.gameRepository.findAll();
    List<GameMinDTO> gamesMin = games.stream().map(GameMinDTO::new).toList();

    return gamesMin;
  }

  @Transactional(readOnly = true)
  public GameDTO getById(Long id) throws Exception {
    var game = this.gameRepository
        .findById(id)
        .orElseThrow(() -> new Exception("Game not found"));

    var gameDto = new GameDTO(game);
    return gameDto;
  }
}
