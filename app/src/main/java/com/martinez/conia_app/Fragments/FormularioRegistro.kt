package com.martinez.conia_app.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.martinez.conia_app.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_formulario_registro.*


class FormularioRegistro : Fragment() {

    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formulario_registro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth  = FirebaseAuth.getInstance()

        btn_create.setOnClickListener {
            if (!ctr_email.text.toString().isEmpty() && !ctr_pass.text.toString().isEmpty()){
                auth.createUserWithEmailAndPassword(ctr_email.text.toString(), ctr_pass.text.toString())
                    .addOnCompleteListener {
                        //Acá la lógica para verificar que los datos coincidan con el usuario que cree en la base de firebase
                        if(!it.isSuccessful) return@addOnCompleteListener
                        //Sí es el correcto, pasa a la siguiente instancia, sino
                        view.findNavController().navigate(R.id.action_formularioRegistro_to_menu_home)
                        //Log.d("El usuario ingresado es correcto", it.result.toString())
                        (this.activity as AppCompatActivity).bottom_nav_view.visibility = View.VISIBLE
                    }.addOnFailureListener {
                        //Le mando un toast con la validación pura de firebase para indicar qué es lo incorrecto
                        Toast.makeText(view!!.context, "Error al crear usuario", Toast.LENGTH_LONG).show()
                    }
            }else{
                Toast.makeText(view!!.context, "Por favor rellenar TODOS los campos", Toast.LENGTH_LONG).show()
            }
        }

        login_back.setOnClickListener {
            it.findNavController().navigate(R.id.action_formularioRegistro_to_loginFragment2)
            (this.activity as AppCompatActivity).bottom_nav_view.visibility = View.GONE
        }
    }


}