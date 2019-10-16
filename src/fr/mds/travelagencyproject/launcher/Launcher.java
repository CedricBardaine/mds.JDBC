package fr.mds.travelagencyproject.launcher;

import java.util.Scanner;

import fr.mds.travelagencyproject.model.JdbcPlaceDao;
import fr.mds.travelagencyproject.model.JdbcTripDao;
import fr.mds.travelagencyproject.model.Place;
import fr.mds.travelagencyproject.model.Trip;;

public class Launcher {

	public static void main(String[] args) {
		System.out.println("Welcome Aboard ! ");
		System.out.println();

		JdbcPlaceDao leDaoPlace = new JdbcPlaceDao() ; 
		JdbcTripDao leDaoTrip = new JdbcTripDao() ; 

		boolean again = false ; 
		boolean terminated = false ; 
		while (!terminated) {
			System.out.println(); System.out.println(); 
			System.out.print("What do you want to do ??!");
			if (again) System.out.print(" (again)");
			System.out.println();
			System.out.println(
					"1 - Add a place \n"
							+ "2 - Find a place \n"
							+ "3 - Edit a place \n"
							+ "4 - Remove a place \n"
							+ "5 - Add a trip \n"
							+ "6 - Find a trip \n"
							+ "7 - Remove a trip \n"
							+ "12 - List all the places \n"
							+ "42 - Run some examples that will certainly not work with a different database \n"
							+ "8 - Quit");
			Scanner s = new Scanner(System.in); // ce scanner déclaré sera réinitialisé plusieurs fois !!!!: ne pas utiliser le Scanner.close() , meme si Eclipse le demande gentillement. 
			int repUser = s.nextInt() ; 

			String repS ; 
			int repI ; 
			switch (repUser) {
			case 1:
				System.out.println("Give a name to the new Place : ");
				s = new Scanner(System.in); 
				repS = s.nextLine() ; 
				Place laPlace = new Place(repS) ;
				leDaoPlace.createPlace(laPlace) ;
				break;

			case 2 : 
				System.out.println("Enter the id of the place to find :");
				Scanner s2 = new Scanner(System.in);  
				repI = s2.nextInt() ; 
				Place placeRet = leDaoPlace.findPlaceById(repI) ; 
				System.out.println( placeRet.toString() ) ;
				break ; 

			case 3 : 
				System.out.println("Enter the id of the place to modify :");
				s = new Scanner(System.in);  
				repI = s.nextInt() ; 
				leDaoPlace.updatePlace(leDaoPlace.findPlaceById(repI) );
				break;

			case 4 : 
				System.out.println("Enter the id of the place to remove :");
				s = new Scanner(System.in);  
				repI = s.nextInt() ; 
				leDaoPlace.removePlace(repI);

			case 5 : 
				System.out.println("Give a departure to the new Trip : ");
				s = new Scanner(System.in); 
				int repIDeparture = s.nextInt() ; 
				System.out.println("Give a destination to the new Trip : ");
				s = new Scanner(System.in); 
				int repIDestination = s.nextInt() ; 
				System.out.println("Give a Price to the new Trip : ");
				s = new Scanner(System.in); 
				int repIPrice = s.nextInt() ; 
				Trip leTrip = new Trip(leDaoPlace.findPlaceById(repIDeparture) , leDaoPlace.findPlaceById(repIDestination) , repIPrice) ;
				leDaoTrip.createTrip(leTrip) ;
				break;

			case 6 : 
				System.out.println("Enter the id of the trip to find :");
				s = new Scanner(System.in);  
				repI = s.nextInt() ; 
				Trip tripRet = leDaoTrip.findTripById(repI) ; 
				System.out.println( tripRet.toString() ) ;
				break ; 

			case 7 : 
				System.out.println("Enter the id of the trip to remove : ");
				s = new Scanner(System.in); 
				repI = s.nextInt() ; 
				leDaoTrip.removeTrip(repI);
				
			case 12 : 
				leDaoPlace.listAllPlaces(); 
				break;

			case 42 : 
				runManyTests() ; 
				break ;

			case 8 : 
				terminated = true ; 
				System.err.println("So you're leaving ? Ok, bye.");

			default:
				break;
			}
			again = true ;
		}
		
	}


	/**
	 * This code executes some tests.
	 * It has been put in a fonction, because it takes a big place in the file. 
	 * @version 1.0
	 */
	public static void runManyTests() {
		JdbcPlaceDao leDaoPlace = new JdbcPlaceDao() ; 
		JdbcTripDao leDaoTrip = new JdbcTripDao() ; 

		// tests : 

		System.out.println("Creation a Place... ");
		Place test = new Place("nomTestdePlace3") ; 
		System.out.println(" - - - ");
		System.out.println();

		System.out.println("Displaying the Place : ");
		System.out.println(test.toString());
		System.out.println(" - - - ");
		System.out.println();

		//		System.out.println("Insertion des places : ");
		//		leDAO.createPlace(test) ;
		//		System.out.println(" - - - ");
		//		System.out.println();

		System.out.println("Getting the Place with the id:4");
		Place laplacerecup = leDaoPlace.findPlaceById(4) ; 
		System.out.println("Displaying the Place object : ");
		System.out.println( laplacerecup.toString() );
		System.out.println(" - - - ");
		System.out.println();

		System.out.println("Updating of the Place 4 : ");
		leDaoPlace.updatePlace(laplacerecup);
		System.out.println(" - - - ");
		System.out.println();

		//		int idtoremove = 1 ; 
		//		System.out.println("Removing the Place "+idtoremove);
		//		leDaoPlace.removePlace(idtoremove);
		//		System.out.println(" - - - ");
		//		System.out.println();

		System.out.println();
		System.out.println("___________________________");
		System.out.println();


		System.out.println("Getting the Trip with the id : 1");
		Trip leTripRecup = leDaoTrip.findTripById(1) ; 
		System.out.println("Displaying the Trip : ");
		System.out.println(leTripRecup.toString());
		System.out.println(" - - - ");
		System.out.println();

		System.out.println("Creating a new Trip : ");
		Trip triptest = new Trip(new Place(1 , "oneRandomDeparture") , new Place(1 , "oneRandomDestination") , 0) ; 
		leDaoTrip.createTrip(triptest) ; 
		System.out.println(" - - - ");
		System.out.println();

		//		int idtriptoremove = 6 ; 
		//		System.out.println("Removing the Trip : "+idtriptoremove) ;
		//		leDaoTrip.removeTrip(idtriptoremove);
		//		System.out.println(" - - - ");
		//		System.out.println();


	}

}