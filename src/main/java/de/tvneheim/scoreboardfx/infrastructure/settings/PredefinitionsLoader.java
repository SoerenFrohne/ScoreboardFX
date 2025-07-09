package de.tvneheim.scoreboardfx.infrastructure.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Log
public final class PredefinitionsLoader {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @SneakyThrows
  public static List<PredefinedTeam> loadTeams() {
    var workingDir = System.getProperty("user.dir");
    var path = Path.of(workingDir,"predefinitions\\teams.json");
    var file = Files.readString(path, StandardCharsets.UTF_8);
    log.info("File exists: " + file);
    var teams = OBJECT_MAPPER.readValue(file, PredefinedTeam[].class);
    return Arrays.asList(teams);
  }

}
