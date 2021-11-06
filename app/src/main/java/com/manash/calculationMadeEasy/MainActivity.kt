package com.manash.calculationMadeEasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ArrayAdapter.createFromResource(this, R.array.Menu, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val intent:Intent
                when (p2) {
                    1 -> {
                        intent = Intent(this@MainActivity, ComplexNumberActivity::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        intent = Intent(this@MainActivity, EquationSolver::class.java)
                        startActivity(intent)
                    }
                    3 -> {
                        intent = Intent(this@MainActivity, UnitConverter::class.java)
                        startActivity(intent)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        zero.setOnClickListener{updateText("0")}
        one.setOnClickListener{updateText("1")}
        two.setOnClickListener{updateText("2")}
        three.setOnClickListener{updateText("3")}
        four.setOnClickListener{updateText("4")}
        five.setOnClickListener{updateText("5")}
        six.setOnClickListener{updateText("6")}
        seven.setOnClickListener{updateText("7")}
        eight.setOnClickListener{updateText("8")}
        nine.setOnClickListener{updateText("9")}

        point.setOnClickListener{updateText(".")}
        open_bracket.setOnClickListener{updateText("(")}
        close_bracket.setOnClickListener{updateText(")")}
        power.setOnClickListener{updateOperator("^")}

        plus.setOnClickListener{updateOperator("+")}
        minus.setOnClickListener{updateOperator("-")}
        multiply.setOnClickListener{updateOperator("*")}
        divide.setOnClickListener{updateOperator("/")}
        clear.setOnClickListener{input_text.setText(""); answerBox.text=""}
        backspace.setOnClickListener{backspaceBTN()}
        equal.setOnClickListener{equalBTN()}
        percentage.setOnClickListener { updateText("%")}
        pie.setOnClickListener { updateText("pi") }
        sine.setOnClickListener {
            if (sine.text=="sin"){
                updateTrig("sin")
            }
            else{
                updateTrig("asin")
            }
        }
        cosine.setOnClickListener {
            if (cosine.text == "cos") {
                updateTrig("cos")
            }
            else {
                updateTrig("acos")
            }
        }
        tangent.setOnClickListener {
            if (tangent.text == getString(R.string.tangent)){
                updateTrig(getString(R.string.tangent))
            }
            else{
                updateTrig("atan")
            }
        }
        inverse.setOnClickListener {inverse()}
        squareRoot.setOnClickListener { updateText("^0.5") }
        square.setOnClickListener { updateText("^2") }
        exponential.setOnClickListener { updateText("e") }
        logarithm.setOnClickListener { updateText("ln(") }
    }

    override fun onStart() {
        super.onStart()
        spinner.setSelection(0)
        input_text.showSoftInputOnFocus = false
    }
    private fun updateText(txtToAdd: String){
        val cursorPos: Int=input_text.selectionStart
        if (input_text.text.length >= 14){
            input_text.textSize = 25F
        }
        if (input_text.text.length <= 14){
            input_text.textSize = 36F
        }

        input_text.setText(input_text.text.insert(cursorPos,txtToAdd).toString())
        input_text.setSelection(cursorPos+txtToAdd.length)
    }
    private fun updateOperator(oprToAdd:String){
        val cursorPos:Int=input_text.selectionStart
        val oldStr:String=input_text.text.toString()
        if (input_text.text.length >= 14){
            input_text.textSize = 25F
        }
        if (input_text.text.length <= 14){
            input_text.textSize = 36F
        }

        Log.d("Cursor position update text",cursorPos.toString())
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
    private fun updateTrig(trigToAdd:String){
        val cursorPos: Int=input_text.selectionStart
        if (input_text.text.length >= 14){
            input_text.textSize = 25F
        }
        if (input_text.text.length <= 14){
            input_text.textSize = 36F
        }

        input_text.setText(input_text.text.insert(cursorPos,trigToAdd.plus("(")).toString())
        input_text.setSelection(cursorPos+trigToAdd.length+1)
    }
    private fun backspaceBTN() {
        val oldStr:String =input_text.text.toString()
        val cursorPos:Int=input_text.selectionStart
        if (input_text.text.length >= 14){
            input_text.textSize = 25F
        }
        if (input_text.text.length <= 14){
            input_text.textSize = 36F
        }

        Log.d("Before Cursor position",cursorPos.toString())
        val leftStr: String= oldStr.subSequence(0,cursorPos).toString()
        val rightStr:String=oldStr.subSequence(cursorPos,oldStr.length).toString()
        if(leftStr.isEmpty()){
            return
        }
        if(leftStr.takeLast(5)=="asin(" || leftStr.takeLast(5)=="acos(" || leftStr.takeLast(5)=="atan("){
            input_text.setText(String.format("%s%s",leftStr.dropLast(5),rightStr))
            input_text.setSelection(cursorPos-5)
            return
        }
        if(leftStr.takeLast(4)=="sin(" || leftStr.takeLast(4)=="cos(" || leftStr.takeLast(4)=="tan("){
            input_text.setText(String.format("%s%s",leftStr.dropLast(4),rightStr))
            input_text.setSelection(cursorPos - 4)
            return
        }
        if(leftStr.takeLast(3)=="ans" || leftStr.takeLast(3)=="ln("){
            input_text.setText(String.format("%s%s",leftStr.dropLast(3),rightStr))
            input_text.setSelection(cursorPos - 3)
            return
        }
        if(leftStr.takeLast(2)=="pi") {
            input_text.setText(String.format("%s%s", leftStr.dropLast(2), rightStr))
            input_text.setSelection(cursorPos - 2)
            return
        }
        if(input_text.text.toString().isNotEmpty()) {
            input_text.setText(String.format("%s%s",leftStr.dropLast(1),rightStr))
            input_text.setSelection(cursorPos - 1)
        }
        else{
            input_text.setSelection(0)
        }
    }
    private fun equalBTN() {
        var userExp:String=input_text.text.toString()
        if(userExp.isEmpty()){ return }
        var result ="0"
        userExp=userExp.replace("ans",result,false)
        val exp=Expression(userExp)
        result=exp.calculate().toString()
        if(result=="NaN"){ answerBox.text = getString(R.string.Error); return}
        if(result=="Infinity"){ answerBox.text = result; return}
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
            result= "$decimal$power"
        }
        answerBox.text = result
    }
    private fun inverse() {
        if (sine.text==getString(R.string.sine)) {
            sine.textSize = 20F
            cosine.textSize= 20F
            tangent.textSize = 20F
            sine.text = getString(R.string.sineInverse)
            cosine.text = getString(R.string.cosInverse)
            tangent.text = getString(R.string.tanInverse)
        }
        else{
            sine.textSize = 30F
            cosine.textSize= 30F
            tangent.textSize = 30F
            sine.text = getString(R.string.sine)
            cosine.text = getString(R.string.cosine)
            tangent.text = getString(R.string.tangent)
        }
    }
}

