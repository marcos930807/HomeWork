package cu.marcos930807.robotrevo.ui.activities

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.model.Notice
import cu.marcos930807.robotrevo.db.model.clear
import cu.marcos930807.robotrevo.db.sql.NoticeDBHelper
import io.multimoon.colorful.CAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.insertcontact.view.*
import kotlinx.android.synthetic.main.insertimg.view.*
import kotlinx.android.synthetic.main.insertnotice.view.*
import org.jetbrains.anko.act
import org.jetbrains.anko.toast

class DetailActivity : CAppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    var noticetoedit: Notice? = null

    var isEditmode = false
    var id: Long? = null
    lateinit var noticeDBHelper: NoticeDBHelper


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                container.setCurrentItem(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                container.setCurrentItem(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                container.setCurrentItem(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        noticeDBHelper = NoticeDBHelper(this)

        InsertNotice.newInstance().clearinfo()
        InsertContact.newInstance().clearinfo()
        InsertImg.newInstance().clearinfo()
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        fabinsert.setOnClickListener { view ->

            val category = InsertNotice.newInstance().category
            val title = InsertNotice.newInstance().title
            val body = InsertNotice.newInstance().body
            val price = InsertNotice.newInstance().price


            val name = InsertContact.newInstance().cname
            val mail = InsertContact.newInstance().cmail
            val phone = InsertContact.newInstance().cphone


            val photoa = InsertImg.newInstance().phata
            val photob = InsertImg.newInstance().phatb
            val photoc = InsertImg.newInstance().phatc


            val notice = Notice(id,
                    category,
                    title,
                    body,
                    price,
                    mail,
                    name,
                    phone,
                    photoa, photob, photoc,null)

                val result = noticeDBHelper.insertNotice(notice)
                if (result) {
                    toast("Insertado correctamente")

            }
            InsertNotice.newInstance().clearinfo()
            InsertContact.newInstance().clearinfo()
            InsertImg.newInstance().clearinfo()
            NoticeListFragment.newInstance("").refreshadapter()
            act.finish()
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        var prevMenuItem = navigation.getMenu().getItem(0)

        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false)
                } else {
                    navigation.getMenu().getItem(0).setChecked(false)
                }

                navigation.getMenu().getItem(position).setChecked(true)
                prevMenuItem = navigation.getMenu().getItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            when (position) {
                0 -> {

                    return InsertNotice.newInstance()
                }
                1 -> {
                    return InsertContact.newInstance()
                }
                2 -> {

                    return InsertImg.newInstance()
                }
            }
            return InsertNotice.newInstance()
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }


}


