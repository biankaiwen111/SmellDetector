/**
 * The tape class represents a physical tape.
 */
public class Tape {
    private String _name = "no name";
    private String _serialNumber;
    private Movie _movie;

    public Tape(String serialNumber, Movie movie) {
        _serialNumber = serialNumber;
        _movie = movie;
    }

    public String getName() {
        return _name;
    }

    public Movie getMovie() {
        return _movie;
    }
}
