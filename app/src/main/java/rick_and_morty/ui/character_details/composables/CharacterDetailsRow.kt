package rick_and_morty.ui.character_details.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import rick_and_morty.ui.character_details.CharacterDetails

@Composable
fun CharacterDetailsRow(
    characterDetails: CharacterDetails,
    modifier: Modifier = Modifier
) {
    val imagePainter = rememberImagePainter(data = characterDetails.image)
    val imageSize by animateFloatAsState(
        targetValue = 0.5f,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 50,
            easing = FastOutSlowInEasing
        )
    )

    Column(
        modifier
            .fillMaxWidth()
            .height(350.dp)
            .border(border = BorderStroke(width = 2.dp, color = Color.White)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier
                .fillMaxWidth()
                .fillMaxHeight(imageSize),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier.padding(top = 10.dp))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(characterDetails.characterShortDetailsList) { characterDetailsList ->
                RowComponent(spaceBetween = 5.dp) {
                    Text(
                        text = characterDetailsList.title+": ",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = characterDetailsList.info,
                        fontSize = 15.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}
