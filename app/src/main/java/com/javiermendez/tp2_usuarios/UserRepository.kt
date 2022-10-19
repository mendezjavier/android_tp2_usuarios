package com.javiermendez.tp2_usuarios

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class UserRepository {
    private val BASE_URL = "https://randomuser.me/"
    private val service: RandomUserApiService
    private lateinit var users: List<User>

    init {
        val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val converterMoshiRetrofit = MoshiConverterFactory.create(moshi)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterMoshiRetrofit)
            .build()

        service = retrofit.create(RandomUserApiService::class.java)
    }

    fun getLimitUsers(
        limit: Int,
        callbackResult: (List<User>) -> Unit,
        callbackError: (Throwable) -> Unit
    ) {
        Log.d("PARAMETER", limit.toString())
        service.getUsers(limit = limit.toString()).enqueue(object : Callback<RandomUserResponse> {
            override fun onResponse(
                call: Call<RandomUserResponse>,
                response: Response<RandomUserResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body == null) {
                        throw IllegalStateException("Error getting users")
                    }
                    Log.d("UserRepository  Body", body.toString())

                    users = body.results.map { randomUser -> mapRandomUserToUser(randomUser) }
                    callbackResult(users)
                }
            }

            override fun onFailure(call: Call<RandomUserResponse>, t: Throwable) {
                Log.d("UserRespository ERROR", call.toString())
                callbackError(t)
            }

        })
    }

    private fun mapRandomUserToUser(randomUser: RandomUser): User {
        return User(
            name = "${randomUser.name.first} ${randomUser.name.last}",
            age = randomUser.dob.age,
            email = randomUser.email,
            image = UserImage(
                small = randomUser.picture.thumbnail,
                large = randomUser.picture.large
            ),
            country = randomUser.location.country,
            telephone = randomUser.phone,
            location = Location(
                address = "${randomUser.location.street.name} ${randomUser.location.street.number}",
                latitude = randomUser.location.coordinates.latitude,
                longitude = randomUser.location.coordinates.longitude,
            )
        );
    }

    fun getUserByEmail(email: String): User? {
        return users.find { user -> user.email == email }
    }
}

