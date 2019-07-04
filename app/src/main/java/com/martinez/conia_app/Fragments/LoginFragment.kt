package com.martinez.conia_app.Fragments


import android.content.Context
import android.net.Uri
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
import kotlinx.android.synthetic.main.fragment_login.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    //Creo una variable de tipo FirebaseAuth
    private lateinit var auth : FirebaseAuth
    //-------------------------------------------
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Comienzo a crear la lógica de validación
        auth  = FirebaseAuth.getInstance() //Creo la instancia de la autenticación acá
        btn_login.setOnClickListener{
            if (!et_user.text.toString().isEmpty() && !et_pass.text.toString().isEmpty()){
                //Recupero de los XML los campos donde solicito la información para Firebase la valide
                auth.signInWithEmailAndPassword(et_user.text.toString(),et_pass.text.toString())
                        .addOnCompleteListener {
                            //Acá la lógica para verificar que los datos coincidan con el usuario que cree en la base de firebase
                            if(!it.isSuccessful) return@addOnCompleteListener
                            //Sí es el correcto, pasa a la siguiente instancia, sino
                            view.findNavController().navigate(R.id.toMainFragment)
                            //Log.d("El usuario ingresado es correcto", it.result.toString())
                            (this.activity as AppCompatActivity).bottom_nav_view.visibility = View.VISIBLE
                        }
                        .addOnFailureListener {
                            //Le mando un toast con la validación pura de firebase para indicar qué es lo incorrecto
                            Toast.makeText(view!!.context, it.localizedMessage.toString(), Toast.LENGTH_LONG).show()
                        }
            }else{
                Toast.makeText(view!!.context, "Por favor rellene los campos", Toast.LENGTH_LONG).show()
            }

        }

        to_register.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment2_to_formularioRegistro)
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onResume() {
        super.onResume()
        (this.activity as AppCompatActivity).bottom_nav_view.visibility = View.GONE
    }

    /*override fun onStop() {
        super.onStop()
        (this.activity as AppCompatActivity).bottom_nav_view.visibility = View.VISIBLE
    }*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LoginFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}