import java.awt.Rectangle;

public class LinearAlgebra {
    /* *************
         FUNCTIONS
       ************* */

    /* Mathematics */
    /**
     * Adds two matrices together
     * @param m1 double[][]: First matrix used for addition
     * @param m2 double[][]: Second matrix used for addition
     * @return double[][]: Result matrix
     * @throws MatrixSizeMismatchException Throws if sizes of matrices do not match
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @uses boolean validMatrix(double[][])
     */
    public static double[][] add(double[][] m1, double[][] m2) {
        if(!validMatrix(m1)) throw new InvalidMatrixException(m1);
        if(!validMatrix(m2)) throw new InvalidMatrixException(m2);
        if(m1.length!=m2.length||m1[0].length!=m2[0].length) throw new MatrixSizeMismatchException(m1,m2, "MatrixSizeMismatchException: Matrices must be the same size to add");

        double[][] result = new double[m1.length][m1[0].length];

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                result[i][j] = m1[i][j] + m2[i][j];
            }
        }

        return result;
    }

    /**
     * Adds two vectors together
     * @param v1 double[]: First vector to be added
     * @param v2 double[]: Second vector to be added
     * @return double[][]: Result vector
     * @throws VectorSizeMismatchException Thrown if sizes of vectors do not match
     */
    public static double[] add(double[] v1, double[] v2) {
        if(v1.length!=v2.length) throw new VectorSizeMismatchException(v1,v2, "VectorSizeMismatchException: Vectors must be the same size to add");

        double[] result = new double[v1.length];

        for(int i = 0; i < v1.length; i++) result[i] = v1[i] + v2[i];

        return result;
    }

    /**
     * Returns the adjugate matrix of a matrix
     * @param m double[][]: The matrix to find the adjugate matrix of
     * @return double[][]: The adjugate matrix of the given matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @throws NotSquareException Throws when the matrix is not square
     * @uses boolean validMatrix(double[][])
     * @uses double cofactor(double[][],int,int)
     * @uses double determinant(double[][])
     * @uses double[][] minor(double[][],int,int)
     */
    public static double[][] adjugateMatrix(double[][] m) {
        if(!validMatrix(m)) throw new InvalidMatrixException(m);
        double[][] result = new double[m.length][m[0].length];

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                result[j][i] = cofactor(m, i, j);
            }
        }

        return result;
    }

    /**
     * Returns the cofactor of a matrix for some given row and column
     * @param m double[][]: The matrix to find the cofactor of
     * @param r int: The row number used to find the cofactor
     * @param c int: The column number used to find the cofactor
     * @return double: The cofactor of the matrix given row r and column c
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @throws NotSquareException Throws when the matrix is not square
     * @uses boolean validMatrix(double[][])
     * @uses double determinant(double[][])
     * @uses double[][] minor(double[][],int,int)
     */
    public static double cofactor(double[][] m, int r, int c) {
        return ((r + c) % 2 == 0 ? 1.0 : -1.0) * determinant(minor(m, r+1, c+1));
    }

    /**
     * Returns the determinant of the given matrix
     * @param m double[][]: The matrix to find the determinant of
     * @return double: The determinant of the matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @throws NotSquareException Throws when the matrix is not square
     * @uses boolean isSquare(double[][])
     * @uses boolean validMatrix(double[][])
     * @uses double[][] minor(double[][],int,int)
     */
    public static double determinant(double[][] m) {
        if(!isSquare(m)) throw new NotSquareException(m);
        if(m.length==1) return m[0][0];
        if(m.length==2) return m[0][0] * m[1][1] - m[0][1] * m[1][0];
        double result = 0.0;

        for(int i = 0; i < m.length; i++) result += (i % 2 != 0 ? -1.0 : 1.0) * m[0][i] * determinant(minor(m,1,i + 1));

        return result;
    }

    /**
     * Returns the inverse matrix of the given matrix
     * @param m double[][]: The matrix to find the inverse of
     * @return double[][] OR null: The inverse of the given matrix unless no inverse matrix exists; singular
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @throws NotSquareException Throws when the matrix is not square
     * @uses boolean validMatrix(double[][])
     * @uses boolean isSquare(double[][])
     * @uses double determinant(double[][])
     * @uses double[][] minor(double[][],int,int)
     * @uses double cofactor(double[][],int,int)
     */
    public static double[][] inverse(double[][] m) {
        double d = determinant(m);
        if (d==0) return null;
        double[][] result = new double[m.length][m[0].length];

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                result[j][i] = cofactor(m, i, j) / d;
            }
        }

        return result;
    }

    /**
     * Returns the matrix of cofactors for a given matrix
     * @param m double[][]: The matrix to find to matrix of cofactors of
     * @return double[][]: The matrix of cofactors for the given matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @throws NotSquareException Throws when the matrix is not square
     * @uses double cofactor(double[][])
     * @uses double determinant(double[][])
     * @uses double[][] minor(double[][],int,int)
     * @uses boolean isSquare(double[][])
     * @uses boolean validMatrix(double[][])
     */
    public static double[][] matrixOfCofactors(double[][] m) {
        double[][] result = new double[m.length][m[0].length];

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                result[i][j] = cofactor(m, i, j);
            }
        }

        return result;
    }

    /**
     * Returns the matrix of minors for the given matrix
     * @param m double[][]: The matrix to find the matrix of minors of
     * @return double[][]: The matrix of minors for the given matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @throws NotSquareException Throws when the matrix is not square
     * @uses boolean validMatrix(double[][])
     * @uses boolean isSquare(double[][])
     * @uses double determinant(double[][])
     * @uses double[][] minor(double[][],int,int)
     */
    public static double[][] matrixOfMinors(double[][] m) {
        double[][] result = new double[m.length][m[0].length];

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                result[i][j] = determinant(minor(m, i+1, j+1));
            }
        }

        return result;
    }

    /**
     * Returns the minor of the given matrix for the given row and column
     * @param m double[][]: The matrix to take the minor of
     * @param r int: The row to be removed. Must be 1 or greater
     * @param c int: The column to be removed. Must be 1 or greater
     * @return double[][]: The resulting matrix
     * @throws ArrayIndexOutOfBoundsException Throws if the row or column numbers passed are outside of the bounds of the matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @uses boolean validMatrix(double[][])
     */
    public static double[][] minor(double[][] m, int r, int c) {
        if(!validMatrix(m)) throw new InvalidMatrixException(m);
        if(m.length < r || m[0].length < c || c < 1 || r < 1) throw new ArrayIndexOutOfBoundsException("ArrayIndexOutOfBoundsException: The row and/or column to be removed from matrix is outside of the bounds of the matrix\n" +
                "Row: " + r + " Column: " + c + " Matrix size: " + m.length + "x" + m[0].length);

        double[][] result = new double[m.length - 1][m[0].length - 1];

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result.length; j++) {
                result[i][j] = m[i<r-1 ? i : i+1][j<c-1 ? j : j+1];
            }
        }

        return result;
    }

    /**
     * Multiplies a matrix by a constant and returns the result
     * @param k double: Constant matrix is multiplied by
     * @param m double[][]: Matrix to be multiplied
     * @return double[][]: Result of multiplication
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @uses boolean validMatrix(double[][])
     */
    public static double[][] multiply(double k, double[][] m) {
        if(!validMatrix(m)) throw new InvalidMatrixException(m);
        double[][] result = new double[m.length][m[0].length];

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                result[i][j] = k * m[i][j];
            }
        }

        return result;
    }

    /**
     * Multiplies a vector by a constant and returns the result
     * @param k double: Scalar matrix is multiplied by
     * @param v double[][]: Vector to be multiplied
     * @return double[]: Result of multiplication
     */
    public static double[] multiply(double k, double[] v) {
        double[] result = new double[v.length];
        for(int i = 0; i < v.length; i++) result[i] = k * v[i];
        return result;
    }

    /**
     * Multiplies two matrices together and returns the new matrix
     * @param m1 double[][]: First matrix being multiplied
     * @param m2 double[][]: Second matrix being multiplied
     * @return double[][]: Resulting matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @throws MatrixSizeMismatchException Throws when m1 columns and m2 rows do not match
     * @uses boolean validMatrix(double[][])
     */
    public static double[][] multiply(double[][] m1, double[][] m2) {
        if(!validMatrix(m1)) throw new InvalidMatrixException(m1);
        if(!validMatrix(m2)) throw new InvalidMatrixException(m2);
        if(m1[0].length != m2.length) throw new MatrixSizeMismatchException(m1, m2, "MatrixSizeMismatchException: Number of columns in matrix 1 must be equal to number of rows in matrix 2");
        double[][] result = new double[m1.length][m2[0].length];

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                double x = 0;

                for(int k = 0; k < m1[0].length; k++) {
                    x += m1[i][k] * m2[k][j];
                }

                result[i][j] = x;
            }
        }

        return result;
    }

    /**
     * Subtracts two matrices together
     * @param m1 double[][]: First matrix to be subtracted
     * @param m2 double[][]: Second matrix to be subtracted
     * @return double[][]: Result matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @throws MatrixSizeMismatchException Thrown if sizes of matrices do not match
     * @uses boolean validMatrix(double[][])
     */
    public static double[][] subtract(double[][] m1, double[][] m2) {
        if(!validMatrix(m1)) throw new InvalidMatrixException(m1);
        if(!validMatrix(m2)) throw new InvalidMatrixException(m2);
        if(m1.length!=m2.length||m1[0].length!=m2[0].length) throw new MatrixSizeMismatchException(m1,m2, "MatrixSizeMismatchException: Matrices must be the same size to subtract");

        double[][] result = new double[m1.length][m1[0].length];

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                result[i][j] = m1[i][j] - m2[i][j];
            }
        }

        return result;
    }

    /**
     * Subtracts two vectors together
     * @param v1 double[]: First vector to be subtracted
     * @param v2 double[]: Second vector to be subtracted
     * @return double[][]: Result vector
     * @throws VectorSizeMismatchException Thrown if sizes of vectors do not match
     */
    public static double[] subtract(double[] v1, double[] v2) {
        if(v1.length!=v2.length) throw new VectorSizeMismatchException(v1,v2, "VectorSizeMismatchException: Vectors must be the same size to subtract");

        double[] result = new double[v1.length];

        for(int i = 0; i < v1.length; i++) result[i] = v1[i] - v2[i];

        return result;
    }

    /**
     * Returns the trace of a matrix
     * @param m double[][]: Matrix to use
     * @return double: The trace of the matrix
     * @throws NotSquareException Throws when the matrix is not square
     * @throws InvalidMatrixException Throws when matrix is invalid
     */
    public static double trace(double[][] m) {
        if(!isSquare(m)) throw new NotSquareException(m, "Not Square Exception: Matrix must be square to find the trace");

        double result = 0.0;
        for(int i = 0; i < m.length; i++) result += m[i][i];
        return result;
    }

    /**
     * Returns the transpose of the given matrix
     * @param m double[][]: The matrix to find the transpose of
     * @return double[][]: The transposed matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @uses boolean validMatrix(double[][])
     */
    public static double[][] transpose(double[][] m) {
        if(!validMatrix(m)) throw new InvalidMatrixException(m);
        double[][] result = new double[m[0].length][m.length];

        for(int i = 0 ; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                result[i][j] = m[j][i];
            }
        }

        return result;
    }


    /* Utilities */
    /**
     * Checks to make sure all rows in a matrix are the same size. If so returns true; else returns false
     * @param m double[][]: Matrix to be check
     * @return boolean
     */
    public static boolean validMatrix(double[][] m) {
        int width = m[0].length;
        for(int i = 1; i < m.length; i++) {
            if(width!=m[i].length) return false;
        }
        return true;
    }

    /**
     * Returns a Rectangle object representing the size of the matrix
     * @param m double[][]: Matrix to find size of
     * @return Rectangle: Size of matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @apiNote new Rectangle(m.length,m[0].length) returns the same result and is faster but will not catch InvalidMatrixException
     */
    public static Rectangle getSize(double[][] m) {
        if(!validMatrix(m)) throw new InvalidMatrixException(m);
        return new Rectangle(m.length,m[0].length);
    }

    /**
     * Returns the height (the number of rows) of given matrix
     * @param m double[][]: Matrix to be checked
     * @return int: Height of matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @apiNote m.length returns the same result and is faster but will not catch InvalidMatrixException
     */
    public static int getHeight(double[][] m) {
        if(!validMatrix(m)) throw new InvalidMatrixException(m);
        return m.length;
    }

    /**
     * Returns the height (the number of columns) of a given matrix
     * @param m double[][]: Matrix to be check
     * @return int: Width of matrix
     * @throws InvalidMatrixException Throws when matrix is invalid
     * @apiNote m[0].length returns the same result and is faster but will not catch InvalidMatrixException
     */
    public static int getWidth(double[][] m) {
        if(!validMatrix(m)) throw new InvalidMatrixException(m);
        return m[0].length;
    }

    /**
     * Checks if the number of rows is equal to number of columns
     * @param m double[][]: Matrix to be checked
     * @return boolean
     * @throws InvalidMatrixException Throws when matrix is invalid
     */
    public static boolean isSquare(double[][] m) {
        return getHeight(m) == getWidth(m);
    }

    /**
     * Returns an identity matrix of specified size
     * @param size int: size of new identity matrix
     * @return double[][]: New identity matrix
     */
    public static double[][] newIdentityMatrix(int size) {
        double[][] result = new double[size][size];
        for(int i = 0; i < size; i++) {
            result[i][i] = 1.0;
        }
        return result;
    }

    /**
     * Returns a string that describes an matrix
     * @param m double[][]: Matrix to be described
     * @return String
     */
    public static String toString(double[][] m) {
        StringBuilder result = new StringBuilder();
        for(double[] r : m) {
            result.append("[");
            for(double x : r) {
                result.append(String.format("%6.5s,", (x < 10000 && x > -10000) ? x : "ovrflw"));
            }
            result.replace(result.lastIndexOf(","),result.lastIndexOf(",") + 1, "]\n");
        }
        return result.toString();
    }

    /**
     * Returns a string that describes an vector
     * @param v double[][]: Vector to be described
     * @return String
     */
    public static String toString(double[] v) {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for(double x : v) {
            result.append(String.format("%6.5s,", (x < 10000 && x > -10000) ? x : "ovrflw"));
        }
        result.replace(result.lastIndexOf(","),result.lastIndexOf(",") + 1, "]\n");

        return result.toString();
    }


    /* ************
        EXCEPTIONS
       ************ */
    /* Matrix Exceptions */
    /**
     * Contains a copy of the matrix that caused the exception to occur for error checking purposes
     */
    private static class MatrixRuntimeException extends RuntimeException {
        double[][] matrix;

        private MatrixRuntimeException(double[][] m, String message) {
            super(message);
            matrix = m;
        }
    }

    /**
     * Occurs when the matrix has an invalid format
     * @throws InvalidMatrixException Throws when matrix is invalid
     */
    public static class InvalidMatrixException extends MatrixRuntimeException {
        public InvalidMatrixException(double[][] m, String message) {
            super(m,message);
        }

        public InvalidMatrixException(double[][] m) {
            super(m, "InvalidMatrixException: Not all rows are of the same size");
        }
    }

    /**
     * Occurs when a matrix is expected to be square and is not
     * @throws NotSquareException Throws when the matrix is not square
     */
    public static class NotSquareException extends MatrixRuntimeException {
        public NotSquareException(double[][] m, String message) {
            super(m,message);
        }

        public NotSquareException(double[][] m) {
            super(m,"Not Square Exception: Operation requires matrix to be square");
        }
    }

    /**
     * Occurs when two matrices are expected to have compatible sizes
     * Stores both matrices
     */
    public static class MatrixSizeMismatchException extends MatrixRuntimeException {
        double[][] matrix2;
        public MatrixSizeMismatchException(double[][] m1, double[][] m2, String message) {
            super(m1, message);
            this.matrix2 = m2;
        }

        public MatrixSizeMismatchException(double[][] m1, double[][] m2) {
            super(m1, "Matrix Size Mismatch Exception: Matrices must have compatible sizes to perform operation");
            this.matrix2 = m2;
        }
    }

    /* Vector Exceptions */
    /**
     * Contains a copy of the vector that caused the exception to occur for error checking purposes
     */
    private static class VectorRuntimeException extends RuntimeException {
        double[] vector;

        private VectorRuntimeException(double[] v, String message) {
            super(message);
            this.vector = v;
        }
    }

    /**
     * Occurs when two vectors are expected to have compatible sizes
     * Stores both vectors
     */
    public static class VectorSizeMismatchException extends VectorRuntimeException {
        double[] vector2;

        public VectorSizeMismatchException(double[] v, double[] v2, String message) {
            super(v, message);
            this.vector2 = v2;
        }

        public VectorSizeMismatchException(double[] v, double[] v2) {
            super(v, "VectorSizeMismatchException: Vectors must be the same size to perform operation");
            this.vector2 = v2;
        }
    }
}