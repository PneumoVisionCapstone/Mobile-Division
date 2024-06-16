package com.example.myapplication.ui.profile

import EditViewModel
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.myapplication.R
import com.example.myapplication.ViewModelFactory
import com.example.myapplication.preference.UserModel
import com.example.myapplication.retrofit.ApiConfig
import com.example.myapplication.response.EditProfileResponse
import com.example.myapplication.response.ProfileResponse
import com.example.myapplication.ui.login.LoginActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class EditActivity : AppCompatActivity() {

    private lateinit var editEmail: EditText
    private lateinit var editName: EditText
    private lateinit var saveButton: Button
    private lateinit var btnBack: ImageView
    private lateinit var profileImageView: ImageView
    private lateinit var edImgProfile: ImageView
    private var imageUri: Uri? = null

    private val viewModel: EditViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            profileImageView.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        editEmail = findViewById(R.id.editEmail)
        editName = findViewById(R.id.editName)
        saveButton = findViewById(R.id.saveButton)
        btnBack = findViewById(R.id.btnBack)
        profileImageView = findViewById(R.id.profileImageView)
        edImgProfile = findViewById(R.id.ed_img_profile)

        edImgProfile.setOnClickListener {
            getImage.launch("image/*")
        }

        viewModel.getSession().observe(this) { user ->
            if (user == null || !user.isLogin) {
                Log.e("EditActivity", "Current user is null or not logged in")
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                editEmail.setText(user.email)
                editName.setText(user.name)

                // Load the current profile image
                fetchProfile(user.token)

                saveButton.setOnClickListener {
                    val newEmail = editEmail.text.toString().trim()
                    val newName = editName.text.toString().trim()
                    updateUserProfile(user.token, newName, imageUri)
                }

                btnBack.setOnClickListener {
                    finish()
                }
            }
        }
    }

    private fun fetchProfile(token: String) {
        val apiService = ApiConfig.getApiService()
        apiService.getProfile("Bearer $token").enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful) {
                    val profile = response.body()
                    profile?.profilePicture?.let { profileUrl ->
                        if (!isDestroyed && !isFinishing) {
                            Glide.with(this@EditActivity)
                                .load(profileUrl)
                                .into(profileImageView)
                        }
                    }
                } else {
                    Log.e(ContentValues.TAG, "Failed to fetch profile: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "Error fetching profile", t)
            }
        })
    }


    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.profile) // Placeholder image
            .error(R.drawable.profile) // Error image
            .transform(CircleCrop())
            .into(profileImageView)
    }

    private fun updateUserProfile(token: String, name: String, profileImageUri: Uri?) {
        lifecycleScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val namePart = createPartFromString(name.ifEmpty { viewModel.currentUserName })
                val profileImagePart = profileImageUri?.let { prepareFilePart("profile_picture", it) }
                val params = mapOf("name" to namePart)

                val response = apiService.updateProfile("Bearer $token", params, profileImagePart)
                if (response.isSuccessful) {
                    val successResponse = response.body()?.message
                    successResponse?.let {
                        val imageUrl = it
                        showToast("Profile updated successfully")
                        if (!isDestroyed && !isFinishing) {
                            Glide.with(this@EditActivity)
                                .load(imageUrl)
//                                .transform(CircleCrop())
                                .into(profileImageView)
                        }
                    }
                    viewModel.saveSession(UserModel(viewModel.currentUserEmail, token, true, name, viewModel.currentUserId))
                    showToast(getString(R.string.success_profile_update))
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, EditProfileResponse::class.java)
                    errorResponse.message?.let { showToast(it) }
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, EditProfileResponse::class.java)
                errorResponse.message?.let { showToast(it) }
            } catch (e: Exception) {
                showToast("Error: ${e.message}")
            }
        }
    }


    private fun createPartFromString(value: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
    }

    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
        val file = createTempFileFromUri(fileUri)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    private fun createTempFileFromUri(uri: Uri): File {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("temp", null, cacheDir)
        inputStream.use { input ->
            FileOutputStream(tempFile).use { output ->
                input?.copyTo(output)
            }
        }
        return tempFile
    }

    private fun showToast(message: String) {
        Toast.makeText(this@EditActivity, message, Toast.LENGTH_SHORT).show()
    }
}
