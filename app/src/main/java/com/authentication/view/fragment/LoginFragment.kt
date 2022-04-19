package com.authentication.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.authentication.R
import com.authentication.databinding.FragmentLoginBinding
import com.authentication.model.remote.IMAGE_URL_LOGIN
import com.authentication.view.activity.UserDetailActivity
import com.authentication.viewmodel.AuthenticationViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel by viewModels<AuthenticationViewModel>()

    val compositeDisposable = CompositeDisposable()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get()
            .load(IMAGE_URL_LOGIN)
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.img)
        binding.loginMain.visibility=View.VISIBLE
        binding.submit.setOnClickListener {
            val user = binding.edtUser.text.toString()
            val pass = binding.edtPass.text.toString()
            if (user.trim()==""||user.length<=3){
                Snackbar.make(it, resources.getText(R.string.user_is_empty), Snackbar.LENGTH_LONG).setBackgroundTint(
                    ContextCompat.getColor(requireActivity(),
                    R.color.purple_700))
                    .show()
            }
            else if(pass.trim()==""||user.length<=3){
                Snackbar.make(it, resources.getText(R.string.pass_is_empty), Snackbar.LENGTH_LONG).setBackgroundTint(
                    ContextCompat.getColor(requireActivity(),
                        R.color.purple_700))
                    .show()
            }else{
                binding.progressCircular.visibility=View.VISIBLE
                viewModel.login(user, pass)
                    .doFinally { binding.progressCircular.visibility=View.INVISIBLE }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : CompletableObserver {
                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                        }
                        override fun onComplete() {
                            requireActivity().startActivity(UserDetailActivity.newInstance(requireActivity()))
                            requireActivity().finish()
                        }
                        override fun onError(e: Throwable) {
                            if (e is HttpException) {
                                if (e.code()==401) {
                                        Snackbar.make(it, resources.getText(R.string.unauthorized), Snackbar.LENGTH_LONG).setBackgroundTint(
                                            ContextCompat.getColor(requireActivity(),
                                                R.color.purple_700))
                                            .show()
                                }
                            }else{
                                Snackbar.make(it, resources.getText(R.string.check_connection), Snackbar.LENGTH_LONG).setBackgroundTint(
                                    ContextCompat.getColor(requireActivity(),
                                        R.color.purple_700))
                                    .show()
                            }
                        }
                    })
            }
        }
    }


    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}