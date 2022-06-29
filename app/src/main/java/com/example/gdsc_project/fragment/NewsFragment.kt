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
    //    private lateinit var database: DatabaseReference
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
//        database = Firebase.database.reference
        auth = Firebase.auth
        val db = Firebase.firestore
        val docRef = auth.uid?.let { db.collection("users").document(it) }
        val user : ArrayList<User> = arrayListOf()
//        myPost.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                user.clear()
//                for(postSnapshot in snapshot.children){
//                    // 여기에 DB에 있는 정책 불러오기
//                    Log.d("@@@@@@@@@@@@", postSnapshot.value.toString())
//                }
//               recyclerView.adapter?.notifyDataSetChanged()
//            }
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })

        docRef?.addSnapshotListener{ snapshot, e->
            if (e != null) {
                Log.w("NewsFragment", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                user.clear()
                Log.d("NewsFragment", "Current data: ${snapshot.data?.get("name")}")
                Log.d("NewsFragment", "Current data: ${snapshot.data?.get("email")}")
                Log.d("NewsFragment", "Current data: ${snapshot.data?.get("age")}")

                val email = snapshot.data?.get("email").toString()
                val username = snapshot.data?.get("name").toString()
                val age = snapshot.data?.get("age").toString()
                user.add(User(email, username, age))

                recyclerView.adapter?.notifyDataSetChanged()

            } else {
                Log.d("NewsFragment", "Current data: null")
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = NewsAdapter(user)

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