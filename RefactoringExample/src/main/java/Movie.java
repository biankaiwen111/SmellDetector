/**
 * Movie represents the notion of a film. A video store might have several tapes in stock of the same movie
 */
public class Movie {
    private String _name = "no name";
    public static final int  CHILDRENS = 2;
    public static final int  REGULAR = 0;
    public static final int  NEW_RELEASE = 1;

    private int _priceCode;

    public Movie(String name, int priceCode) {
        _name = name;
        _priceCode = priceCode;
    }

    public int priceCode() {
        return _priceCode;
    }

    public void persist() {
        Registrar.addMovie(_name, this);
    }

    public  Movie get() {
        return Registrar.getMovie(_name);
    }

    public String getName() {
        return _name;
    }
}

