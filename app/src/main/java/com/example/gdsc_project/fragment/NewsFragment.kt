package com.example.gdsc_project.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager


import androidx.recyclerview.widget.RecyclerView
import com.example.gdsc_project.R
import com.example.gdsc_project.adapter.NewsAdapter
import com.example.gdsc_project.databinding.FragmentNewsBinding
import com.example.gdsc_project.model.Policy
import com.example.gdsc_project.model.Select


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList



class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<NewsFragmentArgs>()
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
        val selectInfo : ArrayList<Select> = arrayListOf()
        var possibleCnt = 0

        docRef?.get()?.addOnSuccessListener { document ->
            if (document != null) {
                Log.d("Users", "DocumentSnapshot data: ${document.data}")
                binding.name.text = document.data?.get("name").toString()

            } else {
                Log.d("Users", "No such document")
            }
        }
            ?.addOnFailureListener { exception ->
                Log.d("Users", "get failed with ", exception)
            }
        Log.d("tst5", "${args.supportArea.toString()}")
        db.collection("po")
            .addSnapshotListener{ snapshot, e ->
                if (e != null) {
                    Log.w("NewsFragment", "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (snapshot != null ) {
                    selectInfo.clear()
                    var cnt = 0
                    val supportAreas = mutableListOf<String>()
                    for (snap in snapshot){
                        // 라디오버튼을 써버리면 메인에서 한개만 뜸. 지원분야를 선택안해도 실행됨
                        val location = snap.data["지역"].toString()
                        val supportArea = snap.data["지원분야"].toString()
                        if ( location.contains(args.location.toString()) && supportArea.contains(args.supportArea.toString()))
                        {
                            supportAreas.add(snap.data["지원분야"].toString())
                        }

                    }
                    for (i in supportAreas.distinct()){
                        cnt = Collections.frequency(supportAreas, i)
                        selectInfo.add(Select(i, cnt))
                        possibleCnt += cnt
                    }
                    binding.possiblePolicy.text = "지원가능한 정책: $possibleCnt"
                    recyclerView.adapter?.notifyDataSetChanged()
                } else {
                    Log.d("NewsFragment", "Current data: null")
                }
            }

        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.adapter = NewsAdapter(selectInfo)

        binding.policyBtn.setOnClickListener {
            selectPolicy()
        }

        binding.recyclerView.setOnClickListener {
            field()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun selectPolicy(){
        findNavController().navigate(R.id.action_navigation_home_to_selectPolicyFragment)
    }
    private fun field(){
        findNavController().navigate(R.id.action_navigation_home_to_selectPolicyFragment)
    }



}