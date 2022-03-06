package soduko;

public class SudokuSolver implements ISudokuSolver {

	private final static int tom = 0;
	private final static int DefaultSize=9;


	private int[][] board;


	public SudokuSolver() {
		this(DefaultSize);
	}
	/**
	 *Försöker att lösa brädet genom att backtracka igenom boardet för att lösa det
	 *@return true om det lyckades lösa brädet
	 */
	public SudokuSolver(int size) {
		this.board=new int[size][size];

		for(int i =0;i<size;i++) {
			for(int j=0; j>size;j++) {
				board[i][j]=tom;
			}
		}
	}
	@Override
	public boolean solve() {

		return solveBoard();

	}
	private boolean solveBoard() {
		if(!isValid()) {
			return false;
		} else {
			for (int row =0; row < getDimension();row++) {
				for(int col=0;col<getDimension();col++) {
					if(board[row][col] == 0) {
						for(int nbrToTry=1;nbrToTry<= 9;nbrToTry++) {
							if(checkValidPlacement(row,col,nbrToTry)) {
								board[row][col]=nbrToTry;

								if(solveBoard()) {
									return true;
								} else {
									board[row][col]= tom;
								}
							}
						}
						return false;
					}
				}
			}
			return true;
		}
	}
	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */
	@Override
	public void add(int row, int col, int digit) {
		checkArg(row,col,digit);
		board[row][col]= digit;

	}
	/**
	 *  tömmer värdet i den specificerade boxen
	 * @parameter row : raden
	 * @parameter col : columnen 
	 * @throw illegalArgumentExeption om row,col pekar på en box utanför dimensionen -1
	 */
	@Override
	public void remove(int row, int col) {
		checkArg(row,col);
		board[row][col]=tom;

	}
	/**
	 * returnerar värdet i bocen row, col
	 * @parameter row : raden
	 * @parameter col : columnen 
	 * @return värdet i row,col eller 0 om den är tom
	 * @throw illegalArgumentExeption om row,col pekar på en box utanför dimensionen -1
	 */
	@Override
	public int get(int row, int col) {
		checkArg(row,col);
		return board[row][col];
	}
	/**
	 * Kollar om alla värden i brädet är valid får vara på den platsen;
	 * @return true om det är lösbart;
	 */
	@Override
	public boolean isValid() {

		for(int i = 0; i<getDimension();i++) {
			for(int j=0; j<getDimension();j++) {
				if(board[i][j]!=0) {
					if(!checkValidPlacement(i,j,board[i][j])) {
						return false;
					}
				}

			}
		}
		return true;
	}
	// hjälpmetod för att kolla alla regler.
	boolean checkValidPlacement(int row, int col, int nbr) {
		checkArg(row, col,nbr);


		int v = board[row][col];
		board[row][col] = tom; // tar bort värdet boxen som ska kollas så det inte kollar mot sig själv

		// kollar på alla sätt 
		boolean result = !checkRow(row, col,nbr) && !checkColumn(col, row,nbr) && !checkBox(row, col, nbr);


		this.board[row][col] = v;

		return result;
	}
	//Kollar om det går att sätta in nbr i rutan.
	private boolean checkBox(int row, int col, int nbr) {
		int localBoxRow= row -row % 3;
		int localBoxCol = col- col % 3; // Hittar boxens vänstra övre hörn.


		for(int i=localBoxRow;i<localBoxRow+3;i++) {
			for(int j= localBoxCol; j<localBoxCol+3;j++) {

				if(board[i][j] == nbr  ) {
					return true; // Returns true om det inte är är valid.
				}

			}
		}
		return false;
	}
	//Kollar om nbr går att sätta in i columnen.
	private boolean checkColumn(int col,int row, int nbr) {
		for(int i=0;i<getDimension();i++) {
			if(board[i][col] == nbr /*&& board[i][col]!=0 && row !=i*/) {
				return true; // returns true om det inte är är valid tal 
			}
		}
		return false;
	}

	// Kollar om nbr går att sätta in på raden.
	private boolean checkRow(int row, int col ,int nbr) {
		for (int i =0; i<getDimension();i++) {
			if(board[row][i]== nbr /*&& board[row][i] !=0 && col != i*/) {
				return true; // returns true om det inte är är vaild tal då det blir snabbare
			}
		}
		return false;
	}
	/**
	 * tömmer alla platser i brädet och sätter dem till 0:or
	 */
	@Override
	public void clear() {
		for(int i=0; i<getDimension();i++) {
			for(int j=0; j<getDimension();j++) {
				board[i][j] = tom;
			}
		}

	}
	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	@Override
	public void setMatrix(int[][] m) {
		board = copyArray(m);


	}
	/**
	 * returns  värdena i brädet. 0 betyder att den bocen är tom
	 * @return värderna i bärdet
	 */
	@Override
	public int[][] getMatrix() {
		// TODO Auto-generated method stub
		return copyArray(board);
	}
	//Hjälpmetod för att kolla så att alla parametrar passa dimensionerna.
	private void checkArg(int ... args) {
		int dim = getDimension();

		for(int a:args) {
			if(a>dim || a<0) {
				throw new IllegalArgumentException();
			}
		}

	}


	//
	public int getDimension() {
		return board.length;
	}

	//Hjälpmetod för att kopiera en array.
	private int[][] copyArray(int [][] array){
		int length = array.length;
		int [][] temp = new int[length][length];

		for (int i=0 ;i<length;i++) {
			for(int j =0; j<length;j++) {
				temp[i][j] = array[i][j];
			}
		}
		return temp;
	}
}