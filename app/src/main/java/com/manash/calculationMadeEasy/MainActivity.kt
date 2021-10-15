package com.manash.calculationMadeEasy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {
    var check:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input_text.showSoftInputOnFocus=false

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(this, R.array.Menu, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.setSelection(0)

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(check){
                    var intent:Intent
                    Toast.makeText(this@MainActivity, "You selected ${p0?.getItemAtPosition(p2).toString()} $p2", Toast.LENGTH_LONG).show()
                    when (p2) {
                        0->{
                            intent=Intent(this@MainActivity,MainActivity::class.java)
                            startActivity(intent)
                        }
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
                check=true
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }
    @SuppressLint("SetTextI18n")
    private fun updateText(txtToAdd: String){
        val cursorPos: Int=input_text.selectionStart
        input_text.setText(input_text.text.insert(cursorPos,txtToAdd).toString())
        input_text.setSelection(cursorPos+txtToAdd.length)
    }
    private fun updateOperator(oprToAdd:String){
        val cursorPos:Int=input_text.selectionStart
        val oldStr:String=input_text.text.toString()
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
        input_text.setText(input_text.text.insert(cursorPos,trigToAdd.plus("(")).toString())
        input_text.setSelection(cursorPos+trigToAdd.length+1)
    }
    fun zeroBTN(view: android.view. View) {
        updateText("0")
    }
    fun oneBTN(view: android.view. View) {
        updateText("1")
    }
    fun twoBTN(view: android.view. View) {
        updateText("2")
    }
    fun threeBTN(view: android.view. View) {
        updateText("3")
    }
    fun fourBTN(view: android.view. View) {
        updateText("4")
    }
    fun fiveBTN(view: android.view. View) {
        updateText("5")
    }
    fun sixBTN(view: android.view. View) {
        updateText("6")
    }
    fun sevenBTN(view: android.view. View) {
        updateText("7")
    }
    fun eightBTN(view: android.view. View) {
        updateText("8")
    }
    fun nineBTN(view: android.view. View) {
        updateText("9")
    }
    fun pointBTN(view: android.view. View) {
        updateText(".")
    }
    fun openBTN(view: android.view. View) {
        updateText("(")
    }
    fun closeBTN(view: android.view. View) {
        updateText(")")
    }
    fun powerBTN(view: android.view. View) {
        updateOperator("^")
    }
    fun plusBTN(view: android.view. View) {
        updateOperator("+")
    }
    fun minusBTN(view: android.view. View) {
        updateOperator("-")
    }
    fun multiplyBTN(view: android.view. View) {
        updateOperator("*")
    }
    fun divideBTN(view: android.view. View) {
        updateOperator("/")
    }
    fun clrBTN(view: android.view. View) {
        input_text.setText(""); answer_box.setText("")
    }
    fun backspaceBTN(view: android.view. View) {
        val oldStr:String =input_text.text.toString()
        val cursorPos:Int=input_text.selectionStart
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
        if(leftStr.takeLast(3)=="ans"){
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
    fun equalBTN(view: android.view. View) {
        var userExp:String=input_text.text.toString()
        if(userExp.isEmpty()){ return }
        var result ="0"
        userExp=userExp.replace("ans",result,false)
        val exp=Expression(userExp)
        result=exp.calculate().toString()
        if(result=="NaN"){ answer_box.setText(getString(R.string.Error)); return}
        if(result=="Infinity"){ answer_box.setText(result); return}
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
        answer_box.setText(result)
    }
    fun percent(view: android.view. View) {
        updateText("%")
    }
    fun mode(view: android.view.View) {
        val intent=Intent(this,ComplexNumberActivity::class.java)
        startActivity(intent)
    }
    fun pie(view: android.view.View) {
        updateText("pi")
    }
    fun ans(view: android.view.View) {
        Toast.makeText(applicationContext, "under working", Toast.LENGTH_LONG).show()
        updateText("")
    }
    fun sin(view: android.view.View) {
        updateTrig("sin")
    }
    fun cos(view: android.view.View) {
        updateTrig("cos")
    }
    fun tan(view: android.view.View){
        updateTrig("tan")
    }
    fun sinInverse(view: android.view.View) {
        updateTrig("asin(")
        inverse(view)
    }
    fun cosInverse(view: android.view.View) {
        updateTrig("acos(")
        inverse(view)
    }
    fun tanInverse(view: android.view.View) {
        updateText(("atan("))
        inverse(view)
    }
    fun inverse(view: android.view.View) {
        if(sine.visibility==View.VISIBLE) {
            sine.visibility = View.GONE
            cosine.visibility = View.GONE
            tangent.visibility = View.GONE
            sineInverse.visibility=View.VISIBLE
            cosineInverse.visibility=View.VISIBLE
            tangentInverse.visibility=View.VISIBLE

        }
        else{
            sine.visibility=View.VISIBLE
            cosine.visibility = View.VISIBLE
            tangent.visibility = View.VISIBLE
            sineInverse.visibility=View.GONE
            cosineInverse.visibility=View.GONE
            tangentInverse.visibility=View.GONE
        }
    }
}

