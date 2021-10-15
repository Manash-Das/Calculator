package com.manash.calculationMadeEasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_equation_solver.*
import kotlinx.android.synthetic.main.activity_unit_converter.*

class UnitConverter : AppCompatActivity() {
    var check:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_converter)
        ArrayAdapter.createFromResource(this,R.array.Menu,android.R.layout.simple_list_item_1).also {adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerUnitConverter.adapter=adapter
        }
        spinnerUnitConverter.setSelection(3)
        spinnerUnitConverter.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(check){
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
                        3 -> {
                            val intent = Intent(this@UnitConverter, UnitConverter::class.java)
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
}
