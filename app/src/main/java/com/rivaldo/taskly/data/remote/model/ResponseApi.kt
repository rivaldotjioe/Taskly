package com.rivaldo.taskly.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseApi(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
