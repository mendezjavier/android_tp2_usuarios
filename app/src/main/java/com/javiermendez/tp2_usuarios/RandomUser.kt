package com.javiermendez.tp2_usuarios

data class RandomUser(
    val gender: String,
    val name: UserName,
    val location: UserLocation,
    val email: String,
    val phone: String,
    val cell: String,
    val id: UserId,
    val picture: UserPicture,
    val nat: String,
    val dob: UserDob
)

data class UserDob(
    val date: String,
    val age: Int
)

data class UserName(
    val title: String,
    val first: String,
    val last: String
)

data class UserLocation(
    val street: LocationStreet,
    val city: String,
    val state: String,
    val country: String,
//    val postcode: Int,
    val coordinates: LocationCoordinates,
    val timezone: LocationTimezone
)

data class LocationStreet(
    val number: Int,
    val name: String
)

data class LocationCoordinates(
    val latitude: String,
    val longitude: String
)

data class LocationTimezone(
    val offset: String,
    val description: String
)

data class UserId(
    val name: String,
    val value: String?
)

data class UserPicture(
    val large: String,
    val medium: String,
    val thumbnail: String
)