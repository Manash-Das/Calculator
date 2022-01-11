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
import com.chaquo.python.android.AndroidPlatform
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_unit_converter.*
import org.mariuszgromada.math.mxparser.Expression

class UnitConverter : AppCompatActivity() {
    var checkOption:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_converter)
        inputLabel.showSoftInputOnFocus=false
        secondLabel.showSoftInputOnFocus=false
        ArrayAdapter.createFromResource(this,R.array.Menu,android.R.layout.simple_list_item_1).also {adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerUnitConverter.adapter=adapter
        }
        spinnerUnitConverter.setSelection(3)
        spinnerUnitConverter.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0->{
                        val intent= Intent(this@UnitConverter,MainActivity::class.java)
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(this@UnitConverter, ComplexNumberActivity::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this@UnitConverter, EquationSolver::class.java)
                        startActivity(intent)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        UnitConverterOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                clear()
                if(checkOption) {
                    when (p2) {
                        0 -> {
                            val optionAdapter = ArrayAdapter.createFromResource(
                                this@UnitConverter,
                                R.array.lengthOption,
                                android.R.layout.simple_list_item_1
                            )
                            optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            firstUnit.adapter = optionAdapter
                            secondUnit.adapter = optionAdapter
                        }
                        1 -> {
                            val optionAdapter = ArrayAdapter.createFromResource(
                                this@UnitConverter,
                                R.array.Area,
                                android.R.layout.simple_list_item_1
                            )
                            optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            firstUnit.adapter = optionAdapter
                            secondUnit.adapter = optionAdapter
                        }
                        2 -> {
                            val optionAdapter = ArrayAdapter.createFromResource(
                                this@UnitConverter,
                                R.array.numberSystemConverter,
                                android.R.layout.simple_list_item_1
                            )
                            optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            firstUnit.adapter = optionAdapter
                            secondUnit.adapter = optionAdapter
                        }
                        3 -> {
                            val optionAdapter = ArrayAdapter.createFromResource(
                                this@UnitConverter,
                                R.array.temperatureOption,
                                android.R.layout.simple_list_item_1
                            )
                            optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            firstUnit.adapter = optionAdapter
                            secondUnit.adapter = optionAdapter
                        }
                        4 -> {
                            val optionAdapter = ArrayAdapter.createFromResource(
                                this@UnitConverter,
                                R.array.scientificOption,
                                android.R.layout.simple_list_item_1
                            )
                            optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            firstUnit.adapter = optionAdapter
                            secondUnit.adapter = optionAdapter
                        }
                        5 -> {
                            val optionAdapter = ArrayAdapter.createFromResource(
                                this@UnitConverter,
                                R.array.Time,
                                android.R.layout.simple_list_item_1
                            )
                            optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            firstUnit.adapter = optionAdapter
                            secondUnit.adapter = optionAdapter
                        }
                        6 -> {
                            val optionAdapter = ArrayAdapter.createFromResource(
                                this@UnitConverter,
                                R.array.weight,
                                android.R.layout.simple_list_item_1
                            )
                            optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            firstUnit.adapter = optionAdapter
                            secondUnit.adapter = optionAdapter
                        }
                        7 -> {
                            val optionAdapter = ArrayAdapter.createFromResource(
                                this@UnitConverter,
                                R.array.Data,
                                android.R.layout.simple_list_item_1
                            )
                            optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            firstUnit.adapter = optionAdapter
                            secondUnit.adapter = optionAdapter
                        }
                        8 -> {
                            val optionAdapter = ArrayAdapter.createFromResource(
                                this@UnitConverter,
                                R.array.Angle,
                                android.R.layout.simple_list_item_1
                            )
                            optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            firstUnit.adapter = optionAdapter
                            secondUnit.adapter = optionAdapter
                        }
                    }
                }
                checkOption=true

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        firstUnit.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                calculation()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        secondUnit.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                calculation()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        oneUnitConverter.setOnClickListener { updateText("1") }
        twoUnitConverter.setOnClickListener { updateText("2")}
        threeUnitConverter.setOnClickListener{ updateText("3") }
        fourUnitConverter.setOnClickListener { updateText("4") }
        fiveUnitConverter.setOnClickListener { updateText("5") }
        sixUnitConverter.setOnClickListener{ updateText("6") }
        sevenUnitConverter.setOnClickListener { updateText("7") }
        eightUnitConverter.setOnClickListener{ updateText("8") }
        nineUnitConverter.setOnClickListener{ updateText("9")}
        zeroUnitConverter.setOnClickListener{updateText("0")}
        pointUnitConverter.setOnClickListener {updateText(".") }
        backspaceUnitConverter.setOnClickListener{
            val oldStr:String =inputLabel.text.toString()
            val cursorPos:Int=inputLabel.selectionStart
            if(cursorPos==0){
                return@setOnClickListener
            }
            val leftStr: String= oldStr.subSequence(0,cursorPos).toString()
            val rightStr:String=oldStr.subSequence(cursorPos,oldStr.length).toString()
            if(inputLabel.text.toString().isNotEmpty()) {
                inputLabel.setText(String.format("%s%s",leftStr.dropLast(1),rightStr))
                inputLabel.setSelection(cursorPos - 1)
            }
            else{
                inputLabel.setSelection(0)
            }
            calculation()
        }
    }
    override fun onStart() {
        super.onStart()
        spinnerUnitConverter.setSelection(3)
    }
    private fun updateText(txtToAdd: String){
        val cursorPos: Int=inputLabel.selectionStart
        inputLabel.setText(inputLabel.text.insert(cursorPos,txtToAdd).toString())
        inputLabel.setSelection(cursorPos+txtToAdd.length)
        calculation()

    }
    private fun mathematics(): String {
        val number: String = inputLabel.text.toString()
        if (number == "") {
            return ""
        }
        val unitPresent=firstUnit.selectedItem
        val unitToConvert=secondUnit.selectedItem
        val userExpression="$number*[$unitPresent]/[$unitToConvert]"
        val exp = Expression(userExpression)
        val result= exp.calculate().toString()
        if(result=="NaN"){
            return "Error"
        }
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
    private fun pythonModule():String {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
        val py: Python = Python.getInstance()
        val pyObj: PyObject = py.getModule("Python file")

        val number: String = inputLabel.text.toString()
        if (number == "") {
            return ""
        }

        val unitPresent=convertingToSpecific(firstUnit.selectedItem.toString())
        val unitToConvert=convertingToSpecific(secondUnit.selectedItem.toString())
        val obj:PyObject=pyObj.callAttr("numberSystem",unitPresent,number,unitToConvert)
        return obj.toString()
    }
    private fun convertingToSpecific(Data:String):String{
        when(Data){
            "Binary"->return "2"
            "Decimal"->return "10"
            "Octal"->return "8"
            "Hexadecimal"->return "16"
        }
        return ""
    }
    private fun clear() {
        inputLabel.setText("")
        secondLabel.setText("")
    }
    private fun calculation(){
        when (UnitConverterOption.selectedItemId.toInt()){
            2->secondLabel.setText(pythonModule())
            3->secondLabel.setText(temperature())
            else->secondLabel.setText(mathematics())
        }
    }
    private fun temperature():String{
        val inputNumber= inputLabel.text.toString()
        if(inputNumber=="") {
            return ""
        }
        val number=inputNumber.toDouble()
        when(firstUnit.selectedItemId.toInt()){
            0->when(secondUnit.selectedItemId.toInt()){
                0->return number.toString()//fahrenheit to fahrenheit
                1->return ((number-32.0)*(5.0/9.0)).toString() //fahrenheit to celsius
                2->return ((number-32.0)*(5.0/9.0)+273.15).toString() // fahrenheit to kelvin
            }
            1->when(secondUnit.selectedItemId.toInt()){
                0->return (number*(9.0/5.0)+32.0).toString()
                1->return number.toString()
                2->return (number+273.15).toString()
            }
            2->when(secondUnit.selectedItemId.toInt()){
                0->return ((number-273.15)*(9.0/5.0)+32.0).toString()
                1->return (number-273.15).toString()
                2->return number.toString()
            }
        }
        return ""
    }
}

