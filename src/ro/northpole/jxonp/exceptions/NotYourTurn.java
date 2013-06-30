package ro.northpole.jxonp.exceptions;

public class NotYourTurn extends JXoNpException {

	private static final long serialVersionUID = 3459884159479566148L;

	public NotYourTurn() {
		super("Not your turn");
	}
}
