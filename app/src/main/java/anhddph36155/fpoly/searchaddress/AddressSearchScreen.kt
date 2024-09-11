package anhddph36155.fpoly.searchaddress

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressSearchScreen(viewModel: SearchViewModel) {
    var query by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResult.observeAsState(listOf())
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(0.dp, Color.White, shape = RoundedCornerShape(20.dp)),
            value = query,
            onValueChange = { newText ->
                query = newText
                viewModel.searchAddress(query)
            },
            placeholder = { Text(text = "Enter keyword")},
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,  // Xóa viền khi focus
                unfocusedIndicatorColor = Color.Transparent  // Xóa viền khi không focus
            ),
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.search),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painterResource(id = R.drawable.close),
                            contentDescription = null
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn {
            items(searchResults) { address ->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement =Arrangement.SpaceEvenly
                    ){
                        Icon(
                            painterResource(id = R.drawable.location),
                            contentDescription = null
                        )
                        Box(modifier = Modifier.weight(1f)){
                            HightlightedText(fullText = address.address.label, keyword = query)
                        }
                        IconButton(
                            onClick = { OpenGoogleMaps(address.address.label, context) },
                            modifier = Modifier
                                .size(20.dp)
                        ) {
                            Icon(
                                painterResource(id = R.drawable.right), //Đoạn này em không hiểu sao icon không hiển thị =((
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}