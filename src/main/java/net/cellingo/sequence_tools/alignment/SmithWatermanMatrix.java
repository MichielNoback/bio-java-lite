package net.cellingo.sequence_tools.alignment;
/**
 * This class constructs a three-dimensional matrix to be used in 
 * Smith/Waterman sequence alignment procedures.
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SmithWatermanMatrix {
	private int xSize;
	private int ySize;
	private int[][][] alignMatrix;
	
	//no-arg constructor; default matrix size is 250 * 250
	public SmithWatermanMatrix(){
		xSize = 250;
		ySize = 250;
		createMatrix();
	}
	//single-arg constructor; matrix size is size*size
	public SmithWatermanMatrix(int size){
		xSize = size;
		ySize = size;
		createMatrix();
	}
	//two-arg constructor; matrix size is x*y
	public SmithWatermanMatrix(int x, int y){
		xSize = x;
		ySize = y;
		createMatrix();
	}
	//method for getting score of certain position in matrix
	public int getScore(int x, int y){
		return alignMatrix[x][y][0];
	}
	//method for setting score of certain position in matrix
	public void setScore(int x, int y, int score){
		alignMatrix[x][y][0] = score;
	}
	//method for getting path-value at certain position in matrix 
	public int getPath(int x, int y){
		return alignMatrix[x][y][1];
	}
	//method for setting score of certain position in matrix
	public void setPath(int x, int y, int path){
		alignMatrix[x][y][1] = path;
	}
	
	private void createMatrix() {
		alignMatrix = new int[xSize][ySize][2];
		
		for(int x=0; x<xSize; x++){ //first dimension of alignment matrix
			for(int y=0; y<ySize; y++){	//second dimension of alignment matrix
				//third dimension of alignment matrix:
				//index [0] = score; index [1] = path
				alignMatrix[x][y][0] = 0;
				alignMatrix[x][y][1] = 0;
			}
		}
	}
	
}
