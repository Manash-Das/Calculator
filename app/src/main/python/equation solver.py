import numpy as np
def polynomial(allElement,noOfElement):
    matrix=[int(i) for i in allElement.split(",")]
    print(matrix)
    matrix.insert(0, -1)
    lisA=[]
    lisB=[]
    for i in range(1,len(matrix)):
        if(i % noOfElement==0):
            lisB.append(matrix[i])
        else:
            lisA.append(matrix[i])
    matA=np.array(lisA)
    matB=np.array(lisB)
    matA=matA.reshape(noOfElement-1,noOfElement-1)
    result=np.matmul(np.linalg.inv(matA),matB)
    ans=''
    for i in result:
        ans=ans+str(i)+str(" , ")
    return ans

def quadratic(coeff):
    return np.roots(coeff)