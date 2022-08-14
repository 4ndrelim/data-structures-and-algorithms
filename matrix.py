## Implementing Matrix Operations
##
## NOTE:
##      1. Matrix is implemented as a list of lists (i.e the null matrix would be [[]]
##      2. Each nested list is of the same length (cols)


"""
Scalar multiplication on a given matrix
Returns a new matrix after applying operation
"""
def scalar(A, c):
    ret = [[0 for j in range(len(A[0]))] for i in range(len(A))]
    for i in range(len(A)):
        for j in range(len(A[0])):
            ret[i][j] = A[i][j] * c
    return ret


"""
Adds two matrices element-wise, if possible
Returns a new amtrix after applying the operation
"""
def add(A, B):
    if len(A) != len(B) or len(A[0]) != len(B[0]):
        raise Exception('A and B are incompatible')
    ret = [[0 for j in range(len(A[0]))] for i in range(len(A))]

    for i in range(len(A)):
        for j in range(len(A[0])):
            ret[i][j] = A[i][j] + B[i][j]
    return ret


"""
Transposition on a given matrix
Return the new transposed matrix
"""
def transpose(A):
    ret = [[0 for i in range(len(A))] for j in range(len(A[0]))]

    for i in range(len(A)):
        for j in range(len(A[0])):
            ret[j][i] = A[i][j]
    return ret


"""
Apply matrix vector multiplication on two (compatible) matrices
Returns the new matrix that results from their product

"""
def vector_mult(A, B):
    if len(A[0]) != len(B):
        raise Exception('Incompatible dimensions for matrix vector multiplication')
    ret = [[0 for j in range(len(A[0]))] for i in range(len(A))]

    for i in range(len(A)):
        for j in range(len(A[0])):
            row = A[i][:]
            col = [B[x][j] for x in range(len(B))]
            ret[i][j] = sum(a * b for a, b in zip(row, col))
    return ret
