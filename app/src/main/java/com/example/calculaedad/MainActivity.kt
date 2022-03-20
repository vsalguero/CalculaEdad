package com.example.calculaedad
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        var etDate = findViewById(R.id.etDate) as EditText
        etDate.setOnClickListener { showDatePickerDialog() }
        var link = findViewById(R.id.link) as TextView
        link.setOnClickListener{ openLink() }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        //etDate = findViewById<EditText>(R.id.etDate)
        var etDate = findViewById(R.id.etDate) as EditText;
        etDate.setText("$day/$month/$year");
        getAge(year,month,day)

    }

    private fun getAge(year: Int, month: Int, day: Int): String? {
        val dob: Calendar = Calendar.getInstance()
        val today: Calendar = Calendar.getInstance()
        dob.set(year, month, day)
        var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        val ageInt = age

        var ageText = findViewById(R.id.age) as TextView;
        ageText.setText("Edad: " + ageInt.toString() + " Años");
        return ageInt.toString()
    }

    private fun openLink(){
        val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vladi.codes"))
        startActivity(i)
    }
}