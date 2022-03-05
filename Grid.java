package soduko;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Component;
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
	private JPanel sPanel;
	
	public Grid(ISudokuSolver s) {
		this.solver = s;
		int counter =0;
		sMatrix= new JTextField[9][9];
				SwingUtilities.invokeLater(() -> createWindow("Sudoku", 400, 400));
	}
	
	private void createWindow(String Title, int height, int width) {
		
		//Gör ett fönster.
		JFrame sFrame = new JFrame("Sudoku");
		sFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sFrame.setSize(width, height);
		
		
		//Gör en panel för sudokut.
		sPanel = new JPanel();

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
		
		
		//skapar knapparna
		JButton solveButton = new JButton("Solve");
		JButton clearButton = new JButton("Clear");
		JButton  nyBtn = new JButton ("New Sudoku");
		
		Container pane = sFrame.getContentPane();
		
		
		solveButton.addActionListener(event -> {
			//Konvertera värden som skrivits till "board" 
			int [][] values = new int[9][9];  // borde nog lägga allt detta i en metod 
			for(int row = 0; row < 9; row++) {
				for(int col = 0; col < 9; col++) {
					if(sMatrix[row][col].getText().length() == 0) {
						values[row][col] = 0;
					} else {
						try {
						
							//om siffran är för hög.
							if(Integer.parseInt(sMatrix[row][col].getText()) > 9) {
								JOptionPane.showMessageDialog(sFrame, "Enbart siffror 1-9 är tillåtna", "Error", JOptionPane.ERROR_MESSAGE);
								sMatrix[row][col].requestFocus();
								sMatrix[row][col].selectAll();
								return;
							}
							values[row][col] = Integer.parseInt(sMatrix[row][col].getText());
						} 
					
						//Om man t.ex. har skrivit in ord
						catch (Exception w){
							JOptionPane.showMessageDialog(sFrame, "Enbart siffror 1-9 är tillåtna", "Error", JOptionPane.ERROR_MESSAGE);
							sMatrix[row][col].requestFocus();
							sMatrix[row][col].selectAll();
							return;
							}
						}
					}
				}
				solver.setMatrix(values);
			
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
		int [][] newMatrix = new int [dim][dim];
		for (int row=0; row<dim;row++) {
			String line = scanner.nextLine();
			Scanner lineScanner = new Scanner(line);
			
			for (int i = 0; i < 9 ; i++) {
				newMatrix[row][i]= lineScanner.nextInt();
				}
			
		}
		solver.setMatrix(newMatrix);
		buildBoard( 9,false);
		
	}

	private void clearBoard(int i) {
		this.buildBoard(9, true);
	
		
	}

	private void buildBoard(int dim, boolean clear) {
		String t;

		// Catch-block catches both IllegalArgumentException from solver.setNumber
		// as well as NumberFormatException from Integer.parseInt.
		// If invalid, set the number back to 0.
		
		if (clear) {
			this.solver.clear();
		}
		
		//setBoxColor(sMatrix);
		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
	
				int nbr = this.solver.get(r, c);
				if(nbr!=0) {
					sMatrix[r][c].setEditable(false);
				}else {
					sMatrix[r][c].setEditable(true);
				}
				String val = nbr > 0 ? String.valueOf(nbr) : "";
				JTextField box = new JTextField();

				
				
				
				sMatrix[r][c].setText(val);
			}
		}
		

		
	}
	

	private void rebuildBoard(int dim) {
		
		this.buildBoard(dim, false);
		
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
