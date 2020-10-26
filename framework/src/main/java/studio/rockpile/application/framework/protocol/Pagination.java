package studio.rockpile.application.framework.protocol;

public class Pagination {
	private int current;
	private int size;

	@Override
	public String toString() {
		return "Pagination [current=" + current + ", size=" + size + "]";
	}

	public int getCurrent() {
		return current;
	}

	public int getSize() {
		return size;
	}

	@SuppressWarnings("unused")
	private Pagination() {

	}

	public Pagination(int current, int size) {
		this.current = current;
		this.size = size;
	}
}
