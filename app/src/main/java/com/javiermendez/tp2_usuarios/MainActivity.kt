package com.javiermendez.tp2_usuarios

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var loader: ProgressBar
    private lateinit var usersList: RecyclerView
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var userRepository: UserRepository
    private lateinit var userClick: (User) -> Unit
    private val INTERNET_REQUEST_CODE = 666


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loader = findViewById(R.id.loader)
        usersList = findViewById(R.id.users_list)
        swipe = findViewById(R.id.swipe)

        userRepository = UserRepository()

        userClick = { user: User ->
            Log.d("OPEN USER", user.toString())
            val intent = Intent(this, UserDetalle::class.java)
//            TODO: SET OBJECT TO EXTRA
            intent.putExtra("email", user.email)
            intent.putExtra("name", user.name)
            intent.putExtra("phone", user.telephone)
            intent.putExtra("image", user.image.large)
            intent.putExtra("address", user.location.address)
            startActivity(intent)
        }
        usersAdapter = UsersAdapter(this, userClick)
        usersList.adapter = usersAdapter
        checkAppPermissions()

        swipe.setOnRefreshListener {
            swipe.isRefreshing = true
            getUsers()
        }
        getUsers()
    }


    private fun checkAppPermissions() {
        if (!checkPermission(Manifest.permission.INTERNET)) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET),
                INTERNET_REQUEST_CODE
            )
        } else {
            Log.d("Internet:", "Permission granted")
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return PermissionChecker.checkSelfPermission(
            this,
            permission
        ) == PermissionChecker.PERMISSION_GRANTED
    }

    private fun getUsers() {
        loader.visibility = View.VISIBLE
        usersList.visibility = View.GONE

        userRepository.getLimitUsers(50,
            callbackError = { t -> showError(t) },
            callbackResult = { users -> showUsers(users) })
    }


    private fun showUsers(users: List<User>) {
        Log.d("USERS LIST", users.toString())
        usersAdapter.setUsers(users)

        swipe.isRefreshing = false
        loader.visibility = View.GONE
        usersList.visibility = View.VISIBLE
    }

    private fun showError(error: Throwable) {
        Log.e("GET USER ERROR", "No se pudo obtener los usuarios", error)
        Toast.makeText(this, "No se pudo obtener los usuarios", Toast.LENGTH_LONG).show()
    }

}