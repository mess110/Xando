package ro.northpole.jxonp.exceptions;

public class GameNotStarted extends JXoNpException {

	private static final long serialVersionUID = 8111523729973422267L;

	public GameNotStarted() {
		super("Waiting for players to join");
	}
}
