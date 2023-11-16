package br.com.devsuperior.dlist.repositories;

import br.com.devsuperior.dlist.dto.GameMinDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class GameRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  GameRepository gameRepository;

  @Test
  void returnAnEmptyListWhenDoesntFetchesTheGameList(){
    var result = gameRepository.searchByList(20L);

    assertEquals(0, result.size());
  }

  @Test
  void shouldReturnTheGameListWithTheGivenIdWithSuccess(){
    var result = gameRepository.searchByList(1L);
    assertEquals(5, result.size());
  }
}