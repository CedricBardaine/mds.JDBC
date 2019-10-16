package fr.mds.travelagencyproject.model;

public interface PlaceDao {
	long createPlace(Place p) ; 
	Place findPlaceById(long id) ; 
	void updatePlace(Place p ) ; 
	void removePlace(long id) ; 
}
