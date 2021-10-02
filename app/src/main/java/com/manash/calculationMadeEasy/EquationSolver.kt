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
import kotlinx.android.synthetic.main.activity_main.answer_box
import kotlinx.android.synthetic.main.activity_main.input_text
class EquationSolver : AppCompatActivity() {
    private var checkValidity:Int=0
    private var elements:Int = 0
    private var checkNoOfElement:Int=0
    private var number=1
    private var index=0
    private var equation:String=""
    private var alphabet:String="abcdefghijklmnopqrstuvwxyz"
    private var switch="variable"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equation_solver)
        input_text.showSoftInputOnFocus=false
    }
    @SuppressLint("SetTextI18n")
    private fun updateText(txtToAdd: String){
        if (checkValidity!=0){
            val cursorPos: Int = answer_box.selectionStart
            answer_box.setText(answer_box.text.insert(cursorPos, txtToAdd).toString())
            answer_box.setSelection(cursorPos + txtToAdd.length)
        }
    }
    fun zeroBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("0") }
    fun oneBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("1") }
    fun twoBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("2") }
    fun threeBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("3") }
    fun fourBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("4") }
    fun fiveBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("5") }
    fun sixBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("6") }
    fun sevenBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("7") }
    fun eightBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("8") }
    fun nineBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("9") }
    fun pointBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText(".") }
    fun minusBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("-") }
    fun clrBTN(@Suppress("UNUSED_PARAMETER")view: View) {
        input_text.setText("")
        answer_box.setText("")
        checkValidity=0
        elements= 0
        checkNoOfElement=0
        number=1
        index=0
        equation=""
        switch="variable"
    }
    fun backspaceBTN(@Suppress("UNUSED_PARAMETER")view: View) {
        val oldStr:String =answer_box.text.toString()
        val cursorPos:Int=answer_box.selectionStart
        val leftStr: String= oldStr.subSequence(0,cursorPos).toString()
        if(leftStr.isEmpty()){
            return
        }
        val rightStr:String=oldStr.subSequence(cursorPos,oldStr.length).toString()
        answer_box.setText(String.format("%s%s",leftStr.dropLast(1),rightStr))
        if(answer_box.text.toString().isNotEmpty()) {
            answer_box.setSelection(cursorPos - 1)
        }
        else{
            answer_box.setSelection(0)
        }
    }
    fun mode(@Suppress("UNUSED_PARAMETER")view: View) {
        val intent= Intent(this,MainActivity ::class.java)
        startActivity(intent)
    }
    @SuppressLint("SetTextI18n")
    fun variable(@Suppress("UNUSED_PARAMETER")view: View) {
        input_text.setText("Unknowns?")
        checkValidity=1
        polynomial.isClickable=false
        polynomial.visibility=View.GONE
        equal.visibility=View.VISIBLE
        equal.isClickable=true
    }
    @SuppressLint("SetTextI18n")
    fun equalBTN(@Suppress("UNUSED_PARAMETER")view: View){
        if(checkValidity==1) {
            elements = answer_box.text.toString().toInt()
            answer_box.setText("")
            checkValidity=2
            input_text.setText(alphabet[index++].plus(number.toString()))
        }
        else if(checkNoOfElement==elements*(elements+1)){
            input_text.setText("The value of variables are")
            equation=equation.drop(1)
            answer_box.setText(mathematicsPython(equation,elements+1))
        }
        else{
            checkNoOfElement++
            equation=equation.plus(",${answer_box.text}")
            answer_box.setText("")
            input_text.setText(alphabet[index++].plus(number.toString()))
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
    fun degree(view: android.view.View) {
        input_text.setText("Highest degree?")
        checkValidity=1
        polynomial.isClickable=true
        polynomial.visibility=View.VISIBLE
        equal.visibility=View.GONE
        equal.isClickable=false
        switch="degree"
    }
    fun polynomialSolution(view: android.view.View) {
        if(checkValidity==1) {
            elements = answer_box.text.toString().toInt()+1
            answer_box.setText("")
            checkValidity=2
            input_text.setText(alphabet[index++].plus("?"))
        }
        else if(elements==0){
            input_text.setText("The value of variables are")
            equation=equation.drop(1)
            answer_box.setText(mathematicsPython(equation,elements+1))
        }
        else{
            elements--
            equation=equation.plus(",${answer_box.text}")
            answer_box.setText("")
            input_text.setText(alphabet[index++].plus("?"))
        }
    }
}