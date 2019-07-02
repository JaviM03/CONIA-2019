package com.martinez.conia_app.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.template_ponencias.view.*


abstract class PonenciaCustomAdapter internal constructor(context: Context) : RecyclerView.Adapter<PonenciaCustomAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var items = emptyList<Ponencia>()

    abstract fun setClickListenerToReport(holder: ViewHolder, item: Ponencia)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.template_ponencias, parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        setClickListenerToReport(holder, items[position])
    }

    internal fun changeDataSet(newDataSet : List<Ponencia>){
        this.items = newDataSet
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind (item: Ponencia) = with(itemView){

            //Acá le agrego los atributos que deseo consumir de la api
            tv_nombreP.text = item.nombre
            tv_descripcion.text = item.resumen //ahorita solo un resumen
            //Con esto mando a consumir mi imagen de firebase
            Glide.with(itemView).load(item.imagenURL).into(iv_ponente)
        }
    }

}