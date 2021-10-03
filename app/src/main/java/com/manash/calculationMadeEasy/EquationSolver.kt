package com.manash.calculationMadeEasy
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.android.synthetic.main.activity_equation_solver.*
import org.mariuszgromada.math.mxparser.Expression

class EquationSolver : AppCompatActivity() {
    private var checkValidity:Int=0
    private var elements:Int = 0
    private var checkNoOfElement:Int=0
    private var number=1
    private var index=0
    private var equation:String=""
    private var alphabet:String="abcdefghijklmnopqrstuvwxyz"
    private var switch="None"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equation_solver)
        input_text_equation_solver.showSoftInputOnFocus=false
    }
    @SuppressLint("SetTextI18n")
    private fun updateTextEquationSolver(txtToAdd: String){
        Log.d("updateTextEquationSolver",txtToAdd)
        if (switch=="variable" || switch=="degree"){
            val cursorPos: Int = answer_box_equation_solver.selectionStart
            answer_box_equation_solver.setText(answer_box_equation_solver.text.insert(cursorPos, txtToAdd).toString())
            answer_box_equation_solver.setSelection(cursorPos + txtToAdd.length)
        }
    }
    fun zeroBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("0") }
    fun oneBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("1") }
    fun twoBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("2") }
    fun threeBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("3") }
    fun fourBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("4") }
    fun fiveBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("5") }
    fun sixBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("6") }
    fun sevenBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("7") }
    fun eightBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("8") }
    fun nineBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("9") }
    fun pointBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver(".") }
    fun minusBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateTextEquationSolver("-") }
    fun clrBTN(@Suppress("UNUSED_PARAMETER")view: View) {
        input_text_equation_solver.setText("")
        answer_box_equation_solver.setText("")
        checkValidity=0
        elements= 0
        checkNoOfElement=0
        number=1
        index=0
        equation=""
        switch="None"
    }
    fun backspaceBTN(@Suppress("UNUSED_PARAMETER")view: View) {
        val oldStr:String =answer_box_equation_solver.text.toString()
        val cursorPos:Int=answer_box_equation_solver.selectionStart
        val leftStr: String= oldStr.subSequence(0,cursorPos).toString()
        if(leftStr.isEmpty()){
            return
        }
        val rightStr:String=oldStr.subSequence(cursorPos,oldStr.length).toString()
        answer_box_equation_solver.setText(String.format("%s%s",leftStr.dropLast(1),rightStr))
        if(answer_box_equation_solver.text.toString().isNotEmpty()) {
            answer_box_equation_solver.setSelection(cursorPos - 1)
        }
        else{
            answer_box_equation_solver.setSelection(0)
        }
    }
    fun mode(@Suppress("UNUSED_PARAMETER")view: View) {
        val intent= Intent(this,MainActivity ::class.java)
        startActivity(intent)
    }
    @SuppressLint("SetTextI18n")
    fun varBTN(@Suppress("UNUSED_PARAMETER")view: View) {
        input_text_equation_solver.setText("Unknowns?")
        checkValidity=1
        polynomial.isClickable=false
        polynomial.visibility=View.GONE
        LinearEqn.visibility=View.VISIBLE
        LinearEqn.isClickable=true
        switch="variable"
    }
    @SuppressLint("SetTextI18n")
    fun variableSolutionBTN(@Suppress("UNUSED_PARAMETER")view: View){
        if(checkValidity==1) {
            elements = answer_box_equation_solver.text.toString().toInt()
            answer_box_equation_solver.setText("")
            checkValidity=2
            input_text_equation_solver.setText(alphabet[index++].plus(number.toString()))
        }
        else if(checkNoOfElement==elements*(elements+1)){
            input_text_equation_solver.setText(equation)
            equation=equation.drop(1)
            answer_box_equation_solver.setText(mathematicsPython(equation,elements+1))
        }
        else{
            checkNoOfElement++
            equation=equation.plus(",${answer_box_equation_solver.text}")
            answer_box_equation_solver.setText("")
            input_text_equation_solver.setText(alphabet[index++].plus(number.toString()))
            if(index>=elements+1){
                number++
                index=0
            }
        }
    }
    private fun mathematicsPython(userExp: String,noOfElement:Int):String{
        if(!Python.isStarted()){ Python.start(AndroidPlatform(this)) }
        val py: Python = Python.getInstance()
        val pyObj: PyObject =py.getModule("Python file")
        if(switch=="variable") {
            val obj: PyObject = pyObj.callAttr("polynomial", userExp, noOfElement)
            Log.d("python", obj.toString())
            return obj.toString()
        }
        else{
            val obj: PyObject = pyObj.callAttr("degree", userExp, noOfElement)
            Log.d("python", obj.toString())
            return obj.toString()
        }
    }
    @SuppressLint("SetTextI18n")
    fun degreeBTN(view: android.view.View) {
        input_text_equation_solver.setText("Highest degree?")
        checkValidity=1
        polynomial.isClickable=true
        polynomial.visibility=View.VISIBLE
        LinearEqn.visibility=View.GONE
        LinearEqn.isClickable=false
        switch="degree"
    }
    fun polynomialSolutionBTN(view: android.view.View) {
        if(checkValidity==1) {
            elements = answer_box_equation_solver.text.toString().toInt()+1
            answer_box_equation_solver.setText("")
            checkValidity=2
            input_text_equation_solver.setText(alphabet[index++].plus("?"))
        }
        else if(elements==0){
            input_text_equation_solver.setText("The value of variables are")
            equation=equation.drop(1)
            answer_box_equation_solver.setText(mathematicsPython(equation,elements+1))
        }
        else{
            elements--
            equation=equation.plus(",${answer_box_equation_solver.text}")
            answer_box_equation_solver.setText("")
            input_text_equation_solver.setText(alphabet[index++].plus("?"))
        }
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
}