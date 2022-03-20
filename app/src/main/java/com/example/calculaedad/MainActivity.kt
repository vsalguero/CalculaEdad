package com.example.calculaedad
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yodo1.mas.Yodo1Mas
import com.yodo1.mas.banner.Yodo1MasBannerAdListener
import com.yodo1.mas.error.Yodo1MasError
import com.yodo1.mas.helper.model.Yodo1MasAdBuildConfig
import com.yodo1.mas.banner.Yodo1MasBannerAdView;

class MainActivity : AppCompatActivity() {

    lateinit var bannerAdView : Yodo1MasBannerAdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        var etDate = findViewById(R.id.etDate) as EditText
        etDate.setOnClickListener { showDatePickerDialog() }
        var link = findViewById(R.id.link) as TextView
        link.setOnClickListener{ openLink() }

        //Yodo1
        val config = Yodo1MasAdBuildConfig.Builder().enableUserPrivacyDialog(true).privacyPolicyUrl("Your privacy policy url").build()
        Yodo1Mas.getInstance().setAdBuildConfig(config)

        Yodo1Mas.getInstance().init(this, "2UonxT9JwP", object : Yodo1Mas.InitListener {
            override fun onMasInitSuccessful() {
                Toast.makeText(this@MainActivity, "[Yodo1 Mas] Successful initialization", Toast.LENGTH_SHORT).show()
            }
            override fun onMasInitFailed(error: Yodo1MasError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        bannerAdView = findViewById(R.id.yodo1_mas_banner)
        bannerAdView.setAdListener(object : Yodo1MasBannerAdListener {
            override fun onBannerAdLoaded(bannerAdView: Yodo1MasBannerAdView?) {
                // Code to be executed when an ad finishes loading.
            }

            override fun onBannerAdFailedToLoad(
                bannerAdView: Yodo1MasBannerAdView?,
                error: Yodo1MasError
            ) {
                // Code to be executed when an ad request fails.
            }

            override fun onBannerAdOpened(bannerAdView: Yodo1MasBannerAdView?) {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onBannerAdFailedToOpen(
                bannerAdView: Yodo1MasBannerAdView?,
                error: Yodo1MasError
            ) {
                // Code to be executed when an ad open fails.
            }

            override fun onBannerAdClosed(bannerAdView: Yodo1MasBannerAdView?) {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.

            }

        })
        bannerAdView.loadAd()
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        var etDate = findViewById(R.id.etDate) as EditText;
        etDate.setText("$day/$month/$year");
        getAge(year,month,day)
    }

    private fun getAge(year: Int, month: Int, day: Int) {
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
    }

    private fun openLink(){
        val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vladi.codes/2022/03/calcula-edad-android-kotlin-app.html"))
        startActivity(i)
    }
}