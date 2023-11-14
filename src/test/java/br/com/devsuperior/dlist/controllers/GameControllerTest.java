package br.com.devsuperior.dlist.controllers;

import br.com.devsuperior.dlist.dto.GameMinDTO;
import br.com.devsuperior.dlist.entities.Game;
import br.com.devsuperior.dlist.services.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
class GameControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  GameService gameService;

  @Test
  void whenTheGameListIsEmpty() throws Exception {
    var game1 =  new Game(1L, "Max Payne", 1999, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
        "Best FPS Game", "Best FPS Game");
    var game2 =  new Game(1L, "Max Payne 2", 2002, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
        "Best FPS Game", "Best FPS Game");
    var game3 =  new Game(1L, "Max Payne", 2010, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
        "Best FPS Game", "Best FPS Game");

    var gameList = List.of(game1, game2, game3);
    var gameMinDTOList = gameList.stream().map(GameMinDTO::new).toList();

    given(gameService.getAll()).willReturn(gameMinDTOList);

    mvc.perform(get("/games")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpectAll(
            status().isOk(),
            jsonPath("$", hasSize(3)),
            jsonPath("$[0].title", is(game1.getTitle())),
            jsonPath("$[1].title", is(game2.getTitle())),
            jsonPath("$[2].title", is(game3.getTitle()))
        );
  }
}