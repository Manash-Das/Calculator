package com.manash.calculationMadeEasy
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.android.synthetic.main.activity_equation_solver.*
import kotlinx.android.synthetic.main.activity_main.*
import org.mariuszgromada.math.mxparser.Expression

class EquationSolver : AppCompatActivity() {
    private var checkValidity:Int=0
    private var elements:Int = 0
    private var checkNoOfElement:Int=0
    private var number=1
    private var index=-1
    private var alphabet:String="abcdefghijklmnopqrstuvwxyz"
    private var switch="None"
    private var limit = 0
    private var inputList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equation_solver)
        answerBoxEquationSolver.showSoftInputOnFocus = false
        ArrayAdapter.createFromResource(this, R.array.Menu, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerEquationSolver.adapter = adapter
        }
        spinnerEquationSolver.setSelection(2)

        spinnerEquationSolver.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val intent:Intent
                when (p2) {
                    0->{
                        intent=Intent(this@EquationSolver,MainActivity::class.java)
                        startActivity(intent)
                    }
                    1 -> {
                        intent = Intent(this@EquationSolver, ComplexNumberActivity::class.java)
                        startActivity(intent)
                    }
                    3 -> {
                        intent = Intent(this@EquationSolver, UnitConverter::class.java)
                        startActivity(intent)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        zeroEquationSolver.setOnClickListener{updateTextEquationSolver("0")}
        oneEquationSolver.setOnClickListener{updateTextEquationSolver("1")}
        twoEquationSolver.setOnClickListener{updateTextEquationSolver("2")}
        threeEquationSolver.setOnClickListener{updateTextEquationSolver("3")}
        fourEquationSolver.setOnClickListener{updateTextEquationSolver("4")}
        fiveEquationSolver.setOnClickListener{updateTextEquationSolver("5")}
        sixEquationSolver.setOnClickListener{updateTextEquationSolver("6")}
        sevenEquationSolver.setOnClickListener{updateTextEquationSolver("7")}
        eightEquationSolver.setOnClickListener{updateTextEquationSolver("8")}
        nineEquationSolver.setOnClickListener{updateTextEquationSolver("9")}

        pointEquationSolver.setOnClickListener{updateTextEquationSolver(".")}
        openBracketEquationSolver.setOnClickListener{updateTextEquationSolver("(")}
        closeBracketEquationSolver.setOnClickListener{updateTextEquationSolver(")")}
        squareRootEquationSolver.setOnClickListener { updateTextEquationSolver("sqrt(") }

        plusEquationSolver.setOnClickListener{updateTextEquationSolver("+")}
        minusEquationSolver.setOnClickListener{updateTextEquationSolver("-")}
        multiplyEquationSolver.setOnClickListener{updateTextEquationSolver("*")}
        divideEquationSolver.setOnClickListener{updateTextEquationSolver("/")}
        backspaceEquationSolver.setOnClickListener{backspaceBTNEquationSolver()}
        clearEquationSolver.setOnClickListener { clrBTNEquationSolver() }
        arrow_up.setOnClickListener { arrowUpButton() }
        arrow_down.setOnClickListener { arrowDownButton() }

        variableEquationSolver.setOnClickListener { answerBoxEquationSolver.textSize = 60F; varBTNEquationSolver() }
        degreeEquationSolver.setOnClickListener { answerBoxEquationSolver.textSize = 60F; degreeBTNEquationSolver() }
        polynomialEquationSolver.setOnClickListener { answerBoxEquationSolver.textSize = 60F; polynomialSolutionBTN() }
        LinearEqnEquationSolver.setOnClickListener { answerBoxEquationSolver.textSize = 60F;variableSolutionBTN() }
    }
    override fun onStart() {
        super.onStart()
        spinnerEquationSolver.setSelection(2)
    }
    private fun updateTextEquationSolver(txtToAdd: String){
        if (switch=="variable" || switch=="degree"){
            val cursorPos: Int = answerBoxEquationSolver.selectionStart
            answerBoxEquationSolver.setText(answerBoxEquationSolver.text.insert(cursorPos, txtToAdd).toString())
            answerBoxEquationSolver.setSelection(cursorPos + txtToAdd.length)
        }
    }
    private fun clrBTNEquationSolver() {
        inputTextEquationSolver.text = ""
        answerBoxEquationSolver.setText("")
        checkValidity=0
        elements= 0
        checkNoOfElement=0
        number=1
        index=-1
        switch="None"
        limit = 0
        answerBoxEquationSolver.textSize = 60F
        inputList.clear()
    }
    private fun backspaceBTNEquationSolver() {
        val oldStr:String =answerBoxEquationSolver.text.toString()
        val cursorPos:Int=answerBoxEquationSolver.selectionStart
        val leftStr: String= oldStr.subSequence(0,cursorPos).toString()
        if(leftStr.isEmpty()){
            return
        }
        val rightStr:String=oldStr.subSequence(cursorPos,oldStr.length).toString()
        answerBoxEquationSolver.setText(String.format("%s%s",leftStr.dropLast(1),rightStr))
        if(answerBoxEquationSolver.text.toString().isNotEmpty()) {
            answerBoxEquationSolver.setSelection(cursorPos - 1)
        }
        else{
            answerBoxEquationSolver.setSelection(0)
        }
    }
    //controls all button needed for solution of variable
    private fun varBTNEquationSolver() {
        inputList.clear()
        inputTextEquationSolver.text = getString(R.string.unknown)
        checkValidity=1
        polynomialEquationSolver.isClickable=false
        polynomialEquationSolver.visibility=View.GONE
        LinearEqnEquationSolver.visibility=View.VISIBLE
        LinearEqnEquationSolver.isClickable=true
        switch="variable"
    }
    //performs solution for polynomial equation
    private fun variableSolutionBTN(){
        when {
            checkValidity==1 -> {
                elements = answerBoxEquationSolver.text.toString().toInt()
                if (elements >26){
                    Toast.makeText(applicationContext,"maximum limit 26",Toast.LENGTH_LONG).show()
                    varBTNEquationSolver()
                    return
                }
                limit = elements*(elements+1)
                answerBoxEquationSolver.setText("")
                checkValidity=2
                inputTextEquationSolver.text = alphabet[++index].plus("$number")
            }
            limit-1 <= (number - 1) * (elements + 1) + index -> {
                LinearEqnEquationSolver.isClickable = false
                val equationSolved=mathematicsModule(answerBoxEquationSolver.text.toString())
                try {
                    inputList[(number - 1) * (elements + 1) + index] = equationSolved
                }catch (e:IndexOutOfBoundsException){
                    inputList.add(((number - 1) * (elements + 1) + index), equationSolved)
                }

                answerBoxEquationSolver.textSize = 40F
                var equation =""
                for(items in inputList){
                    equation = equation.plus(",$items")
                }
                equation=equation.drop(1)
                answerBoxEquationSolver.setText(mathematicsPython(equation,elements+1))
            }
            else -> {
                arrow_up.isClickable = true
                arrow_down.isClickable = true
                val equationSolved=mathematicsModule(answerBoxEquationSolver.text.toString())
                try {
                    inputList[(number - 1) * (elements + 1) + index] = equationSolved
                }catch (e:IndexOutOfBoundsException){
                    inputList.add(((number - 1) * (elements + 1) + index), equationSolved)
                }
                try{
                    answerBoxEquationSolver.setText(inputList[(number - 1) * (elements + 1) + index+1])
                }catch (e:IndexOutOfBoundsException){
                    answerBoxEquationSolver.setText("")
                }
                if(index==elements){
                    number++
                    index=-1
                }
                inputTextEquationSolver.text = alphabet[++index].plus("$number")
                checkNoOfElement = inputList.size
            }
        }
    }
    private fun mathematicsPython(userExp: String,noOfElement:Int):String{
        if(!Python.isStarted()){ Python.start(AndroidPlatform(this)) }
        val py: Python = Python.getInstance()
        val pyObj: PyObject =py.getModule("Python file")
        return if(switch=="variable") {
            val obj: PyObject = pyObj.callAttr("polynomial", userExp, noOfElement)
            obj.toString()
        } else{
            val obj: PyObject = pyObj.callAttr("degree", userExp, noOfElement)
            obj.toString()
        }
    }
    private fun degreeBTNEquationSolver() {
        inputTextEquationSolver.text= getString(R.string.highestDegree)
        checkValidity = 1
        polynomialEquationSolver.isClickable=true
        polynomialEquationSolver.visibility=View.VISIBLE
        LinearEqnEquationSolver.visibility=View.GONE
        LinearEqnEquationSolver.isClickable =false
        switch="degree"
        inputList.clear()
    }
    private fun polynomialSolutionBTN() {
        when {
            checkValidity==1 -> {
                elements = answerBoxEquationSolver.text.toString().toInt()
                limit = elements+1
                answerBoxEquationSolver.setText("")
                checkValidity=2
                inputTextEquationSolver.text = alphabet[++index].plus("$number")
            }
            limit-1 <= (number - 1) * (elements + 1) + index -> {
                polynomialEquationSolver.isClickable = false
                val equationSolved=mathematicsModule(answerBoxEquationSolver.text.toString())
                try {
                    inputList[(number - 1) * (elements + 1) + index] = equationSolved
                }catch (e:IndexOutOfBoundsException){
                    inputList.add(((number - 1) * (elements + 1) + index), equationSolved)
                }

                answerBoxEquationSolver.textSize = 40F
                var equation =""
                for(items in inputList){
                    equation = equation.plus(",$items")
                    Toast.makeText(applicationContext, equation,Toast.LENGTH_SHORT).show()
                }
                equation=equation.drop(1)
                answerBoxEquationSolver.setText(mathematicsPython(equation,elements+1))
            }
            else -> {
                arrow_up.isClickable = true
                arrow_down.isClickable = true
                val equationSolved=mathematicsModule(answerBoxEquationSolver.text.toString())
                try {
                    inputList[(number - 1) * (elements + 1) + index] = equationSolved
                }catch (e:IndexOutOfBoundsException){
                    inputList.add(((number - 1) * (elements + 1) + index), equationSolved)
                }
                try{
                    answerBoxEquationSolver.setText(inputList[(number - 1) * (elements + 1) + index+1])
                }catch (e:IndexOutOfBoundsException){
                    answerBoxEquationSolver.setText("")
                }
                if(index==elements){
                    number++
                    index=-1
                }
                inputTextEquationSolver.text = alphabet[++index].plus("$number")
                checkNoOfElement = inputList.size
            }
        }

//        var equation =""
//        when {
//            checkValidity==1 -> {
//                elements = answerBoxEquationSolver.text.toString().toInt()
//                answerBoxEquationSolver.setText("")
//                checkValidity=2
//                inputTextEquationSolver.text = alphabet[index++].plus("?")
//            }
//            elements==0 -> {
//                equation=equation.plus(",${answerBoxEquationSolver.text}")
//                inputTextEquationSolver.text = getString(R.string.displayOutput)
//                answerBoxEquationSolver.textSize = 30F
//                equation=equation.drop(1)
//                answerBoxEquationSolver.setText(mathematicsPython(equation,elements+1))
//            }
//            else -> {
//                elements--
//                equation=equation.plus(",${answerBoxEquationSolver.text}")
//                answerBoxEquationSolver.setText("")
//                inputTextEquationSolver.text = alphabet[index++].plus("?")
//            }
//        }
    }
    private fun mathematicsModule(userExpression: String): String {
        val exp = Expression(userExpression)
        val result= exp.calculate().toString()
        Log.d("result",result)
        if(result=="Infinity"){
            return result
        }
        if(result.length>7) {
            var check=0
            var decimal=""
            var power=""
            for (items in result) {
                if (items == 'E') {
                    check = 1
                    power = " $power"

                }
                if (check==0) {
                    decimal = "$decimal$items"
                } else {
                    power = "$power$items"
                }
            }
            if(decimal.length>7) {
                decimal = decimal.substring(0, 7)
            }
            return "$decimal$power"
        }
        return result
    }
    private fun arrowUpButton(){
        LinearEqnEquationSolver.isClickable = true
        Toast.makeText(applicationContext,"($number, $index, ${(number - 1) * (elements + 1) + index}, $elements)",Toast.LENGTH_SHORT).show()
        if(index == 0){
            if(number ==1){
                return
            }
            index = elements
            number--
        }
        else{
            index--
        }
        inputTextEquationSolver.text= alphabet[index].plus(number.toString())
        answerBoxEquationSolver.setText(inputList[(number-1)*(elements+1)+index])
    }
    private fun arrowDownButton(){
        Toast.makeText(applicationContext,"${(number-1)*(elements+1)+index},${inputList.size}",Toast.LENGTH_SHORT).show()
        if(inputList.size <= (number-1)*(elements+1)+index+1){
            return
        }
        if(index == elements){
            index = 0
            number++
        }
        else{
            index++
        }
        inputTextEquationSolver.text= alphabet[index].plus(number.toString())
        answerBoxEquationSolver.setText(inputList[(number-1)*(elements+1)+index])
    }


}


















