import numpy as np
def complex(Expression):
    return eval(Expression)

def polynomial(allElement,noOfElement):
    matrix=[int(i) for i in allElement.split(",")]
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
        ans=ans+"{:.2f}".format(i)+str(" , ")
    return ans

def degree(allElement,x):
    matrix=[int(i) for i in allElement.split(",")]
    coeff=np.roots(matrix)
    ans=str(coeff)
    return ans