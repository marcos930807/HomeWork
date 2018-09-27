package cu.marcos930807.robotrevo.ui.activities

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.BottomNavigationView
import android.support.transition.Visibility
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.marverenic.colors.activity.ColorsAppCompatActivity
import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.model.Notice
import cu.marcos930807.robotrevo.db.sql.NoticeDBHelper
import io.multimoon.colorful.CAppCompatActivity

import kotlinx.android.synthetic.main.activity_notice_detail2.*
import org.jetbrains.anko.act
import org.jetbrains.anko.toast
import com.afollestad.materialdialogs.folderselector.FileChooserDialog
import com.beardedhen.androidbootstrap.BootstrapDropDown
import com.squareup.picasso.Picasso
import cu.marcos930807.robotrevo.db.mappers.getCategoryInfofromDropdown
import java.io.File
import com.zhihu.matisse.engine.impl.PicassoEngine
import com.zhihu.matisse.filter.Filter.K
import com.zhihu.matisse.MimeType.ofImage
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import android.content.Intent
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import cu.marcos930807.robotrevo.db.mappers.getCategoryNamefromRevoid
import cu.marcos930807.robotrevo.db.model.Client
import cu.marcos930807.robotrevo.ui.recyclerview.QuickClientAdapter
import cu.marcos930807.robotrevo.ui.utils.*
import kotlinx.android.synthetic.main.app_bar_detail.*
import org.jetbrains.anko.support.v4.act


class NoticeDetail2 : CAppCompatActivity(), View.OnClickListener, FileChooserDialog.FileCallback {
    val REQUEST_CODE_CHOOSE = 23
    var isEditmode = false
    var isClientmode = false
    var id: Long? = null

    var clientid:Long?=null
    var category: String = "31"
    override fun onFileChooserDismissed(dialog: FileChooserDialog) {
        dialog.dismiss()
    }

    override fun onFileSelection(dialog: FileChooserDialog, file: File) {
        val path = file.absolutePath
        when (Imageclicked) {
            R.id.Ima -> {
                photoa.setText(path)
                Glide.with(act).load(file).into(Ima)
            }
            R.id.Imb -> {
                photob.setText(path)
                Glide.with(act).load(file).into(Imb)
            }
            R.id.Imc -> {
                photoc.setText(path)
                Glide.with(act).load(file).into(Imc)
            }
            R.id.adj ->{
                val string:StringBuilder= StringBuilder()
                file.forEachLine { string.append(it)
                string.append("\n")}
                noticebody.setText(string)
            }
        }
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.adj->{
                selectFile("text/plain")
                Imageclicked = R.id.adj
            }

            R.id.Ima -> {
                selectFile()
                Imageclicked = R.id.Ima
            }
            R.id.Imb -> {
                selectFile()
                Imageclicked = R.id.Imb
            }
            R.id.Imc -> {
                selectFile()
                Imageclicked = R.id.Imc
            }
            R.id.fabinsert -> {

                val notice = Notice(id,
                        category,
                        noticetitle.text.toString(),
                        noticebody.text.toString(),
                        noticeprice.text.toString(),
                        contactmail.text.toString(),
                        contactname.text.toString(),
                        contactphone.text.toString(),
                        photoa.text.toString(), photob.text.toString(), photoc.text.toString(),clientid)

                //Validando los campos
                if(notice.title.isEmpty()){
                    toast("Título Campo obligatorio")
                    return
                }
                if(notice.mail.isEmpty()){

                    toast("Email Campo obligatorio")
                    return
                }else if(!notice.mail.isValidEmail()){
                    toast("Email no válido")
                    return
                }
                if (notice.clientid==null){
                    //dar un warning de que el anuncio no pertenece a ninguncliente
                    toast("El anuncio no pertenece a ningun cliente")
                }

                if (isEditmode) {
                    val result = noticeDBHelper.updateNotice(notice)
                    if (result) {
                        toast("Actualizado correctamente")
                        setResult(Activity.RESULT_OK)
                    }
                } else {
                    val result = noticeDBHelper.insertNotice(notice)
                    if (result) {
                        setResult(Activity.RESULT_OK)
                        toast("Insertado correctamente")
                    }

                }
         //       NoticeListFragment.newInstance("").refreshadapter()
         //      NoticeListV2.newInstance("").refreshadapter()
                act.finish()
            }
            R.id.btnselect -> {
                val dark = getDark(mypref(act))

                    Matisse.from(act)
                            .choose(MimeType.ofImage())
                            .theme(if (dark)R.style.Matisse_Dracula else R.style.Matisse_Zhihu)
                            .countable(true)
                            //   .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                            .maxSelectable(3)

                            .imageEngine(Glide4Engine())
                            .forResult(REQUEST_CODE_CHOOSE)


            }
            R.id.btnselectclient->{
                val allclient = noticeDBHelper.readAllClients()
                val adapter = QuickClientAdapter(allclient)
                val dialog=  MaterialDialog.Builder(act)
                        .title("Clientes")
                        .iconRes(R.drawable.ic_contacts_black_24dp)
                        .adapter(adapter, null)
                        .show()
                adapter.setOnItemClickListener { adapter, view, position ->
                    val client = adapter.data[position] as Client
                    clientid=client.id
                    toast("Seleccionado el cliente${client.name}")
                    contactmail.setText(client.mail)
                    contactname.setText(client.name)
                    contactphone.setText(client.phone)
                    contactmail.isEnabled=false
                    contactname.isEnabled=false
                    contactphone.isEnabled=false
                    dialog.dismiss()
                }
            }
        }
    }

    lateinit var noticeDBHelper: NoticeDBHelper
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                noticecontainer.visibility = (View.VISIBLE)
                contactcontainer.visibility = (View.GONE)
                imagecontainer.visibility = (View.GONE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                noticecontainer.visibility = (View.GONE)
                contactcontainer.visibility = (View.VISIBLE)
                imagecontainer.visibility = (View.GONE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                noticecontainer.visibility = (View.GONE)
                contactcontainer.visibility = (View.GONE)
                imagecontainer.visibility = (View.VISIBLE)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_detail2)
        setSupportActionBar(detailtoolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        noticeDBHelper = NoticeDBHelper(this)
        val extras: Bundle? = intent.extras
        if (extras != null) {
            id = extras.getLong("NOTICE_ID")
            clientid = extras.getLong("CLIENT_ID")
            isEditmode = extras.getBoolean("EDIT_MODE",false)
            isClientmode = extras.getBoolean("CLIENT_MODE",false)
        }
        if (isClientmode) forceclientoption()

        categories.setOnDropDownItemClickListener(BootstrapDropDown.OnDropDownItemClickListener { parent, v, id -> cotegoryclicked(v, id) })



        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        if (isEditmode) {
            val array = noticeDBHelper.readNotice(id.toString())
            val noticetoedit = array.get(0)


            category = noticetoedit.category
            clientid= noticetoedit.clientid
            if (!category.isEmpty()) {
                categories.setText(getCategoryNamefromRevoid(category.toInt()))
            }

            noticetitle.setText(noticetoedit.title)
            noticebody.setText(noticetoedit.body)
            noticeprice.setText(noticetoedit.price)
            contactmail.setText(noticetoedit.mail)
            contactname.setText(noticetoedit.ownername)
            contactphone.setText(noticetoedit.number)
            photoa.setText(noticetoedit.photoa)
            photob.setText(noticetoedit.photob)
            photoc.setText(noticetoedit.photoc)
            fabinsert.setImageResource(R.drawable.ic_edit_white_24dp)

            Glide.with(act).load(File(noticetoedit.photoa)).into(Ima)
            Glide.with(act).load(File(noticetoedit.photob)).into(Imb)
            Glide.with(act).load(File(noticetoedit.photoc)).into(Imc)

        }


        fabinsert.setOnClickListener(this)
        Ima.setOnClickListener(this)
        Imb.setOnClickListener(this)
        Imc.setOnClickListener(this)
        btnselect.setOnClickListener(this)
        btnselectclient.setOnClickListener(this)
        adj.setOnClickListener(this)

    }

    private fun forceclientoption() {
        val client = noticeDBHelper.readClient(clientid.toString())[0]
        contactmail.setText(client.mail)
        contactname.setText(client.name)
        contactphone.setText(client.phone)
        contactmail.isEnabled=false
        contactname.isEnabled=false
        contactphone.isEnabled=false
        btnselectclient.isEnabled=false

    }

    private fun cotegoryclicked(v: View?, id: Int) {
        val cat = getCategoryInfofromDropdown(id)
        categories.setText(cat[0])
        category = cat[1]

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            var paths = mutableListOf<String>()

            try {
                paths = Matisse.obtainPathResult(data)
            } catch (e: Exception) {
                Log.e("Execption: ", e.toString())
            } finally {
                if (paths.size >= 1) {
                    photoa.setText(paths[0])
                   Glide.with(act).load(File(paths[0])).into(Ima)
                }

                if (paths.size >= 2) {
                    photob.setText(paths[1])
                    Glide.with(act).load(File(paths[1])).into(Imb)
                }
                if (paths.size >= 3) {
                    photoc.setText(paths[2])
                    Glide.with(act).load(File(paths[2])).into(Imc)
                }


            }
            //   mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data))
            Log.e("OnActivityResult ", Matisse.obtainOriginalState(data).toString())
        }
    }

    companion object {
        var Imageclicked: Int? = null

    }
}
