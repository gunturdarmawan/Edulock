package com.example.edulock.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.example.edulock.R
import com.example.edulock.firebaseauth.Login

class MainActivity : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var indicatorsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)

        if (restorePrefData()) {
            val intent = Intent(this, Login:: class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isIntroOpened", true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        return pref.getBoolean("isIntroOpened", false)
    }


    private fun setOnboardingItems() {
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.phone,
                    title = "Kontrol Penuh Orangtua",
                    description = "Mengunci Aplikasi yang sering anak buka, agar terhindar dari kecanduan bermain gadget"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.learn,
                    title = "Tingkatkan Kemauan Belajar Anak",
                    description = "Dengan anak berhasil menjawab soal diharapkan menumbuhkan kemauan untuk belajar"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.noworry,
                    title = "Bebas gerak & bebas khawatir",
                    description = "Tidak perlu khawatir anak akan kecanduan bermain hp, karena anak tetap dapat belajar"
                )
            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
            findViewById<MaterialButton>(R.id.buttonNext).setOnClickListener{
            if (onboardingViewPager.currentItem + 1 < onboardingItemsAdapter.itemCount ) {
                onboardingViewPager.currentItem += 1
            } else {
                val intent = Intent(this, Login:: class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                savePrefsData()
            }
        }

        findViewById<TextView>(R.id.textSkip).setOnClickListener {
            onboardingViewPager.currentItem = 2
        }
    }

    private fun setupIndicators(){
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }


    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if(i ==position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
        if (position == onboardingItemsAdapter.itemCount - 1){
            "Get Started".also { findViewById<MaterialButton>(R.id.buttonNext).text = it }
            findViewById<TextView>(R.id.textSkip).visibility = View.INVISIBLE
        } else {
            findViewById<TextView>(R.id.textSkip).visibility = View.VISIBLE
            "Next".also { findViewById<MaterialButton>(R.id.buttonNext).text = it }
        }
    }

}