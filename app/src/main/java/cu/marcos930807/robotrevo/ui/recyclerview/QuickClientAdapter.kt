package cu.marcos930807.robotrevo.ui.recyclerview

import android.view.View
import com.beardedhen.androidbootstrap.BootstrapLabel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.ivbaranov.mli.MaterialLetterIcon
import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.model.Client

class QuickClientAdapter(ClientList: MutableList<Client>?) : BaseQuickAdapter<Client, BaseViewHolder>(R.layout.item_client,ClientList) {

    override fun convert(helper: BaseViewHolder, item: Client) {

        helper.setText(R.id.name,item.name)
        helper.setText(R.id.mail,item.mail)
        helper.setText(R.id.phone,item.phone)
        val circle=   helper.itemView.findViewById<MaterialLetterIcon>(R.id.icon)
        circle.letter= item.name
        val count = helper.itemView.findViewById<BootstrapLabel>(R.id.childs)
        count.visibility = View.GONE

 //        if(!item.photoa.isEmpty()){
//
//           helper.setImageBitmap(R.id.icImage, BitmapfromPath(item.photoa))
//        }else{
//            helper.setImageResource(R.id.icImage, R.drawable.wall)
//        }
        //Aqui se puede establecer una condicion en base si se quiere que un hijo de un Notice especifico sea cliqueable o no en mi caso todos son clickeables
       // helper.addOnClickListener(R.id.icImage)

    }
}