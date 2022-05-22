package com.example.gtamapirl

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_happy_place.*
import java.util.*

class AddHappyPlaceActivity : AppCompatActivity() , View.OnClickListener //for implementing the Add Date functionality , we inherit our activity with View.OnClickListener and implement it's member :
 {

    //for implementing the Add Date functionality :
    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener : DatePickerDialog.OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_happy_place)
        setSupportActionBar(toolbar_add_place)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_add_place.setNavigationOnClickListener { onBackPressed() }

        //for implementing the Add Date functionality :
        dateSetListener = DatePickerDialog.OnDateSetListener{
            view , year , month , dayOfMonth ->
            cal.set(Calendar.YEAR , year)
            cal.set(Calendar.MONTH , month)
            cal.set(Calendar.DAY_OF_MONTH , dayOfMonth)
        }

    }
     //for implementing the Add Date functionality and all the onClickListeners we might need:
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.et_date ->{
                //open the DatePickerDialog to get details :
                DatePickerDialog(
                    this@AddHappyPlaceActivity ,
                    dateSetListener ,
                    cal.get(Calendar.YEAR) , cal.get(Calendar.MONTH) , cal.get(Calendar.DAY_OF_MONTH)
                )
            }
        }
    }

}