package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String user = "student";
	private static final String pw = "student";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pw);

			String sql = "SELECT * FROM film JOIN film_actor ON film.id = film_actor.film_id JOIN language ON language.id = film.language_id WHERE film_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);

			ResultSet filmResult = stmt.executeQuery();

			while (filmResult.next()) {
				int filmId1 = filmResult.getInt("id");
				String title = filmResult.getString("title");
				String desc = filmResult.getString("description");
				short releaseYear = filmResult.getShort("release_Year");
				int langId = filmResult.getInt("language_Id");
				int rentDur = filmResult.getInt("rental_duration");
				double rate = filmResult.getDouble("rental_rate");
				int length = filmResult.getInt("length");
				double repCost = filmResult.getDouble("replacement_cost");
				String rating = filmResult.getString("rating");
				String features = filmResult.getString("special_features");
				String language = filmResult.getString("name");

				film = new Film(filmId1, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features, language);
				film.setActors(findActorsByFilmId(filmId));
				film.setFilmId(filmId1);
				film.setTitle(title);
				film.setDesc(desc);
				film.setReleaseYear(releaseYear);
				film.setLangId(langId);
				film.setRentDur(rentDur);
				film.setRate(rate);
				film.setLength(length);
				film.setRepCost(repCost);
				film.setRating(rating);
				film.setFeatures(features);
				film.setLanguage(language);
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pw);
			String sql = "SELECT * FROM actor WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {

				int id = actorResult.getInt("id");
				String fn = actorResult.getString("first_name");
				String ln = actorResult.getString("last_name");
				actor = new Actor(id, fn, ln); // Create the object
				actor.setFilms(null);
			}
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		// TODO Auto-generated method stub
		try {
			Connection conn = DriverManager.getConnection(URL, user, pw);

			String sql = "SELECT * FROM film JOIN film_actor ON film.id = film_actor.film_id "
					+ "JOIN actor_info ON actor_info.actor_id = film_actor.actor_id WHERE film_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);

			ResultSet filmResult = stmt.executeQuery();

			while (filmResult.next()) {
			int id = filmResult.getInt("id");
			String fn = filmResult.getString("first_name");
			String ln = filmResult.getString("last_name");
			Actor actor = new Actor(id, fn, ln);
			actors.add(actor);
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actors;
	}

	@Override
	public List<Film> findFilmByKeyword(String filmKeyword) {
		List<Film> films = null;;
		try {
			
			Connection conn = DriverManager.getConnection(URL, user, pw);

			String sql = "SELECT * FROM film JOIN language ON language.id = film.language_id WHERE film.title LIKE ? "
					+ "OR film.description LIKE ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+filmKeyword+ "%");
			stmt.setString(2, "%"+filmKeyword+ "%");

			ResultSet filmResult = stmt.executeQuery();
			while (filmResult.next()) {
				films = new ArrayList<>();
				int filmId = filmResult.getInt("id");
				String title = filmResult.getString("title");
				String desc = filmResult.getString("description");
				short releaseYear = filmResult.getShort("release_Year");
				int langId = filmResult.getInt("language_Id");
				int rentDur = filmResult.getInt("rental_duration");
				double rate = filmResult.getDouble("rental_rate");
				int length = filmResult.getInt("length");
				double repCost = filmResult.getDouble("replacement_cost");
				String rating = filmResult.getString("rating");
				String features = filmResult.getString("special_features");
				String language = filmResult.getString("name");
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features, language);
				film.setActors(findActorsByFilmId(filmId));
				films.add(film);
				System.out.println(films);
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

}


