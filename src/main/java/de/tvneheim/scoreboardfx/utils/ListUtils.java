package de.tvneheim.scoreboardfx.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ListUtils {

  private ListUtils() {

  }

  @SafeVarargs
  public static <T> List<T> appendElements(List<T> immutableList, T... elements) {
    List<T> tmpList = new ArrayList<>(immutableList);
    tmpList.addAll(Arrays.asList(elements));
    return Collections.unmodifiableList(tmpList);
  }

  @SafeVarargs
  public static <T> List<T> removeElements(List<T> immutableList, T... elements) {
    List<T> tmpList = new ArrayList<>(immutableList);
    tmpList.removeAll(Arrays.asList(elements));
    return Collections.unmodifiableList(tmpList);
  }
}
