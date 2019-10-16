package fr.mds.travelagencyproject.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import fr.mds.travelagencyproject.db.ConnectionManager;

/**
 * An implementnaiton of the PlaceDao interface using JDBC
 * 
 * @author cedri
 *
 */
public class JdbcPlaceDao implements PlaceDao {	
	Connection connection ; 

	public JdbcPlaceDao() {
		super();
		this.connection = ConnectionManager.getConnection() ; 

	}

	/**
	 * @version 1.0
	 */
	@Override
	public long createPlace(Place p) {
		try (PreparedStatement pStmt = this.connection.prepareStatement("INSERT INTO Place(name) VALUES (?)",
				Statement.RETURN_GENERATED_KEYS)) {
			pStmt.setString(1, p.getName());
			pStmt.executeUpdate();
			ResultSet resultSet = pStmt.getGeneratedKeys();
			resultSet.next();
			Long id = resultSet.getLong(1);
			this.connection.commit();
			System.out.println("Insertion de la place " + id + " effectuée");
			return id ;

		} catch (SQLException e) {
			throw new RuntimeException("Unable to insert this new place :" + p, e);
		}

	}
	//	public long createPlace(Place p) {
	//		try (PreparedStatement pStmt = getConnection().prepareStatement("INSERT INTO Place(name) VALUES (?)",
	//				Statement.RETURN_GENERATED_KEYS)) {
	//			pStmt.setString(1, p.getName());
	//			pStmt.executeUpdate();
	//			ResultSet resultSet = pStmt.getGeneratedKeys();
	//			resultSet.next();
	//			Long id = resultSet.getLong(1);
	//			getConnection().commit();
	//			System.out.println("Insertion de la place " + id + " effectuée");
	//			return id ;
	//			
	//		} catch (SQLException e) {
	//			throw new RuntimeException("Unable to insert this new place :" + p, e);
	//		}
	//		
	//	}

	/**
	 *@version 1.0
	 */
	@Override
	public Place findPlaceById(long id) {
		try {
			Statement st = this.connection.createStatement() ; 
			ResultSet rs = st.executeQuery("SELECT * FROM Place WHERE id="+id ) ; 

			rs.next() ; // place le curseur sur la première et normalement seule ligne
			Place placeRet = new Place(rs.getInt("id") , rs.getString("name")) ; 

			return placeRet ; 
		} catch (SQLException e) {
			throw new RuntimeException("Error, with the return of the Place : "+e);
		}
	}

	/**
	 * Should display something like : 
	 * 
	 * Listing all the places : 
	 * ____________________
	 * | 1 | nomTestdePlace
	 * | 2 | nomTestdePlace
	 * | 3 | nomTestdePlace2
	 * | 4 | testagain
	 * | 6 | nomTestdePlace3
	 * | 7 | nomTestdePlace3
	 * | 8 | nomTestdePlace3
	 * | 9 | nomTestdePlace3
	 * | 10| heymodify
	 * | 11| encore
	 * | 12| encore un test
	 * ____________________
	 * 
	 * @version 1.0
	 */
	public void listAllPlaces() {
		System.out.println("Listing all the places : ");
		try {
			Statement st = this.connection.createStatement() ; 
			ResultSet rs = st.executeQuery("SELECT * FROM Place") ; 
			System.out.println("____________________");
			while(rs.next()) {
				System.out.print("| ");
				System.out.print(rs.getInt("id")) ;
				if(rs.getInt("id") < 10)
					System.out.print(" | ") ; 
				else
					System.out.print("| ");
				System.out.println( rs.getString("name") ) ;

			} 
			//			while(rs.next()) {
			//				System.out.println("| "+ rs.getInt("id") +" | "+ rs.getString("name") ) ;
			//				
			//			} 
			System.out.println("____________________");
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 

	}


	/**
	 *@version 1.0
	 */
	@Override
	public void updatePlace(Place p) {
		int idToChange = (int) p.getId(); 
		System.out.println("Updating the Place : "+idToChange);

		Scanner ss = new Scanner(System.in);
		System.out.print("Enter the new name of the Place : ");
		String newName = ss.nextLine();

		try {
			PreparedStatement pStmt = this.connection.prepareStatement(
					"UPDATE Place\r\n" + 
							"SET name = ? \r\n" + 
							"WHERE id=?",
							Statement.RETURN_GENERATED_KEYS)  ;
			pStmt.setString(1 , newName) ; 
			pStmt.setInt(2, idToChange);
			pStmt.executeUpdate();
			this.connection.commit();
			System.out.println("Updating over.");			
		} catch (SQLException e) {
			e.printStackTrace();
		} 


	}

	/**
	 * Deletes a row in the table Place 
	 * @param id the id of the row 
	 * @version 1.0
	 */
	@Override
	public void removePlace(long id) {
		try {
			PreparedStatement pStmt = this.connection.prepareStatement(
					"DELETE FROM Place \r\n" + 
							"WHERE id ="+id
					)  ; 
			pStmt.executeUpdate() ; 
			this.connection.commit();
			System.out.println("Removing over."); 
		} catch (SQLException e) {
			throw new RuntimeException("Error, with the removing : "+e);
		}
	}

}
