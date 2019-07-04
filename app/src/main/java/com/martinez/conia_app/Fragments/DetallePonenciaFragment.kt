package com.martinez.conia_app.Fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.martinez.conia_app.R
import kotlinx.android.synthetic.main.fragment_detalle_ponencia.*

class DetallePonenciaFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_ponencia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            val safeArgs = DetallePonenciaFragmentArgs.fromBundle(it)
            Log.d("report", safeArgs.ponencia.toString())

            activity?.let { it1 -> Glide.with(it1).load(safeArgs.ponencia.imagenURL).into(propio_ponente) }

            nuevo_tituloPonencia.text = safeArgs.ponencia.nombre
            descripcionCompleta.text = safeArgs.ponencia.descripcion
            tv_finalHora.text = safeArgs.ponencia.hora_final
            tv_inicioHora.text = safeArgs.ponencia.hora_inicio
            minutos.text = safeArgs.ponencia.duracion
        }

    }


}
