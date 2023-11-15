package br.com.devsuperior.dlist.controllers;

import br.com.devsuperior.dlist.dto.GameMinDTO;
import br.com.devsuperior.dlist.services.GameService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

  private final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping
  public ResponseEntity<List<GameMinDTO>> index(){
    var gamesList = gameService.getAll();
    return ResponseEntity.ok(gamesList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> show(@PathVariable Long id) {
    try{
      var game = this.gameService.getById(id);
      return ResponseEntity.ok(game);
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
