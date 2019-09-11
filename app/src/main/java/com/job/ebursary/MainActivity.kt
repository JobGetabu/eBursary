package com.job.ebursary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.job.ebursary.commoners.AdapterImageSlider
import com.job.ebursary.commoners.Tools
import com.job.ebursary.model.Image
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_drawer_content.*


class MainActivity : AppCompatActivity() {

    private lateinit var runnable: Runnable
    private lateinit var viewPager: ViewPager
    private lateinit var adapterImageSlider: AdapterImageSlider

    private var handler = Handler()

    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java)
    }


    private val array_image_place = intArrayOf(
        R.drawable.b1,
        R.drawable.b2,
        R.drawable.b3,
        R.drawable.b4,
        R.drawable.b5
    )

    private val array_title_place = arrayOf(
        "Nandi Governor Stephen Sang giving out County bursary",
        "It Is Early Christmas For Students As County Issues BursariesKwale County",
        "Global Hope Foundation Programme gives bursary ",
        "Embu: County disburses Sh163million bursary\n",
        "Nairobi county executive is yet again on the spot over bursaries"
    )

    /*private val array_details = arrayOf(
        getString(R.string.b1),
        getString(R.string.b2),
        getString(R.string.b3),
        getString(R.string.b4),
        getString(R.string.b5)
    )*/

    private fun getDetails(i: Int): String{
        return when(i){
            0 -> getString(R.string.b1)
            1 -> getString(R.string.b2)
            2 -> getString(R.string.b3)
            3 -> getString(R.string.b4)
            4 -> getString(R.string.b5)
            else -> " "
        }
    }

    private val array_brief_place =
        arrayOf("Nandi Hill", "Kwale County", "River Forest", "Embu County", "Nairobi County")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initNavigationMenu()
        initComponent()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
        actionBar.title = "eBursary"
        Tools.setSystemBarColor(this)
    }

    private fun initNavigationMenu() {

        val toggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener { item ->
            Toast.makeText(applicationContext, "${item.title} Selected", Toast.LENGTH_SHORT).show()
            actionBar?.title = item.title
            drawer_layout.closeDrawers()
            true
        }

        // open drawer at start
        //drawer_layout.openDrawer(GravityCompat.START)

    }

    override fun onDestroy() {
        if (runnable != null) handler.removeCallbacks(runnable)
        super.onDestroy()
    }

    private fun initComponent() {
        layout_dots as LinearLayout
        viewPager = pager as ViewPager
        adapterImageSlider = AdapterImageSlider(this, ArrayList<Image>())

        val items = mutableListOf<Image>()
        for (i in 0 until array_image_place.size) {
            val obj = Image()
            obj.image = array_image_place[i]
            obj.imageDrw = resources.getDrawable(obj.image)
            obj.name = array_title_place[i]
            obj.brief = array_brief_place[i]
            obj.des = getDetails(i)
            items.add(obj)
        }

        adapterImageSlider.setItems(items)
        viewPager.adapter = adapterImageSlider

        // displaying selected image first
        viewPager.currentItem = 0
        addBottomDots(layout_dots, adapterImageSlider.count, 0)
        (findViewById(R.id.title) as TextView).setText(items[0].name)
        (findViewById(R.id.brief) as TextView).setText(items[0].brief)
        (findViewById(R.id.description) as TextView).setText(items[0].des)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(pos: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(pos: Int) {
                (findViewById(R.id.title) as TextView).setText(items.get(pos).name)
                (findViewById(R.id.brief) as TextView).setText(items.get(pos).brief)
                (findViewById(R.id.description) as TextView).setText(items.get(pos).des)
                addBottomDots(layout_dots, adapterImageSlider.count, pos)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        startAutoSlider(adapterImageSlider.count)
    }

    private fun addBottomDots(layout_dots: LinearLayout, size: Int, current: Int) {
        val dots = arrayOfNulls<ImageView>(size)

        layout_dots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            val width_height = 15
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams(width_height, width_height))
            params.setMargins(10, 10, 10, 10)
            dots[i]?.layoutParams = params
            dots[i]?.setImageResource(R.drawable.shape_circle_outline)
            layout_dots.addView(dots[i])
        }

        if (dots.size > 0) {
            dots[current]?.setImageResource(R.drawable.shape_circle)
        }
    }

    private fun startAutoSlider(count: Int) {
        runnable = Runnable {
            var pos = viewPager.currentItem
            pos += 1
            if (pos >= count) pos = 0
            viewPager.currentItem = pos
            handler.postDelayed(runnable, 3000)
        }
        handler.postDelayed(runnable, 3000)
    }

}
