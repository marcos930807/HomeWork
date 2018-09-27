package cu.marcos930807.robotrevo.ui.activities

import android.os.Bundle
import android.content.Intent
import android.view.*
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.NetworkUtils
import com.squareup.picasso.Picasso


import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.model.Notice
import cu.marcos930807.robotrevo.ui.utils.getCurrentTime
import io.multimoon.colorful.CAppCompatActivity


import kotlinx.android.synthetic.main.activity_notice_upload.*


import kotlinx.android.synthetic.main.app_bar_detail.*
import org.jetbrains.anko.act

import org.jetbrains.anko.doAsync

import org.jetbrains.anko.toast

import org.jsoup.Jsoup

import java.io.File
import java.io.FileInputStream


class UpladActivity : CAppCompatActivity() {
    var element_captcha_value = ""
    var cok = mutableMapOf<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_upload)
        setSupportActionBar(detailtoolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        imcaptcha.scaleX = 3.0f
        imcaptcha.scaleY = 3.0f

        //Verificar si hay red
        var isnetworkavailable = false
        if (NetworkUtils.isWifiConnected()) {

            toast("Esta conectado a una wifi")
            isnetworkavailable = true
        } else if (NetworkUtils.isMobileData()) {
            isnetworkavailable = true
            toast("Esta conectado por datos moviles")

        } else {
            toast("No network")
            etcaptchaanswer.visibility = View.GONE
            if (DeviceUtils.getSDKVersionCode() >= 19) {
                nonetwork.enableMergePathsForKitKatAndAbove(true)
                nonetwork.visibility = View.VISIBLE
            }

        }

        if (isnetworkavailable) {

            jsoupMethod()
        }


    }

    private fun jsoupMethod() {


        progress.visibility = View.VISIBLE



        doAsync {
            val con = Jsoup.connect("https://www.revolico.com/insertar-anuncio.html")

            val doc = con.timeout(20 * 1000).get()
            cok = con.response().cookies()

            //Leyendo la Info del captcha
            val element_captcha = doc.getElementById("captchaId")
            element_captcha_value = element_captcha.attr("value")

            runOnUiThread {
                imcaptcha.visibility = View.VISIBLE
                Picasso.with(act).load("https://www.revolico.com/securimage/show.php?id=${element_captcha_value}").into(imcaptcha)
                KeyboardUtils.showSoftInput(etcaptchaanswer)
                etcaptchaanswer.setOnKeyListener { view,
                                                   keyCode,
                                                   keyEvent ->

                    PressEnter(keyCode, keyEvent)

                }



                progress.visibility = View.GONE
            }

        }

        buttonup.setOnClickListener({
            UploadNotice()

        })

    }

    private fun UploadNotice() {
        progress.visibility = View.VISIBLE
        doAsync {

            val captchavalue = etcaptchaanswer.text.toString()

            val postdoc = Jsoup.connect("https://www.revolico.com/insertar-anuncio.html")

            if (!uploadnotice!!.price.isEmpty()) {
                postdoc.data("ad_price", uploadnotice?.price)
            }
            //Campo obligatorio no validar
            postdoc.data("ad_headline", uploadnotice?.title)

            postdoc.data("ad_text", uploadnotice?.body + "   SUBIDO: " + getCurrentTime())
            postdoc.data("email", uploadnotice?.mail)
            if (!uploadnotice!!.ownername.isEmpty()) {
                postdoc.data("name", uploadnotice?.ownername)
            }
            if (!uploadnotice!!.number.isEmpty()) {
                postdoc.data("phone", uploadnotice?.number)

            }


            if (!uploadnotice!!.photoa.isEmpty()) {
                postdoc.data("MAX_FILE_SIZE", "307200")
                postdoc.data("ad_picture_a", uploadnotice?.photoa, FileInputStream(File(uploadnotice?.photoa)))
            }
            if (!uploadnotice!!.photob.isEmpty()) {
                postdoc.data("MAX_FILE_SIZE", "307200")
                postdoc.data("ad_picture_b", uploadnotice?.photob, FileInputStream(File(uploadnotice?.photob)))
            }
            if (!uploadnotice!!.photoc.isEmpty()) {
                postdoc.data("MAX_FILE_SIZE", "307200")
                postdoc.data("ad_picture_c", uploadnotice?.photoc, FileInputStream(File(uploadnotice?.photoc)))
            }
            postdoc.data("category", uploadnotice?.category)
            postdoc.data("captchaId", element_captcha_value)
            postdoc.data("captcha_code", captchavalue)
            postdoc.timeout(30 * 10000)

            val completedoc = postdoc.cookies(cok).post()


//        Codigo quefunciona solo para fotos el codigo de arriba es mas elaborado
//                val postdoc = Jsoup.connect("https://www.revolico.com/compra-venta/celulares-lineas-accesorios/insertar-anuncio.html")
//                        .data("ad_price", uploadnotice?.price)
//                        .data("ad_headline", uploadnotice?.title)
//                        .data("ad_text", uploadnotice?.body + "   SUBIDO: " + getCurrentTime())
//                        .data("email", uploadnotice?.mail)
//                        .data("name", uploadnotice?.ownername)
//                        .data("phone", uploadnotice?.number)
//                        .data("category","31")
//                        .data("MAX_FILE_SIZE", "307200")
//                        .data("ad_picture_a", uploadnotice?.photoa, FileInputStream(File(uploadnotice?.photoa)))
//                        .data("MAX_FILE_SIZE", "307200")
//                        .data("ad_picture_b", uploadnotice?.photob, FileInputStream(File(uploadnotice?.photob)))
//                        .data("MAX_FILE_SIZE", "307200")
//                        .data("ad_picture_c", uploadnotice?.photoc, FileInputStream(File(uploadnotice?.photoc)))
//                        .data("captchaId", element_captcha_value)
//                        .data("captcha_code", captchavalue)
//                        .cookies(cok).post()


//                val finish = postdoc.title()

            val finish = completedoc.location()


            runOnUiThread {
                progress.visibility = View.GONE
                val id = finish.split("=")
                if (id.size > 1)
                    toast("Insertado correctamente con el id ${id[1]}")
                else
                    toast("Error en el captcha")
                finish()
            }


        }

    }

    private fun PressEnter(keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if ((keyEvent?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            UploadNotice()
            KeyboardUtils.hideSoftInput(etcaptchaanswer)

        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {

                startActivity(Intent(act, SettingsActivity::class.java))
                return true
            }
            R.id.homeAsUp -> {

                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        var uploadnotice: Notice? = null
    }


}
