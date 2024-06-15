import androidx.lifecycle.*
import com.example.myapplication.preference.UserModel
import com.example.myapplication.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel(private val repository: UserRepository): ViewModel() {

    private var _currentUser: UserModel? = null

    init {
        viewModelScope.launch {
            _currentUser = repository.getSession().first()
        }
    }

    val currentUserEmail: String get() = _currentUser?.email ?: ""
    val currentUserName: String get() = _currentUser?.name ?: ""
    val currentUserId: String get() = _currentUser?.userId ?: ""

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}
