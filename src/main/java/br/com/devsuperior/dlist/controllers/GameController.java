package br.com.devsuperior.dlist.controllers;

import br.com.devsuperior.dlist.dto.GameMinDTO;
import br.com.devsuperior.dlist.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

  @Autowired
  private GameService gameService;

  @GetMapping
  public ResponseEntity<List<GameMinDTO>> index(){
    var gamesList = gameService.getAll();
    return ResponseEntity.ok(gamesList);
  }
}
