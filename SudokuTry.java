package soduko;

public class SudokuTry {

	public static void main(String[] args) {
		int size =9;
		ISudokuSolver solver= new SudokuSolver(size);
		new Grid(solver);

	}

}
