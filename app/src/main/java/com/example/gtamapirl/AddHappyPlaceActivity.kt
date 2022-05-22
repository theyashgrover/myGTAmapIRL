package com.example.gtamapirl

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_add_happy_place.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

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
        tv_add_image.setOnClickListener(this)
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
            R.id.tv_add_image -> {
                val pictureDialog = AlertDialog.Builder(this) //building an alert dialog box and calling it's pre-defined functions
                pictureDialog.setTitle("Select Action ") //set It's title to "Select Action"
                val pictureDialogItems = arrayOf("Select Photo from Gallery" , "Capture photo from Camera")
                pictureDialog.setItems(pictureDialogItems){
                    dialog , which ->
                    when(which){
                        0 -> choosePhotoFromGallery()
                        1-> Toast.makeText(this@AddHappyPlaceActivity , "Camera Selection Coming Soon..." , Toast.LENGTH_SHORT).show()
                    }
                }
                pictureDialog.show()
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
    private fun choosePhotoFromGallery(){
        Dexter.withActivity(this).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE ,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report != null) {
                    if(report.areAllPermissionsGranted()){
                        Toast.makeText(this@AddHappyPlaceActivity , "Storage READ/WRITE permissions are granted" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                showRationalDialogForPermissions()
            }
        }).onSameThread().check()
    }

     private fun showRationalDialogForPermissions(){
         AlertDialog.Builder(this).setMessage("It looks like you have turned off permission required for this feature. It can be enabled under Applications Settings.")
             .setPositiveButton("GO TO SETTINGS"){
                 _,_-> //jbb variables sirf pass krne ho and use nh krne ho toh lamda expression underscore se start kro , vrna agrr use krne ho toh lamda expression variable name se start kro ;I
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package" , packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e : ActivityNotFoundException){
                    e.printStackTrace()
                }
             }.setNegativeButton("CANCEL"){
                 dialog , _ ->
                 dialog.dismiss()
             }.show()
     }
}