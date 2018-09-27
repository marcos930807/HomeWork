import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rengwuxian.materialedittext.MaterialEditText

import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.model.Client
import cu.marcos930807.robotrevo.db.sql.NoticeDBHelper
import cu.marcos930807.robotrevo.ui.recyclerview.QuickClientAdapter

import kotlinx.android.synthetic.main.fragment3.*

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.toast


/*
 * Use the [InsertNotice.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientFragment : Fragment(), View.OnClickListener {
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.fabnewclient->{
                dialog = MaterialDialog.Builder(act)
                        .title("Añadir cliente")
                        .customView(R.layout.insertcontact,true)
                        .positiveText("Añadir")
                        .onPositive(MaterialDialog.SingleButtonCallback { dialog, which ->
                            val v = dialog.customView
                            val mail = v?.find<MaterialEditText>(R.id.contactmail)
                            val name = v?.find<MaterialEditText>(R.id.contactname)
                            val phone = v?.find<MaterialEditText>(R.id.contactphone)
                          //  toast(name!!.text.toString())
                            val client = Client(null,mail!!.text.toString(),name!!.text.toString(),phone!!.text.toString())
                            noticeDBHelper.insertClient(client)
                        })
                        .show()

            }

        }

    }

    // TODO: Rename and change types of parameters

    private var dialog: MaterialDialog? = null
    private var mParam1: String? = null
    var allclient: ArrayList<Client>? = null
    var adapter: QuickClientAdapter?=null
    lateinit var noticeDBHelper: NoticeDBHelper

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
        allclient = ArrayList()
        adapter = QuickClientAdapter(allclient!!)
        client_list?.adapter=adapter
        adapter!!.bindToRecyclerView(client_list)

        adapter!!.setEmptyView(R.layout.empty_view)
        adapter!!.setOnItemClickListener { adapter, view, position ->
            val client = adapter.data[position] as Client
            clickEvent(client)
        }
        doAsync {
         //   allclient = noticeDBHelper.readAllClients()
            onUiThread {
            //    adapter!!.openLoadAnimation(BaseQuickAdapter.SCALEIN)
            }
        }


    }

    private fun clickEvent(client: Client) {
        toast(client.mail)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        //  mListener = null
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
        private var fragment:ClientFragment?=null

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment InsertNotice.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String = ""): ClientFragment {
            if(fragment==null){
                fragment = ClientFragment()
                val args = Bundle()
                args.putString(ARG_PARAM1, param1)

                fragment!!.arguments = args
            }

            return fragment as ClientFragment
        }
    }
}// Required empty public constructor