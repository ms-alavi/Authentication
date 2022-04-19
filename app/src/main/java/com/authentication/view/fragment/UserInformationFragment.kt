package com.authentication.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.authentication.R
import com.authentication.databinding.FragmentUserInformationBinding
import com.authentication.view.activity.LoginActivity
import com.authentication.viewmodel.AuthenticationViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInformationFragment : Fragment() {

    private val viewModel by viewModels<AuthenticationViewModel>()
    private lateinit var binding: FragmentUserInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_information, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserInfo()
        viewModel.userViewModel.observe(viewLifecycleOwner) {
            Picasso.get()
                .load(it.image)
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.img)
            binding.name.text = "Name : ${it.firstName}   ${it.lastName}"
            binding.address.text = "Address : "+it.address
            binding.phoneNumber.text ="Phone : "+ it.phone
        }
        viewModel.indicator.observe(viewLifecycleOwner){
            if (it){
                binding.progressCircular.visibility=View.VISIBLE
            }else{
                binding.progressCircular.visibility=View.INVISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_detial_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                viewModel.removeToken()
                requireActivity().startActivity(LoginActivity.newInstance(requireActivity()))
                requireActivity().finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            UserInformationFragment()
    }
}