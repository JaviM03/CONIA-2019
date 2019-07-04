package com.martinez.conia_app.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.martinez.conia_app.Entidad.Ponencia
import com.martinez.conia_app.R
import kotlinx.android.synthetic.main.template_ponencias.view.*


abstract class PonenciaCustomAdapter internal constructor(context: Context, listPonencias:MutableList<Ponencia>) : RecyclerView.Adapter<PonenciaCustomAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var items = emptyList<Ponencia>()
    private val listOfPonencias: MutableList<Ponencia> = listPonencias
    private var currentUid = ""

    abstract fun setClickListenerToReport(holder: ViewHolder, item: Ponencia)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.template_ponencias, parent,false)

        currentUid = FirebaseAuth.getInstance().currentUser?.uid.toString()

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

            for (ponencia in listOfPonencias){
                if(ponencia.uid == item.uid){
                    btn_reservaR.setBackgroundResource(R.drawable.red_bt_gradient)
                    btn_reservaR.setText("Reservado")
                }
            }

            btn_reservaR.setOnClickListener {
                if(btn_reservaR.text == "Reservado")
                {
                    btn_reservaR.setBackgroundResource(R.drawable.reserva_bt_gradient)
                    btn_reservaR.setText("Reservar")
                    cancelReserve(it, item)
                }
                else
                {
                    btn_reservaR.setBackgroundResource(R.drawable.red_bt_gradient)
                    btn_reservaR.setText("Reservado")
                    reservePonencia(it, item)
                }
            }
        }
    }

    private fun reservePonencia(view:View, item:Ponencia){
        val reference = FirebaseDatabase.getInstance().getReference("/users/$currentUid/ponencias/${item.uid}")

        reference.setValue(item)
            .addOnFailureListener{
                Toast.makeText(view.context, "Hubo un error al guardar la reserva", Toast.LENGTH_LONG).show()

                val btnReserve = view.findViewById<Button>(R.id.btn_reservaR)
                btnReserve.setBackgroundResource(R.drawable.reserva_bt_gradient)
                btnReserve.setText("Reservar")
            }

    }

    private fun cancelReserve(view:View, item:Ponencia){
        val reference = FirebaseDatabase.getInstance().getReference("/users/$currentUid/ponencias/${item.uid}")

        reference.removeValue()
            .addOnFailureListener{
                Toast.makeText(view.context, "Hubo un error al guardar la reserva", Toast.LENGTH_LONG).show()

                val btnReserve = view.findViewById<Button>(R.id.btn_reservaR)
                btnReserve.setBackgroundResource(R.drawable.red_bt_gradient)
                btnReserve.setText("Reservado")
            }
    }


}