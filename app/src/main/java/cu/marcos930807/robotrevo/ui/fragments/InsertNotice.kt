

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beardedhen.androidbootstrap.BootstrapDropDown


import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.mappers.getCategoryInfofromDropdown
import cu.marcos930807.robotrevo.db.mappers.getCategoryNamefromRevoid
import kotlinx.android.synthetic.main.insertnotice.*


/*
 * Use the [InsertNotice.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertNotice : Fragment(), TextWatcher {

    override fun afterTextChanged(p0: Editable?) {
        updateInsertinfo()

    }

    override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {

    }

    var category: String = ""
    var title: String =""
    var price: String= ""
    var body: String= ""

    var isEdit: Boolean = false


//    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         //   isEdit = arguments!!.getBoolean(ARG_PARAM1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.insertnotice, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categories.setOnDropDownItemClickListener(BootstrapDropDown.OnDropDownItemClickListener { parent, v, id ->  cotegoryclicked(v,id) })

//             if(!category.isEmpty()){
//                 categories.setText(getCategoryNamefromRevoid(category.toInt()))
//             }

        clearinfo()



        noticetitle.addTextChangedListener( this)
        noticebody.addTextChangedListener(this)
        noticeprice.addTextChangedListener(this)



    }
    fun updateInsertinfo(){


        title= noticetitle.text.toString()
        body= noticebody.text.toString()
        price= noticeprice.text.toString()



    }
    fun clearinfo(){
        title=""
        body=""
        price=""
    }

    private fun cotegoryclicked(v: View?, id: Int) {
        val cat = getCategoryInfofromDropdown(id)
        categories.setText(cat[0])
        category = cat[1]
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
        private var fragment: InsertNotice?= null

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment InsertNotice.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: Boolean = false): InsertNotice {

            if(fragment==null) {
                fragment = InsertNotice()
                val args = Bundle()
                args.putBoolean(ARG_PARAM1, param1)

                fragment!!.arguments = args

            }

            return fragment as InsertNotice
        }
    }
}// Required empty public constructor
