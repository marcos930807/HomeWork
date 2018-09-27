import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.model.Notice
import cu.marcos930807.robotrevo.db.sql.NoticeDBHelper

import cu.marcos930807.robotrevo.ui.activities.NoticeDetail2
import cu.marcos930807.robotrevo.ui.activities.UpladActivity
import kotlinx.android.synthetic.main.fragment4.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.onUiThread
import com.afollestad.materialdialogs.MaterialDialog
import cu.marcos930807.robotrevo.ui.recyclerview.dialogs_adapters.NoticeOptionsAdapter
import cu.marcos930807.robotrevo.ui.recyclerview.getoptionsfornotice
import cu.marcos930807.robotrevo.ui.recyclerview.optionNotice
import android.text.InputType


/*
 * Use the [InsertNotice.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoticeListFragment : Fragment(), View.OnClickListener {

    private var dialog: MaterialDialog? = null
    var allnotice: ArrayList<Notice>? = null
    var adapter: NoticeListAdapter? = null
    lateinit var noticeDBHelper: NoticeDBHelper

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fabnewnotice -> {
                startActivity(Intent(act, NoticeDetail2::class.java))
            }

        }
    }

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mParam1 = arguments?.getString(ARG_PARAM1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment4, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noticeDBHelper = NoticeDBHelper(act)
        fabnewnotice.setOnClickListener(this)

        notice_list?.layoutManager = LinearLayoutManager(view.context)


        doAsync {
            allnotice = noticeDBHelper.readAllNotice()
            adapter = NoticeListAdapter(allnotice!!, { Notice -> clickEvent(Notice) }, { Notice -> longclickEvent(Notice) })




            onUiThread {

                notice_list?.adapter = adapter
////                //code to enable swip and drag
//                val touchhelper = RecyclerHelper<Notice>(allnotice!!, adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
////                touchhelper.setRecyclerItemDragEnabled(true).setOnDragItemListener(object : OnDragListener {
////                    override fun onDragItemListener(fromPosition: Int, toPosition: Int) {
////
////
////                    }
////                })
//
//                touchhelper.setRecyclerItemSwipeEnabled(true).setOnSwipeItemListener(object :OnSwipeListener{
//                    override fun onSwipeItemListener() {
//                        toast("se fue")
//                    }
//
//
//                })
//                val itemTouchHelper = ItemTouchHelper(touchhelper)
//                itemTouchHelper.attachToRecyclerView(notice_list)


            }
        }
    }

    private fun longclickEvent(notice: Notice): Boolean {


        return true
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
                val i = Intent(act,NoticeDetail2::class.java)
                i.putExtra("NOTICE_ID",optionNotice.notice.id)
                startActivity(i)

            }
            act.getString(R.string.option_delete) -> {
                noticeDBHelper.deleteNotice(optionNotice.notice.id.toString())
                refreshadapter(adapter!!)


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

    fun refreshadapter(adapter: NoticeListAdapter?= this.adapter) {

        doAsync {
            allnotice = noticeDBHelper.readAllNotice()

          onUiThread {
              adapter?.setData(allnotice!!)
              adapter?.notifyDataSetChanged()
          }
        }

    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private var fragment:NoticeListFragment?=null

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment InsertNotice.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String = ""): NoticeListFragment {
            if(fragment==null) {
                fragment = NoticeListFragment()
                val args = Bundle()
                args.putString(ARG_PARAM1, param1)

                fragment!!.arguments = args

            }

            return fragment as NoticeListFragment
        }


    }
}// Required empty public constructor