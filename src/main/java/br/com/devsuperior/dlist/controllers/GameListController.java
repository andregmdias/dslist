package br.com.devsuperior.dlist.controllers;

import br.com.devsuperior.dlist.dto.GameListDTO;
import br.com.devsuperior.dlist.services.GameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game_lists")
public class GameListController {

  @Autowired
  private GameListService gameListService;

  @GetMapping
  public ResponseEntity<List<GameListDTO>> index(){
    var gameList = this.gameListService.getAll();
    return ResponseEntity.ok(gameList);
  }
}
