package fr.mds.travelagencyproject.model;

public interface TripDao {
	long createTrip(Trip t) ; 
	Trip findTripById(long id) ; 
	void removeTrip(long id) ; 
}
