

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

import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.model.Notice
import cu.marcos930807.robotrevo.db.sql.NoticeDBHelper
import cu.marcos930807.robotrevo.ui.activities.NoticeDetail2
import cu.marcos930807.robotrevo.ui.activities.UpladActivity
import cu.marcos930807.robotrevo.ui.recyclerview.dialogs_adapters.NoticeOptionsAdapter
import cu.marcos930807.robotrevo.ui.recyclerview.QuickAdapter
import cu.marcos930807.robotrevo.ui.recyclerview.getoptionsfornotice
import cu.marcos930807.robotrevo.ui.recyclerview.optionNotice
import kotlinx.android.synthetic.main.fragment2.*
import org.jetbrains.anko.doAsync

import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.toast


/*
 * Use the [InsertNotice.newInstance] factory method to
 * create an instance of this noticeListV.
 */
class NoticeListV2 : Fragment(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabnewnotice -> {
                startActivityForResult(Intent(act, NoticeDetail2::class.java),REQUEST_CODE)
            }

        }
    }

    // TODO: Rename and change types of parameters
    private var dialog: MaterialDialog? = null
    private var mParam1: String? = null
    var allnotice: ArrayList<Notice>? = ArrayList()
    var adapter: QuickAdapter?=null
    private val REQUEST_CODE= 25
    lateinit var noticeDBHelper: NoticeDBHelper
//    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            mParam1 = arguments?.getString(ARG_PARAM1)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && requestCode==REQUEST_CODE){
            refreshadapter()

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this noticeListV
        return inflater.inflate(R.layout.fragment2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noticeDBHelper = NoticeDBHelper(act)
        fabnewnotice.setOnClickListener(this)
        notice_list?.layoutManager = LinearLayoutManager(view.context)
        allnotice=ArrayList()
        adapter =QuickAdapter(allnotice!!)
        adapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        notice_list?.adapter=adapter
        adapter!!.bindToRecyclerView(notice_list)

        adapter!!.setEmptyView(R.layout.empty_view)
        adapter!!.setOnItemClickListener { adapter, view, position ->
            val notice = adapter.data[position] as Notice
            clickEvent(notice)
        }
        adapter!!.setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.icImage->{
                    toast("Tocada la photo")
                }
            }
        })

        refreshadapter()


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
                optionNotice.notice.photoa=""
                optionNotice.notice.photob=""
                optionNotice.notice.photoc=""
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

                            refreshadapter(adapter!!)

                        }).show()

            }
            act.getString(R.string.option_edit) -> {
                val i = Intent(act, NoticeDetail2::class.java)
                i.putExtra("NOTICE_ID",optionNotice.notice.id)
                i.putExtra("EDIT_MODE",true)
                startActivityForResult(i,REQUEST_CODE)

            }
            act.getString(R.string.option_delete) -> {
                noticeDBHelper.deleteNotice(optionNotice.notice.id.toString())
                refreshadapter(adapter!!)


            }
        }
        dialog?.dismiss()

    }
    fun refreshadapter(adapter: QuickAdapter?= this.adapter) {
        if(this.adapter!=null){
            doAsync {
                allnotice = noticeDBHelper.readAllNotice()

                onUiThread {
                    adapter?.setNewData(allnotice)
                      //  adapter?.notifyDataSetChanged()
                }
            }
        }
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



    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the noticeListV initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private var noticeListV:NoticeListV2? =null

        /**
         * Use this factory method to create a new instance of
         * this noticeListV using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of noticeListV InsertNotice.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String = ""): NoticeListV2 {
            if(noticeListV==null){
                noticeListV = NoticeListV2()
                val args = Bundle()
                args.putString(ARG_PARAM1, param1)

                noticeListV!!.arguments = args
            }


            return noticeListV as NoticeListV2
        }
    }
}// Required empty public constructor