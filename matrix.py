## Implementing Matrix Operations
##
## NOTE:
##      1. Matrix is implemented as a list of lists (i.e the null matrix would be [[]]
##      2. Each nested list is of the same length (cols)

import copy

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


"""
Inverse a given matrix using gaussian elimination
"""
def inverse(M):
    # check M is a square matrix
    if (len(M[0]) !=len(M)):
        raise Exception("Non-square matrices are not invertible")

    dim = len(M)

    # here we want to pad the columns of the given matrix with columns of identity matrix of the same order
    # e.g:
    #  [[1, 2, 3],         [[1, 2, 3, 1, 0, 0],
    #   [3, 4, 5],  --->    [4, 5, 6, 0, 1, 0],
    #   [6, 7, 8]]          [7, 8, 9, 0, 0, 1]]
    
    ret = transpose(M)
    # create the identity matrix
    identity = [[1 if i == j else 0 for j in range(dim)] for i in range(dim)]
    # pad to the tranposed matrix
    for row in identity:
        ret.append(row)
    # matrix on RHS
    ret = transpose(ret)

    for i in range(dim):
        # 1. find a row, i <= j < dim, that has a non-zero entry in its ith column and swap with row i
        for j in range(i, dim):
            if ret[j][i] != 0:
                ret[i], ret[j] = ret[j], ret[i]
                break
        # if no such row exists (i.e entire column of 0s) -> matrix is not invertible; exception is thrown
        else:
            raise Exception("Non-square matrices cannot be inverted!")

        # scaling factor
        factor = ret[i][i]
        # 2. multiply the new ith row by a scalar such that the value in the ith column becomes 1
        for c in range(i, len(ret[i])):
            ret[i][c] *= 1/factor

        # add multiples of the new ith row to all other rows such that the value in their ith column becomes 0 (LHS matix becomes RREF)
        for r in range(dim):
            # make sure do not remove the ith row
            if r != i:
                if ret[r][i] != 0:
                    mult = -1 * ret[r][i]
                    for c in range(len(ret[i])):
                        ret[r][c] += mult * ret[i][c]

    # return LHS matrix
    return transpose(transpose(ret)[dim:])
    
def print_matrix(M):
    string = "[\n"
    for row in M:
        string += " ["
        string += ', '.join([str(item) for item in row])
        string += "]"
        string += ',\n'
    string = string[:-2]
    string += "\n]"
    print(string)
    
