package br.com.devsuperior.dlist.services;

import br.com.devsuperior.dlist.dto.GameDTO;
import br.com.devsuperior.dlist.dto.GameMinDTO;
import br.com.devsuperior.dlist.entities.Game;
import br.com.devsuperior.dlist.repositories.GameRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
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
        () -> {
          gameService.getById(1L);
        }
    );
    assertEquals("Game not found", exception.getMessage());
  }
}