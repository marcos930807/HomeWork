import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.rengwuxian.materialedittext.MaterialEditText

import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.model.Client
import cu.marcos930807.robotrevo.db.model.Notice
import cu.marcos930807.robotrevo.db.sql.NoticeDBHelper
import cu.marcos930807.robotrevo.ui.activities.NoticeDetail2
import cu.marcos930807.robotrevo.ui.activities.UpladActivity
import cu.marcos930807.robotrevo.ui.recyclerview.Expandable.AClient
import cu.marcos930807.robotrevo.ui.recyclerview.Expandable.ANotice
import cu.marcos930807.robotrevo.ui.recyclerview.Expandable.ExpandableClientAdapter
import cu.marcos930807.robotrevo.ui.recyclerview.dialogs_adapters.ClientOptionsAdapter
import cu.marcos930807.robotrevo.ui.recyclerview.dialogs_adapters.NoticeOptionsAdapter
import cu.marcos930807.robotrevo.ui.recyclerview.getoptionsforClients
import cu.marcos930807.robotrevo.ui.recyclerview.getoptionsfornotice
import cu.marcos930807.robotrevo.ui.recyclerview.optionClient
import cu.marcos930807.robotrevo.ui.recyclerview.optionNotice
import cu.marcos930807.robotrevo.ui.utils.isValidEmail

import kotlinx.android.synthetic.main.fragment3.*

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast


/*
 * Use the [InsertNotice.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExpandableClientFragment : Fragment(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fabnewclient -> {
                insertOreditDialog(false, null)

            }

        }

    }

    fun insertOreditDialog(isEdit: Boolean = false, client: Client?) {
        val dialog = MaterialDialog.Builder(act)
                .title(if(!isEdit)"Añadir cliente" else "Editar cliente")
                .customView(R.layout.insertcontact, true)
                .positiveText(if(!isEdit)"Añadir" else "Editar")
                .onPositive(MaterialDialog.SingleButtonCallback { dialog, which ->
                    val v = dialog.customView
                    val mail = v?.find<MaterialEditText>(R.id.contactmail)
                    val name = v?.find<MaterialEditText>(R.id.contactname)
                    val phone = v?.find<MaterialEditText>(R.id.contactphone)
                    //Validar los campos

                    if(mail!!.text.toString().isEmpty()){

                        toast("Email Campo obligatorio")
                    //    return@SingleButtonCallback
                    }else if(!mail.text.toString().isValidEmail()) {
                        toast("Email no válido")
                  //      return@SingleButtonCallback
                    }else{


                    if (isEdit) {
                        val eclient = Client(client!!.id, mail!!.text.toString(), name!!.text.toString(), phone!!.text.toString())
                        noticeDBHelper.updateClient(eclient)
                    } else {
                        // toast(name!!.text.toString())
                        val nclient = Client(null, mail!!.text.toString(), name!!.text.toString(), phone!!.text.toString())
                        noticeDBHelper.insertClient(nclient)
                    }


                    refreshAdapter()
                    }

                }).show()

        if (isEdit) {
            val v = dialog.customView
            val mail = v?.find<MaterialEditText>(R.id.contactmail)
            val name = v?.find<MaterialEditText>(R.id.contactname)
            val phone = v?.find<MaterialEditText>(R.id.contactphone)
            mail!!.setText(client!!.mail)
            name!!.setText(client.name)
            phone!!.setText(client.phone)
        }


    }

    // TODO: Rename and change types of parameters

    private var dialog: MaterialDialog? = null

    private var mParam1: String? = null
    var allclient: ArrayList<Client>? = null
    var adapter: ExpandableClientAdapter? = null
    lateinit var noticeDBHelper: NoticeDBHelper
    private val REQUEST_CODE = 25


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mParam1 = arguments?.getString(ARG_PARAM1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noticeDBHelper = NoticeDBHelper(act)
        fabnewclient.setOnClickListener(this)


        client_list?.layoutManager = LinearLayoutManager(view.context)
        val items = mutableListOf<MultiItemEntity>()
        adapter = ExpandableClientAdapter(items)
        adapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        client_list?.adapter = adapter
        adapter!!.bindToRecyclerView(client_list)
        adapter!!.setEmptyView(R.layout.empty_view)
        adapter!!.setOnItemClickListener { adapter, view, position ->
            val anotice = adapter.data[position] as ANotice

            clickEvent(anotice.notice)
        }
        adapter!!.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.data[position] as MultiItemEntity
            val itemtype = item.itemType
            when (itemtype) {
                ExpandableClientAdapter.TYPE_CLIENT -> {
                    val aclient = adapter.data[position] as AClient

                    val optionsAdapter = ClientOptionsAdapter(getoptionsforClients(act, aclient.client), { optionClient -> optionClientclicked(optionClient) })

                    dialog = MaterialDialog.Builder(act)
                            .title(aclient.client.name)
                            .iconRes(R.drawable.ic_contacts_black_24dp)
                            .adapter(optionsAdapter, null)

                            .show()

                }
                ExpandableClientAdapter.TYPE_NOTICE -> {
                    toast("tocada la foto notice")
                }
            }
        }


        refreshAdapter()
    }

    private fun optionClientclicked(optionClient: optionClient) {
        when (optionClient.title) {
            act.getString(R.string.option_new_notice)->{
                val i = Intent(act, NoticeDetail2::class.java)
                i.putExtra("CLIENT_ID",optionClient.client.id)
                i.putExtra("CLIENT_MODE",true)
                startActivityForResult(i,REQUEST_CODE)
            }
            act.getString(R.string.option_edit_c) -> {
                insertOreditDialog(true, optionClient.client)
            }
            act.getString(R.string.option_delete) -> {
                noticeDBHelper.deleteClient(optionClient.client.id.toString())
                refreshAdapter()
            }
        }
        dialog?.dismiss()
    }

    private fun generatedata(): MutableList<MultiItemEntity> {
        val multiItem = mutableListOf<MultiItemEntity>()
        doAsync {

            allclient = noticeDBHelper.readAllClients()
            for (client in allclient!!) {
                val notices = noticeDBHelper.readAllNoticefromClientid(client.id.toString())
                val aclient = AClient(client)
                for (notice in notices) {
                    val anotice = ANotice(notice)
                    aclient.addSubItem(anotice)
                }
                multiItem.add(aclient)

            }
            onUiThread {

                adapter!!.setNewData(multiItem)
            }

        }
        return multiItem
    }

    fun refreshAdapter() {
        val newmultiitem = generatedata()

    }


    fun optionclicked(optionNotice: optionNotice) {
        when (optionNotice.title) {
            act.getString(R.string.option_upload) -> {
                UpladActivity.uploadnotice = optionNotice.notice
                startActivity(Intent(act, UpladActivity::class.java))
            }
            act.getString(R.string.option_no_captcha) -> {
                //  NoCapchaActivity.uploadnotice = optionNotice.notice
                //    startActivity(Intent(act, DetailActivity::class.java))
            }
            act.getString(R.string.option_upload_nopic) -> {
                optionNotice.notice.photoa = ""
                optionNotice.notice.photob = ""
                optionNotice.notice.photoc = ""
                UpladActivity.uploadnotice = optionNotice.notice
                startActivity(Intent(act, UpladActivity::class.java))
            }
            act.getString(R.string.option_edit_money) -> {

                MaterialDialog.Builder(act)
                        .title(R.string.dialog_input)
                        //   .content(optionNotice.notice.price.toString())
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input(act.getString(R.string.dialog_input_hint), optionNotice.notice.price.toString(), MaterialDialog.InputCallback { dialog, input ->
                            optionNotice.notice.price = input.toString()
                            noticeDBHelper.updateNotice(optionNotice.notice)

                            refreshAdapter()

                        }).show()

            }
            act.getString(R.string.option_edit) -> {
                val i = Intent(act, NoticeDetail2::class.java)
                i.putExtra("NOTICE_ID", optionNotice.notice.id)
                i.putExtra("EDIT_MODE",true)
                startActivityForResult(i, REQUEST_CODE)

            }
            act.getString(R.string.option_delete) -> {
                noticeDBHelper.deleteNotice(optionNotice.notice.id.toString())
                refreshAdapter()


            }
        }
        dialog?.dismiss()

    }


    private fun clickEvent(notice: Notice) {


        val optionsAdapter = NoticeOptionsAdapter(getoptionsfornotice(act, notice), { optionNotice -> optionclicked(optionNotice) })

        dialog = MaterialDialog.Builder(act)
                .title(notice.title)
                .iconRes(R.drawable.ic_dialog)
                .adapter(optionsAdapter, null)
                .show()

        // val list = dialog.recyclerView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            refreshAdapter()

        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

        private val ARG_PARAM1 = "param1"
        private var fragment: ExpandableClientFragment? = null

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment InsertNotice.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String = ""): ExpandableClientFragment {
            if (fragment == null) {
                fragment = ExpandableClientFragment()
                val args = Bundle()
                args.putString(ARG_PARAM1, param1)

                fragment!!.arguments = args
            }

            return fragment as ExpandableClientFragment
        }
    }
}// Required empty public constructor