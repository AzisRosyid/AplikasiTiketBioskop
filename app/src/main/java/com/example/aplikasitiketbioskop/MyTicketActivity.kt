package com.example.aplikasitiketbioskop

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.aplikasitiketbioskop.api.ApiRetrofit
import com.example.aplikasitiketbioskop.model.Movie
import com.example.aplikasitiketbioskop.model.Ticket
import com.example.aplikasitiketbioskop.ui.theme.AplikasiTiketBioskopTheme
import retrofit2.awaitResponse

class MyTicketActivity : ComponentActivity() {
    private val api by lazy { ApiRetrofit().apiEndPoint }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikasiTiketBioskopTheme {
                Column {
                    CustomTopBar()
                    TicketGrid()
                }
            }
        }
    }

    @Composable
    private fun TicketGrid() {
        var ticket by remember { mutableStateOf<List<Ticket>>(emptyList()) }

        LaunchedEffect(key1 = Unit) {
            getTicket()?.let {
                ticket = it
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.padding(16.dp)
        ) {
            items(ticket) { ticket ->
                TicketCard(ticket = ticket)
            }
        }
    }

    private suspend fun getTicket(): List<Ticket>? {
        return try {
            val response = api.getTicket().awaitResponse()
            if (response.isSuccessful) {
                response.body()?.tickets
            } else {
                // Handle error
                null
            }
        } catch (e: Exception) {
            // Handle exception
            null
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview2() {
        AplikasiTiketBioskopTheme {
            Column {
                CustomTopBar()
                TicketGrid()
            }
        }
    }

}

@Composable
private fun TicketCard(ticket: Ticket) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = ShapeDefaults.Medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    val intent = Intent(context, MovieDetailActivity::class.java)
                    intent.putExtra("ticket", ticket)
                    context.startActivity(intent)
                }
        ) {

            var model: Any = R.drawable.baseline_movie_24
            if (!ticket.movie_image.isNullOrEmpty()) {
                model = "${Helper.BASE_IMAGE}${ticket.movie_image}"
            }

            Row {
                AsyncImage(
                    model = model,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = ticket.movie_title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(text = "Date: ${ticket.date}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Time: ${ticket.start_time} - ${ticket.end_time}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun CustomTopBar() {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val isWideScreen = screenWidthDp > 600.dp

    val topBarHeight = if (isWideScreen) 110.dp else 50.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(topBarHeight)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "Tiket Saya",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}