package ro.northpole.jxonp.exceptions;

public class GameFinished extends JXoNpException {

	private static final long serialVersionUID = 8111523729973422267L;

	public GameFinished() {
		super("Game over");
	}
}
