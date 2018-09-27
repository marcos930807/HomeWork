package cu.marcos930807.robotrevo.ui.recyclerview.Expandable

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.github.ivbaranov.mli.MaterialLetterIcon
import com.squareup.picasso.Picasso
import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.mappers.getCategoryNamefromRevoid

import cu.marcos930807.robotrevo.ui.utils.BitmapfromPath
import org.jetbrains.anko.find
import java.io.File


class ExpandableClientAdapter(data: List<MultiItemEntity> ) : BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder>(data){

    init {
        addItemType(TYPE_CLIENT, R.layout.item_client)
        addItemType(TYPE_NOTICE, R.layout.item_data)
    }
    override fun convert(holder: BaseViewHolder?, item: MultiItemEntity) {
        when(holder?.itemViewType){
            TYPE_CLIENT ->{

                val aclient = item as AClient

                val childs= aclient.subItems
                var childscount:Int?=0
                if(childs!=null) {
                    childscount = childs.size
                }
                val client= aclient.client
                val circle=   holder.itemView.findViewById<MaterialLetterIcon>(R.id.icon)
                circle.letter= client.name
                holder.setText(R.id.childs,childscount.toString())
                holder.setText(R.id.name,client.name)
                holder.setText(R.id.mail,client.mail)
                holder.setText(R.id.phone,client.phone)
                holder.setImageResource(R.id.indice, if(aclient.isExpanded) R.drawable.ic_ind_close else R.drawable.ic_ind_open)
                holder.itemView.setOnClickListener{
                    val pos = holder.adapterPosition
                    if(aclient.isExpanded){

                        collapse(pos)

                    }else{

                        expand(pos)

                    }

                }
                holder.addOnClickListener(R.id.icon)
         }
            TYPE_NOTICE ->{
                val anotice = item as ANotice
                val notice= anotice.notice
                holder.setText(R.id.dettitle,notice.title)
                holder.setText(R.id.debody,notice.body)
                holder.setText(R.id.deprice,notice.price)
                if (!notice.category.isEmpty()){
                    holder.setText(R.id.labelcategory, getCategoryNamefromRevoid(notice.category.toInt()))
                }
                if(!notice.photoa.isEmpty()){
                    Glide.with(holder.itemView.context).load(File(notice.photoa)).into(holder.itemView.find<ImageView>(R.id.icImage))
                  //  holder.setImageBitmap(R.id.icImage, BitmapfromPath(notice.photoa))
                }else{
                    holder.setImageResource(R.id.icImage, R.drawable.wall)
                }

//Aqui se puede establecer una condicion en base si se quiere que un hijo de un Notice especifico sea cliqueable o no en mi caso todos son clickeables
                holder.addOnClickListener(R.id.icImage)

            }
        }

    }
    companion object {
        private val TAG = ExpandableClientAdapter::class.java.simpleName
        val TYPE_CLIENT:Int=0
        val TYPE_NOTICE:Int=1
    }


}