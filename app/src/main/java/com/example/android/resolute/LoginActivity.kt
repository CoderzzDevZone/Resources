package com.example.android.resolute


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    companion object{
        private const val RC_Sign = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        createLoginUI()
    }


    private fun createLoginUI(){
        val providers = arrayListOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )

    startActivityForResult(
        AuthUI.getInstance().
                createSignInIntentBuilder().setAvailableProviders(providers).build()
        ,RC_Sign)
  }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_Sign){
            var response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                startActivity(Intent(this,MainActivity::class.java))
            }
            else{
                if(response == null){
                    finish()
                }
                if(response?.error?.errorCode == ErrorCodes.NO_NETWORK){
                    return
                }
                if(response?.error?.errorCode == ErrorCodes.UNKNOWN_ERROR){
                    Toast.makeText(this,response?.error?.errorCode.toString(),Toast.LENGTH_LONG)
                }
                    return
                }
            }
    }



}