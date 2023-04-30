package com.skilldistillery.filmquery.database;

import java.util.List;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public interface DatabaseAccessor {
	Film findFilmById(int filmId);

	List<Actor> findActorsByFilmId(int filmId);

	Actor findActorById(int actorId);

	List<Film> findFilmsByActorId(int ActorID);

	List<Film> findFilmByKeyword(String filmKeyword);
}
