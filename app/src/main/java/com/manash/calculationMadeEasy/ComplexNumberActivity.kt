package com.manash.calculationMadeEasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import kotlinx.android.synthetic.main.complex_number.*
import kotlin.math.*

class ComplexNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complex_number)
        ArrayAdapter.createFromResource(this, R.array.Menu, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerComplex.adapter = adapter
        }
        spinnerComplex.setSelection(1)
        spinnerComplex.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val intent:Intent
                when (p2) {
                    0->{
                        intent=Intent(this@ComplexNumberActivity,MainActivity::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        intent = Intent(this@ComplexNumberActivity, EquationSolver::class.java)
                        startActivity(intent)
                    }
                    3 -> {
                        intent = Intent(this@ComplexNumberActivity, UnitConverter::class.java)
                        startActivity(intent)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        zeroComplex.setOnClickListener { updateText("0") }
        oneComplex.setOnClickListener { updateText("1") }
        twoComplex.setOnClickListener { updateText("2") }
        threeComplex.setOnClickListener { updateText("3") }
        fourComplex.setOnClickListener { updateText("4") }
        fiveComplex.setOnClickListener { updateText("5") }
        sixComplex.setOnClickListener { updateText("6") }
        sevenComplex.setOnClickListener { updateText("7") }
        eightComplex.setOnClickListener { updateText("8") }
        nineComplex.setOnClickListener { updateText("9") }


        pointComplex.setOnClickListener { updateText(".") }
        openBracketComplex.setOnClickListener { updateText("(") }
        closeBracketComplex.setOnClickListener { updateText(")") }

        plusComplex.setOnClickListener { updateOperator("+") }
        minusComplex.setOnClickListener { updateOperator("-") }
        multiplyComplex.setOnClickListener { updateOperator("*") }
        divideComplex.setOnClickListener { updateOperator("/") }

        clearComplex.setOnClickListener { inputTextComplex.setText(""); answerBoxComplex.text="" }
        backspaceComplex.setOnClickListener { backspaceBTN() }
        equalComplex.setOnClickListener { equalBTN() }
        imaginaryComplex.setOnClickListener { updateText("j") }
        polarComplex.setOnClickListener { updateText("pol(") }


        rectangularComplex.setOnClickListener { updateText("rect(") }
        absoluteComplex.setOnClickListener { updateText("abs(") }
        argumentComplex.setOnClickListener { updateText("arg(") }
        conjugateComplex.setOnClickListener { updateText("conj(") }
        commaComplex.setOnClickListener { updateText(",") }

    }
    override fun onStart() {
        super.onStart()
        inputTextComplex.showSoftInputOnFocus=false
        spinnerComplex.setSelection(1)
    }
    private fun backspaceBTN() {
        val oldStr:String =inputTextComplex.text.toString()
        val cursorPos:Int=inputTextComplex.selectionStart
        val leftStr: String= oldStr.subSequence(0,cursorPos).toString()
        if(leftStr.isEmpty()){
            return
        }
        val rightStr:String=oldStr.subSequence(cursorPos,oldStr.length).toString()
        if(leftStr.takeLast(4)=="abs(" || leftStr.takeLast(4)=="arg(" || leftStr.takeLast(4)=="pol("){
            inputTextComplex.setText(String.format("%s%s",leftStr.dropLast(4),rightStr))
            inputTextComplex.setSelection(cursorPos-4)
            return
        }
        if(leftStr.takeLast(5)=="conj(" || leftStr.takeLast(5)=="rect(") {
            inputTextComplex.setText(String.format("%s%s", leftStr.dropLast(5), rightStr))
            inputTextComplex.setSelection(cursorPos-5)
            return
        }
        inputTextComplex.setText(String.format("%s%s",leftStr.dropLast(1),rightStr))
        if(inputTextComplex.text.toString().isNotEmpty()) {
            inputTextComplex.setSelection(cursorPos-1)
        }
        else{
            inputTextComplex.setSelection(0)
        }
    }
    private fun equalBTN() {
        var userExp:String=inputTextComplex.text.toString()
        if(userExp.isEmpty()){ return }
        if(userExp.length>4){
            var part:String= userExp.substring(0,3)
            if(part=="pol" || part=="abs" || part=="arg" ){
                userExp=userExp.substring(4).dropLast(1)
                Log.d("equal_function :","if is working")
                answerBoxComplex.text = otherForm(part,userExp)
                return
            }
            part=userExp.substring(0,4)
            Log.d("equal_function part :",part)
            if(part=="rect"){
                userExp=userExp.substring(5).dropLast(1)
                answerBoxComplex.text= rectangularForm(userExp)
                return
            }
            if(part=="conj"){
                userExp=userExp.substring(5).dropLast(1)
                answerBoxComplex.text = otherForm(part,userExp)
                return
            }
        }
        answerBoxComplex.text = mathematicsPython(userExp)

    }
    private fun updateText(txtToAdd: String){
        val cursorPos: Int=inputTextComplex.selectionStart //cursor position
        inputTextComplex.setText(inputTextComplex.text.insert(cursorPos,txtToAdd).toString())
        inputTextComplex.setSelection(cursorPos+txtToAdd.length)
    }
    private fun updateOperator(oprToAdd:String){
        val cursorPos:Int=inputTextComplex.selectionStart
        val oldStr:String=inputTextComplex.text.toString()
        if(oldStr.isEmpty()){
            updateText("0")
            updateText(oprToAdd)
            return
        }
        var leftStr:String=oldStr.subSequence(0,cursorPos).toString()
        val rightStr:String=oldStr.subSequence(cursorPos,oldStr.length).toString()
        val symbols= mutableListOf("+","-","*","/")
        if (symbols.contains(oldStr.last().toString())){
            leftStr=leftStr.dropLast(1)
            inputTextComplex.setText(leftStr.plus(oprToAdd.plus(rightStr)))
            inputTextComplex.setSelection(cursorPos)
        }
        else{
            inputTextComplex.setText(leftStr.plus(oprToAdd.plus(rightStr)))
            inputTextComplex.setSelection(cursorPos+1)
        }

    }
    private fun rectangularForm(userExp:String):String{
        var radius=""
        var angle=""
        var flag=0
        if (userExp.contains('+') || userExp.contains('-') || userExp.contains('*')
            ||userExp.contains('/')){
            return "math Error"
        }
        // extracting angle and radius from user expression
        for(character in userExp) {
            if (character == ',') {
                flag = 1
                continue
            }
            if (flag == 0) {
                radius = "$radius$character"
            } else {
                angle = "$angle$character"
            }
        }
        if(angle.isEmpty()){
            angle ="0"
        }
        if(radius.isEmpty()){
            radius = "0"
        }
        val x= round(radius.toFloat() * cos(angle.toFloat())*1000.0)/1000.0
        val y= round(radius.toFloat() * sin(angle.toFloat())*1000.0)/1000.0
        return if (y< 0){
            "$x${y}j"
        } else
            "$x+${y}j"
    }
    private fun otherForm(sub:String, userExp:String):String{
        var real=""
        var imaginary=""
        var flag=0
        var sign=""
        if (userExp.contains(',')){
            return "Math Error"
        }
//         extracting real and imaginary part from expression
        for(character in userExp){
            if(flag==0){
                if (character =='+' || character =='-'){
                    sign = character.toString()
                    flag=1
                    continue
                }
                real="$real$character"
            }
            else{
                imaginary="$imaginary$character"
            }
        }
//        checking if imaginary is positive or negative
        if (real.isEmpty()){
            return "syntax Error"
        }
        if (imaginary.isEmpty()){
            imaginary ="0"
        }
        if (imaginary.contains('j')){
            imaginary = imaginary.replace("j","")
        }
        val argument = round(atan(imaginary.toFloat()/real.toFloat())*10000.0)/10000.0
        val absolute = round(hypot(real.toFloat(), imaginary.toFloat())*10000.0)/10000.0
        when (sub) {
            "pol" -> {
                return "$absolute,$argument"
            }
            "arg" -> {
                return "$argument"
            }
            "abs" -> {
                return "$absolute"
            }
            "conj" -> {
                return if (sign =="-"){
                    "${real.toFloat()}+${imaginary}j"
                }else
                    "${real.toFloat()}${-imaginary.toFloat()},j"
            }
            else -> return "Syntax Error"
        }
    }

    private fun mathematicsPython(userExp: String):String{
        val py:Python= Python.getInstance()
        val pyObj: PyObject =py.getModule("Python file")
        val obj:PyObject=pyObj.callAttr("complex",userExp)
        return obj.toString()
    }
}
