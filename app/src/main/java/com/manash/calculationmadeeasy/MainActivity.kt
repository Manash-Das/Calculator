package com.manash.calculationmadeeasy

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input_text.showSoftInputOnFocus=false
    }
    @SuppressLint("SetTextI18n")
    private fun updateText(txtToAdd: String){
        val cursorPos: Int=input_text.selectionStart                            //cursor position
        input_text.setText(input_text.text.insert(cursorPos,txtToAdd).toString())
        input_text.setSelection(cursorPos+1)
    }
    private fun updateTrig(trigToAdd:String){
        val cursorPos: Int=input_text.selectionStart
        input_text.setText(input_text.text.insert(cursorPos,trigToAdd.plus("(")).toString())
        input_text.setSelection(cursorPos+4)
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
    fun powerBTN(@Suppress("UNUSED_PARAMETER")view: View) { updateOperator("^") }
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
        if(leftStr.takeLast(4)=="sin(" || leftStr.takeLast(4)=="cos(" || leftStr.takeLast(4)=="tan(" ){
            input_text.setText(String.format("%s%s",leftStr.dropLast(4),rightStr))
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
    fun equalBTN(@Suppress("UNUSED_PARAMETER")view: View) {
        val userExp:String=input_text.text.toString()
        if(userExp.isEmpty()){
            return
        }
        val exp=Expression(userExp)
        val result:String=exp.calculate().toString()
        if(result=="NaN"){
            answer_box.setText(getString(R.string.Error))
        }
        else {
            answer_box.setText(result)
        }
    }
    fun sin(@Suppress("UNUSED_PARAMETER")view: View) {
        updateTrig("sin")
    }
    fun cos(@Suppress("UNUSED_PARAMETER")view: View) {
        updateTrig("cos")
    }
    fun tan(@Suppress("UNUSED_PARAMETER")view: View){
        updateTrig("tan")
    }
    fun mode(@Suppress("UNUSED_PARAMETER")view: View) {
        return
    }
}

