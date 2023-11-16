package br.com.devsuperior.dlist.services;

import br.com.devsuperior.dlist.dto.GameDTO;
import br.com.devsuperior.dlist.dto.GameMinDTO;
import br.com.devsuperior.dlist.entities.Game;
import br.com.devsuperior.dlist.projections.GameMinProjection;
import br.com.devsuperior.dlist.repositories.GameRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

  @Mock
  private GameRepository gameRepository;

  @InjectMocks
  private GameService gameService;

  @DisplayName("When exists, should return a GameMinDTO list")
  @Test
  public void givenGameList_whenGetAllGames_thenReturnGameMinDTOList(){
    var game1 =  new Game(1L, "Max Payne", 1999, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
        "Best FPS Game", "Best FPS Game");
    var game2 =  new Game(2L, "Max Payne 2", 2002, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
        "Best FPS Game", "Best FPS Game");
    var game3 =  new Game(3L, "Max Payne", 2010, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
        "Best FPS Game", "Best FPS Game");

    given(gameRepository.findAll()).willReturn(List.of(game1, game2, game3));

    var gameList = gameService.getAll();

    assertNotNull(gameList);
    assertEquals(3, gameList.size());
    gameList.forEach(gameMinDTO -> assertInstanceOf(GameMinDTO.class, gameMinDTO));
  }

  @DisplayName("When not exists, should return an empty GameMinDTO list")
  @Test
  public void givenEmptyGameList_whenGetAllGames_thenReturnEmptyGameMinDTOList(){
    given(gameRepository.findAll()).willReturn(Collections.emptyList());

    var gameList = gameService.getAll();

    assertNotNull(gameList);
    assertEquals(0, gameList.size());
  }

  @DisplayName("When exists, should return the fetched game")
  @Test
  public void givenAnExistentGameId_whenFindsByGivenId_thenReturnTheFetchedGame() throws Exception {
    var game1 =  new Game(1L, "Max Payne", 1999, "FPS", "PS1, PS2, PC",10.0, "urlMaxPayne",
        "Best FPS Game", "Best FPS Game");

    given(gameRepository.findById(1L)).willReturn(Optional.of(game1));

    var fetchedGame = gameService.getById(1L);

    assertNotNull(fetchedGame);
    assertInstanceOf(GameDTO.class, fetchedGame);
    assertDoesNotThrow(() -> "Game not found");
    assertAll(() -> {
      assertEquals(fetchedGame.getTitle(), game1.getTitle());
      assertEquals(fetchedGame.getGenre(), game1.getGenre());
    });
  }

  @DisplayName("When doesn't exists, should return the fetched game")
  @Test
  public void givenAnExistentGameId_whenDoesntFindsByGivenId_thenThrowsAnException() throws Exception {
    given(gameRepository.findById(1L)).willReturn(Optional.empty());

    Throwable exception = assertThrows(
        Exception.class,
        () -> gameService.getById(1L)
    );
    assertEquals("Game not found", exception.getMessage());
  }

  @Test
  public void shouldReturnTheGameListWithTheGivenListId(){
    ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    Map<String, Object> game1ProjectionMap = Map.of(
        "id", 1L,
        "title", "Max Payne",
        "year", 1999,
        "imgUrl", "urlMaxPayne",
        "shortDescription", "Best FPS Game",
        "position", 10
    );

    GameMinProjection game1Projection = factory.createProjection(GameMinProjection.class, game1ProjectionMap);

    Map<String, Object> game2ProjectionMap = Map.of(
        "id", 2L,
        "title", "Max Payne 2",
        "year", 2002,
        "imgUrl", "urlMaxPayne2",
        "shortDescription", "Best FPS Game",
        "position", 1L
    );

    GameMinProjection game2Projection = factory.createProjection(GameMinProjection.class, game1ProjectionMap);

    Map<String, Object> game3ProjectionMap = Map.of(
        "id", 3L,
        "title", "Max Payne 3",
        "year", 2010,
        "imgUrl", "urlMaxPayne3",
        "shortDescription", "Best FPS Game",
        "position", 2L
    );

    GameMinProjection game3Projection = factory.createProjection(GameMinProjection.class, game1ProjectionMap);

    given(gameRepository.searchByList(1L)).willReturn(List.of(game1Projection, game2Projection, game3Projection));

    var gameMinList = gameService.findByList(1L);

    assertEquals(3, gameMinList.size());
    gameMinList.forEach(gameMinDTO -> assertInstanceOf(GameMinDTO.class, gameMinDTO));
  }

  @Test
  public void shouldReturnAnEmptyGameListWithTheGivenListId(){
    given(gameRepository.searchByList(1L)).willReturn(Collections.emptyList());

    var gameMinList = gameService.findByList(1L);

    assertEquals(0, gameMinList.size());
  }
}