package com.example.gtamapirl

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_happy_place.*
import java.text.SimpleDateFormat
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

        //for implementing the Add Date functionality (ye basically btata hai mujhe kya kya chahiye date mein se and kha kha store krna h sbb):
        dateSetListener = DatePickerDialog.OnDateSetListener{
            view , year , month , dayOfMonth ->
            cal.set(Calendar.YEAR , year)
            cal.set(Calendar.MONTH , month)
            cal.set(Calendar.DAY_OF_MONTH , dayOfMonth)
            updateDateInView() //sb hone ke baad ye vale function ko call krna necessary hai in the onCreate ;I
        }
        et_date.setOnClickListener(this)
    }
     //for implementing the Add Date functionality and all the onClickListeners we might need:
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.et_date ->{
                //open the DatePickerDialog to get details (ye unn saari cheezo ko jo btayi gyi h , date picker kholke , input dilvake , store krvata hai ):
                DatePickerDialog(
                    this@AddHappyPlaceActivity ,
                    dateSetListener ,
                    cal.get(Calendar.YEAR) , cal.get(Calendar.MONTH) , cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }
     //if we exclude this function , then the rest of the code only adds the functionality to show a date picker and store the input by a user :
     //this function on the other hand , displays the date in the textview in a specified date format.
    private fun updateDateInView(){
         val myFormat = "dd.MM.yy"
         val sdf = SimpleDateFormat(myFormat , Locale.getDefault()) //translating my date format as per the setting of my phone ;I
         //setting the et_date view to the date selected by the user :
         et_date.setText(sdf.format(cal.time).toString())
    }
}