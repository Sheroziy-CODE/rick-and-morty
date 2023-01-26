import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import rick_and_morty.data.model.CharacterResultsDto

@Composable
fun CharacterRow(characterResultsDto: CharacterResultsDto) {
    val imagerPainter = rememberImagePainter(data = characterResultsDto.image)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .border(border = BorderStroke(width = 1.dp, color = Color.White)),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = imagerPainter,
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit)
            Column (
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text (
                    text = characterResultsDto.name,
                    fontSize = 30.sp,
                    color = Color.White)
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = "Last known location: \n${characterResultsDto.locationDto?.name}",
                    fontSize = 15.sp,
                    color = Color.White)
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text (
                    text = "Species: \n${characterResultsDto.species}",
                    fontSize = 15.sp,
                    color = Color.White)
            }
        }
    }
}