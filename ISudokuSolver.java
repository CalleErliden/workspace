package soduko;

public interface ISudokuSolver {
	/**
	 *Försöker att lösa brädet genom att backtracka igenom boardet för att lösa det
	 *@return true om det lyckades lösa brädet
	 */
	boolean solve();

	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */
	void add(int row, int col, int digit);

	/**
	 *  tömmer värdet i den specificerade boxen
	 * @parameter row : raden
	 * @parameter col : columnen 
	 * @throw illegalArgumentExeption om row,col pekar på en box utanför dimensionen -1
	 */
	void remove(int row, int col);

	/**
	 * returnerar värdet i bocen row, col
	 * @parameter row : raden
	 * @parameter col : columnen 
	 * @return värdet i row,col eller 0 om den är tom
	 * @throw illegalArgumentExeption om row,col pekar på en box utanför dimensionen -1
	 */
	int get(int row, int col);

	/**
	 * Kollar om alla värden i brädet är valid får vara på den platsen;
	 * @return true om det är lösbart;
	 */
	boolean isValid();

	/**
	 * tömmer alla platser i brädet och sätter dem till 0:or
	 */
	void clear();

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	void setMatrix(int[][] m);

	/**
	 * returns  värdena i brädet. 0 betyder att den bocen är tom
	 * @return värderna i bärdet
	 */
	int[][] getMatrix();

	int getDimension();

	
}
