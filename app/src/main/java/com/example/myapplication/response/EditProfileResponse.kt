package com.example.myapplication.response

import com.google.gson.annotations.SerializedName

data class EditProfileResponse(

	@field:SerializedName("message")
	val message: String? = null
)