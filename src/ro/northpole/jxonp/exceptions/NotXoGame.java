package ro.northpole.jxonp.exceptions;

public class NotXoGame extends JXoNpException {

	private static final long serialVersionUID = 8111523729973422267L;

	public NotXoGame() {
		super("Not an X and O game.");
	}
}
