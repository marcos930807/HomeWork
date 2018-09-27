import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine


import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.ui.utils.getDark
import cu.marcos930807.robotrevo.ui.utils.mypref


import kotlinx.android.synthetic.main.insertimg.*
import org.jetbrains.anko.act
import org.jetbrains.anko.support.v4.act
import java.io.File


/*
 * Use the [InsertNotice.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertImg : Fragment(), View.OnClickListener {
    var phata: String = ""
    var phatb: String = ""
    var phatc: String = ""

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.Ima -> {

            }
            R.id.Imb -> {

            }
            R.id.Imc -> {

            }
            R.id.btnselect -> {
                val dark = getDark(mypref(act))
                if (dark) {
                    Matisse.from(fragment)
                            .choose(MimeType.ofImage())
                            .theme(R.style.Matisse_Dracula)
                            .countable(true)
                            //   .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                            .maxSelectable(3)

                            .imageEngine(PicassoEngine())
                            .forResult(REQUEST_CODE_CHOOSE)
                } else {
                    Matisse.from(fragment)
                            .choose(MimeType.ofImage())
                            .theme(R.style.Matisse_Zhihu)
                            .countable(true)
                            //   .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                            .maxSelectable(3)

                            .imageEngine(PicassoEngine())
                            .forResult(REQUEST_CODE_CHOOSE)
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            var paths = mutableListOf<String>()

            try {
                paths = Matisse.obtainPathResult(data)
                phata = paths[0]
                phatb = paths[1]
                phatc = paths[2]
            } catch (e: Exception) {
                Log.e("Execption: ", e.toString())
            } finally {
                if (paths.size >= 1) {
                    photoa.setText(phata)
                    Picasso.with(act).load(File(phata)).into(Ima)
                }

                if (paths.size >= 2) {
                    photob.setText(phatb)
                    Picasso.with(act).load(File(phatb)).into(Imb)
                }
                if (paths.size >= 3) {
                    photoc.setText(phatc)
                    Picasso.with(act).load(File(phatc)).into(Imc)
                }


            }
            //   mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data))
            Log.e("OnActivityResult ", Matisse.obtainOriginalState(data).toString())
        }
    }

    var isEdit: Boolean = false
    val REQUEST_CODE_CHOOSE = 23


//    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  isEdit = arguments!!.getBoolean(ARG_PARAM1)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.insertimg, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Ima.setOnClickListener(this)
        Imb.setOnClickListener(this)
        Imc.setOnClickListener(this)
        btnselect.setOnClickListener(this)
        clearinfo()

        if (!phata.isEmpty()) {

            Picasso.with(act).load(File(phata)).into(Ima)
        }
        if (!phatb.isEmpty()) {

            Picasso.with(act).load(File(phatb)).into(Imb)
        }
        if (!phatc.isEmpty()) {

            Picasso.with(act).load(File(phatc)).into(Imc)
        }


    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
//        if (mListener != null) {
//            mListener!!.onFragmentInteraction(uri)
//        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
        //  mListener = null
    }

    fun clearinfo(){
        phata=""
        phatb=""
        phatc=""
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
        private var fragment: InsertImg? = null

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment InsertNotice.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: Boolean = false): InsertImg {

            if (fragment == null) {
                fragment = InsertImg()
                val args = Bundle()
                args.putBoolean(ARG_PARAM1, param1)

                fragment!!.arguments = args

            }

            return fragment as InsertImg
        }
    }
}// Required empty public constructor
