package br.com.devsuperior.dlist.controllers;

import br.com.devsuperior.dlist.dto.GameDTO;
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

import java.util.Collections;
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
  void whenTheGameListIsNotEmpty() throws Exception {
    var game1 =  new Game(1L, "Max Payne", 1999, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
        "Best FPS Game", "Best FPS Game");
    var game2 =  new Game(2L, "Max Payne 2", 2002, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
        "Best FPS Game", "Best FPS Game");
    var game3 =  new Game(3L, "Max Payne", 2010, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
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

  @Test
  void whenTheGameListIsEmpty() throws Exception {
    given(gameService.getAll()).willReturn(Collections.EMPTY_LIST);

    mvc.perform(get("/games")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpectAll(
            status().isOk(),
            jsonPath("$", hasSize(0))
        );
  }

  @Test
  void shouldReturnTheGameWithTheGivenId() throws Exception {
    var game1 =  new Game(1L, "Max Payne", 1999, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
        "Best FPS Game", "Best FPS Game");

    given(gameService.getById(1L)).willReturn(new GameDTO(game1));

    mvc.perform(get("/games/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpectAll(
            status().isOk(),
            jsonPath("$.title", is(game1.getTitle())),
            jsonPath("$.year", is(game1.getYear())),
            jsonPath("$.genre", is(game1.getGenre()))
        );
  }

  @Test
  void shouldThrowAnExceptionWhenGameDoesntFetchesTheGameWithGivenId() throws Exception {
    var exception = new Exception("Game not found");
    given(gameService.getById(20L)).willThrow(exception);

    mvc.perform(get("/games/20")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$", is("Game not found")));
  }
}
