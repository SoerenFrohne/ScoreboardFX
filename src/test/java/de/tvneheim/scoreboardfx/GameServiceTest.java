package de.tvneheim.scoreboardfx;

import de.tvneheim.scoreboardfx.viewmodel.GameService;

class GameServiceTest {

  private final GameService gameService = new GameService();

//  @Test
//  void canReproduceState() {
//    gameService.startGame();
//    gameService.changeName(TeamType.HOME, "TV Neheim");
//    gameService.stopTime();
//    gameService.startTime();
//    gameService.scoreHome();
//    gameService.scoreGuest();
//    gameService.stopPeriod();
//
//    var home = new Team(TeamType.HOME, "TV Neheim", 1, List.of(), List.of());
//    var guest = new Team(TeamType.GUEST, "GAST", 1, List.of(), List.of());
//    var time = new Time(true, 2, 30, 0, 0);
//    var settings = new Settings(30);
//    var expected = new Game(Status.PAUSED, home, guest, time, settings);
//    var result = gameService.getState();
//    assertThat(result).isEqualTo(expected);
//  }

}