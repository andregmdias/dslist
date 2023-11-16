package br.com.devsuperior.dlist.controllers;

import br.com.devsuperior.dlist.dto.GameListDTO;
import br.com.devsuperior.dlist.dto.GameMinDTO;
import br.com.devsuperior.dlist.services.GameListService;
import br.com.devsuperior.dlist.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game_lists")
public class GameListController {

  @Autowired
  private GameListService gameListService;

  @Autowired
  private GameService gameService;

  @GetMapping
  public ResponseEntity<List<GameListDTO>> index(){
    var gameList = this.gameListService.getAll();
    return ResponseEntity.ok(gameList);
  }

  @GetMapping("{id}/games")
  public ResponseEntity<List<GameMinDTO>> findByList(@PathVariable Long id){
    var gamesList = gameService.findByList(id);
    return ResponseEntity.ok(gamesList);
  }
}
