package com.manash.calculationMadeEasy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.android.synthetic.main.activity_equation_solver.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.answer_box
import kotlinx.android.synthetic.main.activity_main.input_text
import org.mariuszgromada.math.mxparser.Expression

class EquationSolver : AppCompatActivity() {
    private var flag:Int=0
    private var elements:Int = 0
    private var count:Int=0
    private var number=1
    private var index=0
    private var array:String=""
    private var alphabet:String="abcdefghijklmnopqrstuvwxyz"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equation_solver)
    }
    @SuppressLint("SetTextI18n")
    private fun updateText(txtToAdd: String){
        if (flag==0){
            val cursorPos: Int = input_text.selectionStart
            input_text.setText(input_text.text.insert(cursorPos, txtToAdd).toString())
            input_text.setSelection(cursorPos + txtToAdd.length)
        }
        else{
            val cursorPos: Int = answer_box.selectionStart
            answer_box.setText(answer_box.text.insert(cursorPos, txtToAdd).toString())
            answer_box.setSelection(cursorPos + txtToAdd.length)
        }
    }
    private fun updateOperator(oprToAdd:String){
        val cursorPos:Int=input_text.selectionStart
        val oldStr:String=input_text.text.toString()
        if(oldStr.isEmpty()){
            updateText("0")
            updateText(oprToAdd)
            return
        }
        var leftStr:String=oldStr.subSequence(0,cursorPos).toString()
        val rightStr:String=oldStr.subSequence(cursorPos,oldStr.length).toString()
        val symbols= mutableListOf("+","-","*","/","^")
        if (symbols.contains(oldStr.last().toString())){
            leftStr=leftStr.dropLast(1)
            input_text.setText(leftStr.plus(oprToAdd.plus(rightStr)))
            input_text.setSelection(cursorPos)
        }
        else{
            input_text.setText(leftStr.plus(oprToAdd.plus(rightStr)))
            input_text.setSelection(cursorPos+1)
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

    fun openBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText("(") }
    fun closeBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateText(")") }

    fun plusBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateOperator("+") }
    fun minusBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateOperator("-") }
    fun multiplyBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateOperator("*")}
    fun divideBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateOperator("/") }

    fun clrBTN(@Suppress("UNUSED_PARAMETER")view: View) { input_text.setText(""); answer_box.setText("") }
    fun backspaceBTN(@Suppress("UNUSED_PARAMETER")view: View) {
        val oldStr:String =input_text.text.toString()
        val cursorPos:Int=input_text.selectionStart
        val leftStr: String= oldStr.subSequence(0,cursorPos).toString()
        if(leftStr.isEmpty()){
            return
        }
        val rightStr:String=oldStr.subSequence(cursorPos,oldStr.length).toString()
        if(leftStr.takeLast(4)=="sin(" || leftStr.takeLast(4)=="cos(" || leftStr.takeLast(4)=="tan("){
            input_text.setText(String.format("%s%s",leftStr.dropLast(4),rightStr))
            return
        }
        if(leftStr.takeLast(2)=="pi") {
            input_text.setText(String.format("%s%s", leftStr.dropLast(4), rightStr))
            return
        }
        if(leftStr.takeLast(3)=="ans"){
            input_text.setText(String.format("%s%s",leftStr.dropLast(3),rightStr))
            return
        }
        input_text.setText(String.format("%s%s",leftStr.dropLast(1),rightStr))
        if(input_text.text.toString().isNotEmpty()) {
            input_text.setSelection(cursorPos - 1)
        }
        else{
            input_text.setSelection(0)
        }
    }
    fun mode(@Suppress("UNUSED_PARAMETER")view: View) {
        val intent= Intent(this,MainActivity ::class.java)
        startActivity(intent)
    }
    fun variable(view: android.view.View) {
        input_text.setText("Unknowns?")
        flag=1
        //equal.text = "Done"
//        equal.visibility = GONE
//        equal.isClickable=false
//        done.visibility= VISIBLE
//        done.isClickable=true
    }
    fun equalBTN(view: View){
        Log.d("number",number.toString())
        Log.d("index",index.toString())
        Log.d("Array",array)
        Log.d("count", count.toString())
        if(flag==1) {
            elements = answer_box.text.toString().toInt()
            answer_box.setText("")
            flag=2
            input_text.setText(alphabet[index++].plus(number.toString()))
        }
        else if(count==elements*(elements+1)){
            input_text.setText("Calculating")
            array=array.drop(1)
            answer_box.setText(mathematicsPython(array,elements+1))
        }
        else{
            count++
            array=array.plus(",${answer_box.text}")
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
        val pyObj: PyObject =py.getModule("equation solver")
        val obj: PyObject =pyObj.callAttr("polynomial",userExp,noOfElement)
        Log.d("python",obj.toString())
        return obj.toString()
    }
}