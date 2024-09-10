package anhddph36155.fpoly.searchaddress

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.substring
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableLiveData<List<AddressItem>>()
    val searchResult: LiveData<List<AddressItem>> get() = _searchResults

    private var searchJob: Job? = null

    val retrofit = Retrofit.Builder()
        .baseUrl("https://geocode.search.hereapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: HereApi = retrofit.create(HereApi::class.java)

    fun searchAddress(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000)
            val response = api.searchAddress(query)
            if (response.isSuccessful) {
                Log.d("SearchViewModel", "Response: ${response.body()}")
                _searchResults.postValue(response.body()?.items)
            } else {
                Log.e("SearchViewModel", "Error: ${response.errorBody()}")
            }
        }
    }
}

fun OpenGoogleMaps(address: String, context: Context) {
    val gmmIntentUri = Uri.parse("geo: 0,0?q=$address")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}

@Composable
fun HightlightedText(fullText: String, keyword: String) {
    val annotatedString = buildAnnotatedString {
        val startIndex = fullText.indexOf(keyword, ignoreCase = true)
        if (startIndex >= 0) {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Thin, color = Color.Gray)) {
                append(fullText.substring(0, startIndex))   //Thành phần trước từ khoá
            }

            append(fullText.substring(startIndex, startIndex + keyword.length))

            withStyle(style = SpanStyle(fontWeight = FontWeight.Thin, color = Color.Gray)) {
                append(fullText.substring(startIndex + keyword.length)) //Thành phần sau từ khoá
            }
        } else {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Thin, color = Color.Gray)) {
                append(fullText)
            }
        }
    }
    Text(text = annotatedString)
}