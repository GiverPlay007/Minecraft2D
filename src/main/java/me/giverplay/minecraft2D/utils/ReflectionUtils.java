package me.giverplay.minecraft2D.utils;

import me.giverplay.minecraft2D.entity.Entity;
import me.giverplay.minecraft2D.game.Game;

import java.lang.reflect.Constructor;

public class ReflectionUtils
{
  public static Entity newEntityInstance(String clazzName, Game game, int x, int y) throws Exception
  {
    String typeName = "me.giverplay.minecraft2D.entity.entities." + clazzName;

    Class<?> clazz = Class.forName(typeName);
    Constructor<?> constructor = clazz.getDeclaredConstructor(Game.class, int.class, int.class);

    return (Entity) constructor.newInstance(game, x, y);
  }
}