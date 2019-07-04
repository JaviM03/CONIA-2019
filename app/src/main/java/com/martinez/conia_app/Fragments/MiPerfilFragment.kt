package com.martinez.conia_app.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.martinez.conia_app.Adapters.PonenciaCustomAdapter
import com.martinez.conia_app.Entidad.Ponencia
import com.martinez.conia_app.R
import kotlinx.android.synthetic.main.vista_perfil.*
import kotlinx.android.synthetic.main.vista_perfil.view.*
import kotlinx.android.synthetic.main.vista_perfil.view.btn_cerrar_sesion

class MiPerfilFragment : Fragment() {

    private lateinit var adapter: PonenciaCustomAdapter
    private val listOfPonencias: MutableList<Ponencia> = mutableListOf()
    private var currentUid = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view= inflater.inflate(R.layout.vista_perfil, container, false)
        currentUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        getReservesByUser()
        bind(view)
//        cerrarSesion()
        return view
    }

    fun bind(view: View){
        adapter = object:PonenciaCustomAdapter(view.context, listOfPonencias){
            override fun setClickListenerToReport(holder: ViewHolder, item: Ponencia) {
                holder.itemView.setOnClickListener {
                    val nextAction = MiPerfilFragmentDirections.nextAction(item)
                    Navigation.findNavController(it).navigate(nextAction)
                }
            }

        }
        val rv = view.ponencias_inscritas
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(view.context)

    }

    private fun getReservesByUser(){
        val ref = FirebaseDatabase.getInstance().getReference("/users").child(currentUid)

        val ponenciasListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                listOfPonencias.clear()
                dataSnapshot.
                        children.mapNotNullTo(listOfPonencias) { it.getValue(Ponencia::class.java) }

                Log.d("hola", listOfPonencias.toString())

                if (::adapter.isInitialized) {
                    adapter.changeDataSet(listOfPonencias)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Ponencias", "loadPost:onCancelled ${databaseError.toException()}")
            }
        }

        ref.child("ponencias").addValueEventListener(ponenciasListener)
    }
/* Intento de cierre de sesión malo T.T
    private fun cerrarSesion(){
        btn_cerrar_sesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            view?.findNavController()?.navigate(R.id.loginFragment2)
        }
    }
*/
}
