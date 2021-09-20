package com.manash.calculationMadeEasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.mariuszgromada.math.mxparser.Expression

class ComplexNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.complex_number)
        input_text.showSoftInputOnFocus=false
    }
    private fun updateText(txtToAdd: String){
        val cursorPos: Int=input_text.selectionStart                            //cursor position
        input_text.setText(input_text.text.insert(cursorPos,txtToAdd).toString())
        input_text.setSelection(cursorPos+txtToAdd.length)
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
        if(leftStr.takeLast(4)=="abs(" || leftStr.takeLast(4)=="arg(" || leftStr.takeLast(4)=="pol("){
            input_text.setText(String.format("%s%s",leftStr.dropLast(4),rightStr))
            return
        }
        if(leftStr.takeLast(5)=="conj(" || leftStr.takeLast(5)=="rect(") {
            input_text.setText(String.format("%s%s", leftStr.dropLast(5), rightStr))
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
        var userExp:String=input_text.text.toString()
        if(userExp.isEmpty()){ return }
        if(userExp.length>4){
            var part:String= userExp.substring(0,3)
            if(part=="pol" || part=="abs" || part=="arg" ){
                userExp=userExp.substring(4).dropLast(1)
                Log.d("equal_function :","if is working")
                answer_box.setText(function1(part,userExp))
                return
            }
            part=userExp.substring(0,4)
            Log.d("equal_function part :",part)
            if(part=="rect"){
                userExp=userExp.substring(5).dropLast(1)
                answer_box.setText(function2(userExp))
                return
            }
            if(part=="conj"){
                userExp=userExp.substring(5).dropLast(1)
                answer_box.setText(function1(part,userExp))
                return
            }
        }
        Log.d("equal_function userExp :",userExp)
        answer_box.setText(mathematics(userExp))

    }
    private fun function2(userExp:String):String{
        var r=""
        var angle=""
        var flag=0
        Log.d("function1 userExp",userExp)
        for(alpha in userExp){
            val character:String=alpha.toString()
            if(character==","){
                flag=1
                continue
            }
            if(flag==0){
                Log.d("alpha :", character)
                r="$r$character"
                Log.d("alpha",r)
            }
            else{
                Log.d("alpha :", character)
                angle="$angle$character"
                Log.d("alpha",angle)
            }
        }
        Log.d("final check a",r)
        Log.d("final check b",angle)

        val x=mathematics("$r*cos($angle)")
        val y= mathematics("$r*sin($angle)")
        return "$x+($y)j"
    }
    fun imaginary(@Suppress("UNUSED_PARAMETER")view: View) { updateText("j") }
    fun polar(@Suppress("UNUSED_PARAMETER")view: View) { updateText("pol(") }
    fun rectangular(@Suppress("UNUSED_PARAMETER")view: View) { updateText("rect(") }
    fun absolute(@Suppress("UNUSED_PARAMETER")view: View) { updateText("abs(") }
    fun argument(@Suppress("UNUSED_PARAMETER")view: View) { updateText("arg(") }
    fun conjugate(@Suppress("UNUSED_PARAMETER")view: View) { updateText("conj(") }
    fun mode(@Suppress("UNUSED_PARAMETER")view: View) {
        input_text.setText("")
        intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun function1(sub:String, userExp:String):String{
        var a=""
        var b=""
        var flag=0
        Log.d("function1 userExp",userExp)
        Log.d("function1 Exp",userExp)
        for(alpha in userExp){
            val character:String=alpha.toString()
            if(character=="+" || character=="-" || character=="j"){
                if(character=="-"){
                    flag=2
                    continue
                }
                if(character=="j"){
                    continue
                }
                flag=1
                continue
            }
            if(flag==0){
                Log.d("alpha :", character)
                a="$a$character"
                Log.d("alpha",a)
            }
            else{
                Log.d("alpha :", character)
                b="$b$character"
                Log.d("alpha",b)
            }
        }
        Log.d("final check a",a)
        Log.d("final check b",b)
        var x: String
        if(sub=="pol"){
            x=mathematics("atg($b/$a)")
            Log.d("arg = ",x)
            val y:String=mathematics("sqrt($a*$a+$b*$b)")
            Log.d("abs",y)
            x="($y,$x)"
        }
        else if(sub=="arg"){
            x=mathematics("atan($b/$a)")
        }
        else if(sub=="abs"){
            x=mathematics("sqrt($a*$a+$b*$b)")
        }
        else if(sub=="conj"){
            Log.d("flag :", flag.toString())
            if(flag==1){
                x="-$b"
            }
            else{
                x="+$b"
            }
            x="$a$x'j'"
        }
        else{
            x="syntax Error"
        }
        return x

    }
    private fun mathematics(userExpression: String): String {
        val exp = Expression(userExpression)
        val result= exp.calculate().toString()
        Log.d("result",result)
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
    fun comma(@Suppress("UNUSED_PARAMETER")view: View) { updateText(",") }


}