package com.example.gdsc_project.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gdsc_project.R
import com.example.gdsc_project.databinding.FragmentSelectPolicyBinding


class SelectPolicyFragment : Fragment() {
    private var binding: FragmentSelectPolicyBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectPolicyBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            check.setOnClickListener {
                findNavController().navigate(R.id.action_selectPolicyFragment_to_navigation_home)
            }

        }
    }
}