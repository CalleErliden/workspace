package soduko;

public class SudokuSolver implements ISudokuSolver {
	
	private final static int tom = 0;
	private final static int DefaultSize=9;
	

	private int[][] board;
	
	
	public SudokuSolver() {
		this(DefaultSize);
	}
	
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
	
	@Override
	public void add(int row, int col, int digit) {
		checkArg(row,col,digit);
		board[row][col]= digit;

	}
	

	@Override
	public void remove(int row, int col) {
		checkArg(row,col);
		board[row][col]=tom;

	}

	@Override
	public int get(int row, int col) {
		checkArg(row,col);
		return board[row][col];
	}

	@Override
	public boolean isValid() {
		
		for(int i = 0; i<getDimension();i++) {
			for(int j=0; j<getDimension();j++) {
				if(!checkValidPlacement(i,j,board[i][j])) {
					return false;
				}
				
			}
		}
		return true;
	}

	 boolean checkValidPlacement(int row, int col, int nbr) {
		checkArg(row, col,nbr);

		
		//int v = board[row][col];
	//	board[row][col] = tom; // tar bort värdet boxen som ska kollas så det inte kollar mot sig själv

		// kollar på alla sätt 
		boolean result = !checkRow(row, col,nbr) && !checkColumn(col, row,nbr) && !checkBox(row, col, nbr);

	
		//this.board[row][col] = v;

		return result;
	}

	private boolean checkBox(int row, int col, int nbr) {
		int localBoxRow= row -row % 3;
		int localBoxCol = col- col % 3; // hittar boxens vänstra övre hörn
		
		
		for(int i=localBoxRow;i<localBoxRow+3;i++) {
				for(int j= localBoxCol; j<localBoxCol+3;j++) { // saknade +3 så fungerade inte korreckt
					
					if(board[i][j] == nbr  && ((i != row ) && (j !=col)) && board[i][j] !=0) {
						return true; // returns true om det inte är är valid
				}
			
			}
		}
		return false;
	}

	private boolean checkColumn(int col,int row, int nbr) {
		for(int i=0;i<getDimension();i++) {
			if(board[i][col] == nbr && board[i][col]!=0 && row !=i) {
				return true; // returns true om det inte är är valid tal 
			}
		}
		return false;
	}

	private boolean checkRow(int row, int col ,int nbr) {
		for (int i =0; i<getDimension();i++) {
			if(board[row][i]== nbr && board[row][i] !=0 && col != i) {
				return true; // returns true om det inte är är vaild tal då det blir snabbare
			}
		}
		return false;
	}

	@Override
	public void clear() {
		for(int i=0; i<getDimension();i++) {
			for(int j=0; j<getDimension();j++) {
				board[i][j] = tom;
			}
		}

	}

	@Override
	public void setMatrix(int[][] m) {
		board = copyArray(m);
		

	}
	
	@Override
	public int[][] getMatrix() {
		// TODO Auto-generated method stub
		return copyArray(board);
	}
	//hjälpmetod för att kolla så att alla parametrar passa dimensionerna
	private void checkArg(int ... args) {
		int dim = getDimension();
		
		for(int a:args) {
			if(a>dim || a<0) {
				throw new IllegalArgumentException();
			}
		}
		
	}

	public int getDimension() {
			
			 return board.length;
		}
	private int[][]copyArray(int [][] array){
		int length= array.length;
		int [][] temp= new int[length][length];
		
		for (int i=0 ;i<length;i++) {
			for(int j =0; j<length;j++) {
				temp[i][j]=array[i][j];
			}
		}
		return temp;
	}


	
}
