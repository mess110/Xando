package ro.northpole.jxonp.models;

public class Move {
	public int x, y, kind;
	public long time;

	public Move(int x, int y, int kind, long time) {
		this.x = x;
		this.y = y;
		this.kind = kind;
		this.time = time;
	}

	@Override
	public String toString() {
		return x + ":" + y + " - " + kind + " - " + time;
	}

	@Override
	public boolean equals(Object aThat) {
		if (this == aThat) {
			return true;
		}

		// use instanceof instead of getClass here for two reasons
		// 1. if need be, it can match any supertype, and not just one class;
		// 2. it renders an explict check for "that == null" redundant, since
		// it does the check for null already - "null instanceof [type]" always
		// returns false. (See Effective Java by Joshua Bloch.)
		if (!(aThat instanceof Move)) {
			return false;
		}
		// Alternative to the above line :
		// if ( aThat == null || aThat.getClass() != this.getClass() ) return
		// false;

		Move that = (Move) aThat;

		// now a proper field-by-field evaluation can be made
		return this.x == that.x && this.y == that.y && this.kind == that.kind;
	}
}
