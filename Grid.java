package soduko;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Grid {
	
	private ISudokuSolver solver;
	private int counter;
	private JTextField[][] sMatrix;
	
	public Grid(ISudokuSolver s) {
		this.solver = s;
		SwingUtilities.invokeLater(() -> createWindow("Sudoku", 400, 400));
		int counter =0;
		sMatrix= new JTextField[9][9];
	}
	
	private void createWindow(String Title, int height, int width) {
		
		//Gör ett fönster.
		JFrame sFrame = new JFrame("Sudoku");
		sFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sFrame.setSize(width, height);
		
		
		//Gör en panel för sudokut.
		JPanel sPanel = new JPanel();
		sPanel.setLayout(new GridLayout(9,9));
		sPanel.setBackground(Color.BLACK);
		sFrame.add(sPanel, BorderLayout.CENTER);
		
		
		//Gör en panel för knapparna.
		JPanel bPanel = new JPanel();
		sFrame.add(bPanel, BorderLayout.SOUTH);
		
		
		//Gör en sudoku-matris som sedan fylls med JTextFields.
		
		
		for(int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				sMatrix[r][c] = new JTextField(1);
				sMatrix[r][c].setHorizontalAlignment(JTextField.CENTER);
				
				sPanel.add(sMatrix[r][c]);
				
				
			}
		}
		
		
		//Ändrar färger på rutnätet. Kolla setBoxColor-metoden.
		setBoxColor(sMatrix);
		
		
		//Lägger till en solve-knapp. Kolla solveButton-metoden.
		JButton solveButton = new JButton("Solve");
		JButton clearButton = new JButton("Clear");
		JButton  nyBtn = new JButton ("New Sudoku");
		
		Container pane = sFrame.getContentPane();
		solveButton.addActionListener(event -> {
			if (solver.solve()) {
				this.rebuildBoard(9);
				JOptionPane.showMessageDialog( pane,"The sudoku has been solved");
			} else {
				JOptionPane.showMessageDialog(pane, "The sudoku could not be solved");
			}
		
        });
		clearButton.addActionListener(e -> {
			this.clearBoard(9);
		});
		nyBtn.addActionListener(e->{
					
					newBoard(9,counter);
					counter++;
					if(counter>3) {
						counter =0;
					}
		});
		bPanel.add(solveButton, BorderLayout.EAST);
		bPanel.add(clearButton, BorderLayout.EAST);
		bPanel.add(nyBtn,BorderLayout.EAST);
		
		//Gör fönstret synbart och packar ihop det tätt.
		sFrame.pack();
	    sFrame.setVisible(true);
	}
	
	
	
	
	private void newBoard(int dim, int counter) {
		
		Scanner scanner = null;
		try {
		scanner = new Scanner(new File("SudokuFile"));
		} catch(FileNotFoundException e ) {
		System.out.println("Couldn’t open file: sudokufile");
		System.exit(1);
		}
		for (int i =0; i<counter*9;i++) {
			String line = scanner.nextLine();
		}
		int [][] sMatrix = new int [dim][dim];
		for (int row=0; row<dim;row++) {
			String line = scanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			
			for (int i = 0; i < 9 ; i++) {
				sMatrix[row][i]= lineScanner.nextInt();
				}
			
		}
		solver.setMatrix(sMatrix);
		buildBoard( 9,false,false);
		
	}

	private void clearBoard(int i) {
		this.buildBoard(9, false, true);
		
	}

	private void buildBoard(int dim, boolean initialBuild, boolean clear) {
		if (clear) {
			this.solver.clear();
		}

		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				int nbr = this.solver.get(r, c);
				if(nbr!=0) {
					sMatrix[r][c].setEditable(false);
				}else {
					sMatrix[r][c].setEditable(true);
				}
				String val = nbr > 0 ? String.valueOf(nbr) : "";
				JTextField field = new JTextField();

				// If first build, set attributes and add them to the panel
			/*	if (initialBuild) {
					this.setFieldAttributes(field, r, c, dim);
					this.sPanel.add(field);
					fields[r][c] = field;
				}*/

				sMatrix[r][c].setText(val);
			}
		}

		
	}

	private void setFieldAttributes(JTextField field, int r, int c, int dim) {
		Color bgColor = this.squareBackground((int) Math.sqrt(dim), r, c);

		field.setBackground(bgColor);

		field.setPreferredSize(new Dimension(400, 400));
		field.setHorizontalAlignment(JTextField.CENTER);

		field.addFocusListener(new FocusListener() {

			// Set background on hover
			@Override
			public void focusGained(FocusEvent e) {
				field.setBackground(SudokuColors.HOVER);

			}
			
			// Set value and revert background on blur 
			@Override
			public void focusLost(FocusEvent e) {
				field.setBackground(bgColor);

				String t = field.getText();

				// Catch-block catches both IllegalArgumentException from solver.setNumber
				// as well as NumberFormatException from Integer.parseInt.
				// If invalid, set the number back to 0.
				try {
					int nbr = Integer.parseInt(t);

					// Simple way to set the number back to 0 and hide it from the view
					if (nbr <= 0) {
						throw new IllegalArgumentException();
					}

					solver.add(r, c, nbr);

				} catch (Exception err) {
					solver.add(r, c, 0);
					field.setText("");
				}

			}

		});
		
	}

	private Color squareBackground(int size, int row, int col) {
		int gridRow = (row / size) * size;
		int gridCol = (col / size) * size;

		if (gridRow % 2 == 0 && gridCol % 2 == 0 || (gridRow == size && gridCol == size)) {
			return SudokuColors.ACCENT;
		}

		return Color.white;
	}
	

	private void rebuildBoard(int dim) {
		this.buildBoard(dim, false, false);
		
	}

	
	
	
	
	
	//Ändrar färgen på rutnätet.
	private void setBoxColor(JTextField[][] sMatrix) {
		
		for(int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (((r >= 0) && (r <= 2)) || ((r >= 6) && (r <= 8))) {
					if (((c >= 0) && (c <= 2)) || ((c >= 6) && (c <= 8))) {
						sMatrix[r][c].setBackground(Color.ORANGE);
					} 
				} else {
					if ((c >= 3) && (c <= 5)) {
						sMatrix[r][c].setBackground(Color.ORANGE);
					}
				}
			}
		}
	}
}
