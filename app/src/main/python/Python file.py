import numpy as np
def complex(Expression):
    try:
        ans=eval(Expression)
        return "{:.3f}".format(ans)
    except:
        return "syntax Error"

def polynomial(allElement,noOfElement):
    matrix=[float(i) for i in allElement.split(",")]
    matrix.insert(0, -1)
    print(matrix)
    lisA=[]
    lisB=[]
    for i in range(1,len(matrix)):
        if(i % noOfElement==0):
            lisB.append(matrix[i])
        else:
            lisA.append(matrix[i])
    matA=np.array(lisA).reshape(noOfElement-1,noOfElement-1)
    matB=np.array(lisB)
    if np.allclose(np.linalg.det(matA),0):
        return "No possible solution"
    try:
        result=np.matmul(np.linalg.inv(matA),matB)
        ans='['
        for i in result:
            ans=ans + str(round(i,2)) +","
        ans=ans.strip(",")
        ans=ans+"]"
    except :
        ans="Have no unique solution"
    return ans


def degree(allElement,x):
    # allElement = "1.0,2.0,3.0"
    matrix=[float(i) for i in allElement.split(",")]
    try:
        coeff=np.roots(matrix)
        ans =""
        for i in coeff:
            ans=ans + str(round(i,2)) +","
    except:
        ans = "Not possible"
    return ans

def numberSystem(fromUnit, number, toUnit):
    try:
        decimal=int(number,int(fromUnit))
    except:
        return "Invalid"
    if toUnit=="10":
        return str(decimal)
    if toUnit=="2":
        return bin(decimal)[2:]
    if toUnit=="8":
        return oct(decimal)[2:]
    if toUnit=="16":
        return hex(decimal)[2:]

