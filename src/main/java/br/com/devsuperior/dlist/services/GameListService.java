package br.com.devsuperior.dlist.services;

import br.com.devsuperior.dlist.dto.GameListDTO;
import br.com.devsuperior.dlist.repositories.GameListRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameListService {

  @Autowired
  GameListRepostory gameListRepostory;

  @Transactional(readOnly = true)
  public List<GameListDTO> getAll(){
    var gameList = gameListRepostory.findAll();
    return gameList.stream().map(GameListDTO::new).toList();
  }
}
