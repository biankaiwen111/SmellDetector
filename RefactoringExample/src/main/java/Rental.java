/**
 * The rental class represents a customer renting a movie.
 */
public class Rental {
    protected String _name = "no name";
    private Tape _tape;
    private int _daysRented;

    public Rental(Tape tape, int daysRented) {
        _tape = tape;
        _daysRented = daysRented;
    }

    public int daysRented() {
        return _daysRented;
    }

    public Tape getTape() {
        return _tape;
    }

}