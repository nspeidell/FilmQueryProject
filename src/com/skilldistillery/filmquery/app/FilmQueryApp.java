package com.skilldistillery.filmquery.app;
import java.util.List;
import java.util.Scanner;
import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	private DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {
		Scanner kb = new Scanner(System.in);
		menu();
		startUserInterface(kb);
	}

	private void startUserInterface(Scanner kb) {
		int selectionNumber = kb.nextInt();
		if (selectionNumber == 3) {
			System.out.println("You quit");
		} else if (selectionNumber == 1) {
			System.out.println("Enter film ID");
			int filmID = kb.nextInt();
			Film film = db.findFilmById(filmID);
			if (film != null) {
				System.out.println(db.findFilmById(filmID));
				System.out.println();
				launch();
			}
		else {
				System.out.println("No film found");
				launch();
			}
		} else if (selectionNumber == 2) {
			System.out.println("Enter keyword to search by");
			String filmKeyword = kb.next();
			List<Film> filmKeywordFilms = db.findFilmByKeyword(filmKeyword);	
			if (filmKeywordFilms != null) {
				launch();
			
			} else {
				System.out.println("No film found");
				System.out.println();
				launch();
			}

		} else {
			System.out.println("Invalid entry, please select one of the allowed options:");
			System.out.println();
			System.out.println();
			launch();
		}
	}

	private void menu() {
		System.out.println("Press 1 to Look up a film by its ID");
		System.out.println("Press 2 to Look up a film by a search keyword");
		System.out.println("Press 3 to exit the application");
	}
}
