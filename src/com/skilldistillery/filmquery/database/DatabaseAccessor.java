package com.skilldistillery.filmquery.database;

import java.util.List;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public interface DatabaseAccessor {
  Film findFilmById(int filmId);
 public Actor findActorById(int actorId);
 //public List<Actor> findActorsByFilmId(int filmId);
  
List<Film> findFilmsByActorId(int ActorID);
}