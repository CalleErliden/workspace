package soduko;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SudokuSolverTest {
		
	private SudokuSolver sudoku;
	private final int[][] testBoard = { { 0, 0, 8, 0, 0, 9, 0, 6, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 5 },
			{ 1, 0, 2, 5, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 1, 0, 0, 9, 0 }, { 0, 5, 0, 0, 0, 0, 6, 0, 0 },
			{ 6, 0, 0, 0, 0, 0, 0, 2, 8 }, { 4, 1, 0, 6, 0, 8, 0, 0, 0 }, { 8, 6, 0, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 4, 0, 0 } };

	@BeforeEach
	void setUp() throws Exception {
		this.sudoku= new SudokuSolver();
	}

	@AfterEach
	void tearDown() throws Exception {
		this.sudoku= null;
	}
	@Test
	void testEmpty() {
		assertTrue(this.sudoku.solve());
	}
	@Test
	void testUnsolvableColumn() {
		this.sudoku.add(0, 0, 1);
		this.sudoku.add(1, 0, 1);

		assertFalse(this.sudoku.solve());

		this.sudoku.add(1, 0, 0);

		assertTrue(this.sudoku.solve());
	}
	@Test 
	void testUnsolveableRow() {
		this.sudoku.add(0, 0, 1);
		this.sudoku.add(0, 3, 1);

		assertFalse(this.sudoku.solve());

		this.sudoku.add(0, 3, 0);

		assertTrue(this.sudoku.solve());
	}

	@Test
	void testGivenBoard() {
		this.sudoku.setMatrix(this.testBoard);
		assertTrue(sudoku.solve());
		}
	
	@Test 
	void testUnsolveableBoard() {
		sudoku.add(0,0,1);
		sudoku.add(0,1,1);
		
		assertFalse(sudoku.solve());
		
		sudoku.add(0,1,0);
		
		assertTrue(sudoku.solve());
	}
	@Test
	void testGetSetNumber() {
		assertEquals(this.sudoku.get(0, 0), 0);
		assertThrows(IllegalArgumentException.class, () -> this.sudoku.get(10, 10), "Should throw error");

		this.sudoku.add(0, 0, 1);
		
		assertThrows(IllegalArgumentException.class, () -> this.sudoku.add(0, 0, 12), "Should throw error");
		assertEquals(this.sudoku.get(0, 0), 1);

	}
	@Test 
	void testisValid() {
		sudoku.add(0,0,1);
		sudoku.add(4,1,1);
		
		assertTrue(this.sudoku.checkValidPlacement(0, 0, 1), "IsValid is not correct");
		assertTrue(this.sudoku.solve());
		assertTrue(this.sudoku.isValid());
		assertThrows(IllegalArgumentException.class, () -> this.sudoku.checkValidPlacement(10, 10, 10), "Should throw error");
		}
	@Test 
	void testClear() {
		int dim =sudoku.getDimension();
		assertTrue(sudoku.solve());
		sudoku.clear();
		
		for(int row = 0; row < dim; row++) {
			for(int col = 0; col < dim; col++) {
				assertEquals(this.sudoku.get(row, col), 0);
			}
		}
	}
@Test
	void testsetMatrix () {
	sudoku.setMatrix(testBoard);
	assertEquals(sudoku.get(2,0),1);
	assertEquals(sudoku.get(0,2),8);
}
	
	@Test
	void testGetMatrix() {
		int [][] tom= sudoku.getMatrix();
		int dim = sudoku.getDimension();
		for(int row =0;row<dim;row++) {
			for(int col = 0; col < dim; col++ ) {
				assertEquals(tom[row][col],0);
			}
			
			sudoku.solve();
			int [] [] löst = sudoku.getMatrix();
			
			for(int i=0; i<dim;i++) {
				assertEquals(löst[0][i],i+1);
			}
		}
	}

}
