

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import cu.marcos930807.robotrevo.R
import kotlinx.android.synthetic.main.insertcontact.*


/*
 * Use the [InsertNotice.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertContact : Fragment(), TextWatcher {
    override fun afterTextChanged(p0: Editable?) {
        updateContactinfo()

    }

    override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {

    }

    var isEdit: Boolean = false
    var cname=""
    var cmail=""
    var cphone=""


//    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //    isEdit = arguments!!.getBoolean(ARG_PARAM1)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.insertcontact, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clearinfo()


         contactmail.addTextChangedListener(this)
        contactname.addTextChangedListener(this)
        contactphone.addTextChangedListener(this)
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
        cmail=""
        cname=""
        cphone=""
    }

    fun updateContactinfo(){


        cmail=contactmail.text.toString()
        cname=contactname.text.toString()
        cphone=contactphone.text.toString()

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
        private var fragment: InsertContact?= null

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment InsertNotice.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: Boolean=false): InsertContact {

            if(fragment==null) {
                fragment = InsertContact()
                val args = Bundle()
                args.putBoolean(ARG_PARAM1, param1)

                fragment!!.arguments = args

            }

            return fragment as InsertContact
        }
    }
}// Required empty public constructor
