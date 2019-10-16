package fr.mds.travelagencyproject.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.mds.travelagencyproject.db.ConnectionManager;

/**
 * an implementation of the TripDao interface using JDBC.
 * @author cedri
 *
 */
public class JdbcTripDao implements TripDao {
	Connection connection ; 

	public JdbcTripDao() {
		super();
		this.connection = ConnectionManager.getConnection() ; 

	}

	/**
	 * @version 1.0 
	 */
	@Override
	public long createTrip(Trip t) {
		try (PreparedStatement pStmt = this.connection.prepareStatement(
				"INSERT INTO Trip(departure, destination, price) VALUES (? , ? , ?)",
				Statement.RETURN_GENERATED_KEYS)) {
			pStmt.setInt(1, (int) t.getDeparture().getId() );
			pStmt.setInt(2, (int) t.getDestination().getId() );
			pStmt.setInt(3, (int) t.getPrice() );
			pStmt.executeUpdate();
			ResultSet resultSet = pStmt.getGeneratedKeys();
			resultSet.next();
			Long id = resultSet.getLong(1);
			this.connection.commit();
			System.out.println("Insertion of the Trip " + id + " done");
			return id ;

		} catch (SQLException e) {
			throw new RuntimeException("Unable to insert this new Trip :" + t, e);
		}
	}

	/**
	 * @version 1.0
	 */
	@Override
	public Trip findTripById(long id) {
		try {
			Statement st = this.connection.createStatement() ; 
			ResultSet rs = st.executeQuery("SELECT * FROM Trip WHERE id="+id ) ; 

			rs.next() ; // place le curseur sur la première et normalement seule ligne
			int idTrip = rs.getInt("id") ; 
			int intPlaceDeparture = rs.getInt("departure") ; 
			int intPlaceDestination = rs.getInt("destination") ; 
			int priceTrip = rs.getInt("price") ; 
			
			JdbcPlaceDao leDAO = new JdbcPlaceDao() ; 
			Place departurePlace = leDAO.findPlaceById(intPlaceDeparture) ; 
			Place destinationPlace = leDAO.findPlaceById(intPlaceDestination) ; 
			
			
			return new Trip(idTrip , departurePlace , destinationPlace , priceTrip) ; 
		} catch (SQLException e) {
			throw new RuntimeException("Error, with the return of the Trip : "+e);
		}
	}

	/**
	 * @version 1.0
	 */
	@Override
	public void removeTrip(long id) {
		try {
			PreparedStatement pStmt = this.connection.prepareStatement(
					"DELETE FROM Trip \r\n" + 
							"WHERE id ="+id
					)  ; 
			pStmt.executeUpdate() ; 
			this.connection.commit();
			System.out.println("Removing is over."); 
		} catch (SQLException e) {
			throw new RuntimeException("Error, with the removing : "+e);
		}
		
	}
	
}
