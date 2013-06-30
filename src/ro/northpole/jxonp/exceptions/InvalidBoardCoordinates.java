package ro.northpole.jxonp.exceptions;

public class InvalidBoardCoordinates extends JXoNpException {

	private static final long serialVersionUID = 4557550615120836791L;

	public InvalidBoardCoordinates() {
		super("Invalid board coordinates");
	}
}
