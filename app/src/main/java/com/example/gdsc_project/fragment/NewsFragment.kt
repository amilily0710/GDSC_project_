package com.example.gdsc_project.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gdsc_project.R
import com.example.gdsc_project.adapter.NewsAdapter
import com.example.gdsc_project.databinding.FragmentNewsBinding
import com.example.gdsc_project.model.Policy

import com.example.gdsc_project.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        auth = Firebase.auth
        val db = Firebase.firestore
        val docRef = auth.uid?.let { db.collection("users").document(it) }
        val policy : ArrayList<Policy> = arrayListOf()

//        db.collection("po")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result){
//                    Log.d("!!!!!!!!!!!!!", "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("!!!!!!!!!!!!!", "Error getting documents: ", exception)
//            }

        db.collection("po")
            .addSnapshotListener{ snapshot, e ->
                if (e != null) {
                    Log.w("NewsFragment", "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (snapshot != null ) {
                    policy.clear()
                    for (snap in snapshot){
                        Log.d("!!!!!!!!!!!!!", "${snap.id} => ${snap.data}")
                        policy.add(Policy(snap.data["FIELD8"]?.toString()
                            , snap.data["사이트"]?.toString()
                            , snap.data["정책내용"]?.toString()
                            , snap.data["정책명"]?.toString()
                            , snap.data["지역"]?.toString()
                            ,snap.data["지원규모"]?.toString()
                            ,snap.data["지원분야"]?.toString()
                            ,snap.data["지원인원"]?.toString()))
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    Log.d("NewsFragment", "Current data: null")
                }
            }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = NewsAdapter(policy)

        binding.policyBtn.setOnClickListener {
            selectPolicy()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun selectPolicy(){
        findNavController().navigate(R.id.action_navigation_home_to_selectPolicyFragment)
    }


}