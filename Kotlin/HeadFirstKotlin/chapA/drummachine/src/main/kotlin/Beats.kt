import java.io.File
import javax.sound.sampled.AudioSystem
import kotlinx.coroutines.*

suspend fun playBeats(beats: String, file: String) {
    for (i in 0..beats.length - 1) {
        delay(100)
        if (beats[i] == 'x') {
            playSound(file)
        }
    }
}

fun playSound(file: String) {
    val clip = AudioSystem.getClip()
    val audioInputStream = AudioSystem.getAudioInputStream(
        File(
            file
        )
    )
    clip.open(audioInputStream)
    clip.start()
}

suspend fun main() {
    runBlocking {
        launch { playBeats("x-x-x-x-x-x-", "toms.aiff") }
        playBeats("x-----x-----", "crash_cymbal.aiff")
    }
}