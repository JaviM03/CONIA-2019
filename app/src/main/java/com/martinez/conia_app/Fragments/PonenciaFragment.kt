package com.martinez.conia_app.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.martinez.conia_app.Adapters.PonenciaCustomAdapter
import com.martinez.conia_app.Entidad.Ponencia

import com.martinez.conia_app.R
import kotlinx.android.synthetic.main.fragment_ponencia.view.*


class PonenciaFragment : Fragment() {

    private lateinit var ponenciasRef: DatabaseReference
    private lateinit var adapter: PonenciaCustomAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_ponencia, container, false)
        bind(view)
        getPonencias()
        return view
    }

    private fun getPonencias(){
        ponenciasRef = FirebaseDatabase.getInstance().getReference("ponencias")
        ponenciasRef.keepSynced(true)

        val reportsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                cargarListaPonencias(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //Los podes sustituir por toast para mostrar errores
                Log.d("Ponencias", "loadPost:onCancelled", databaseError.toException())
            }
        }

        ponenciasRef.addValueEventListener(reportsListener)

    }

    private fun cargarListaPonencias(dataSnapshot: DataSnapshot){

        val listaPonencia = mutableListOf<Ponencia>()

        for (postSnapshot in dataSnapshot.children) {
            val ponencia = postSnapshot.getValue(Ponencia::class.java)
            ponencia?.let { listaPonencia.add(it) }
        }

        Log.d("Ponencias", listaPonencia.toString())

        adapter.changeDataSet(listaPonencia)
    }



    fun bind(view: View){
        adapter = object:PonenciaCustomAdapter(view.context){
            override fun setClickListenerToReport(holder: ViewHolder, item: Ponencia) {
                holder.itemView.setOnClickListener {
                    //Acá mostraré los detalles de la nueva ponencia, va un nuevo navigation
                    Toast.makeText(view.context, "Posición: " + holder.adapterPosition, Toast.LENGTH_SHORT).show()
                }
            }

        }
        val rv = view.lista
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(view.context)

    }
}
