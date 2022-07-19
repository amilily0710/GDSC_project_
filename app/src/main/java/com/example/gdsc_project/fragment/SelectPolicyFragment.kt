package com.example.gdsc_project.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gdsc_project.R
import com.example.gdsc_project.databinding.FragmentSelectPolicyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.parcelize.Parcelize

//@Parcelize
//data class SelectField(
//
//    val location: String? = null,
//    val supportArea: String? = null,
////    val age : String
//) : Parcelable

class SelectPolicyFragment : Fragment() {
    private var _binding: FragmentSelectPolicyBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectPolicyBinding.inflate(inflater, container, false)
        return binding!!.root





    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            locationSpn.adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.location,
                android.R.layout.simple_list_item_1
            )

                check.setOnClickListener {
                    var selectedItem = if (cb1.isChecked){
                        cb1.text.toString()
                    }else if(cb2.isChecked){
                        cb2.text.toString()
                    } else if(cb3.isChecked){
                        cb3.text.toString()
                    }else{
                        ""
                    }
                    val action =
                        SelectPolicyFragmentDirections.actionSelectPolicyFragmentToNavigationHome(
                            location = locationSpn.selectedItem.toString(),
                            supportArea = selectedItem
                        )
                    findNavController().navigate(action)

                }




        }
    }
}


