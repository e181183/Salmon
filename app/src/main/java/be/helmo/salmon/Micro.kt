package be.helmo.salmon

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.io.File

class Micro(private val context: Context) {

    companion object {
        private val REQUIRED_MIC_PERMISSION =
            mutableListOf(
                Manifest.permission.RECORD_AUDIO
            ).toTypedArray()
    }

    private var mediaRecorder: MediaRecorder? = null

    private var mediaPlayer1 = MediaPlayer()
    private var mediaPlayer2 = MediaPlayer()
    private var mediaPlayer3 = MediaPlayer()
    private var mediaPlayer4 = MediaPlayer()

    private var isRecording: Boolean = false

    fun getIsRecording() : Boolean {
        return isRecording
    }

    fun playAudio(mediaPlayer : MediaPlayer, buttonId : Int) {
        try {
            resetMediaPlayer(buttonId)
            mediaPlayer!!.setDataSource(getRecordingFilePath(buttonId))
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } catch (exc: Exception) {
            Toast.makeText(context, "fail play", Toast.LENGTH_SHORT).show()
        }
    }

    fun choseMediaPlayer(buttonId: Int): MediaPlayer {
        return when (buttonId) {
            1 -> mediaPlayer1
            2 -> mediaPlayer2
            3 -> mediaPlayer3
            else -> mediaPlayer4
        }
    }

    private fun resetMediaPlayer(buttonId: Int) {
        when (buttonId) {
            1 -> mediaPlayer1 = MediaPlayer()
            2 -> mediaPlayer2 = MediaPlayer()
            3 -> mediaPlayer3 = MediaPlayer()
            else -> mediaPlayer4 = MediaPlayer()
        }
    }

    fun recordAudio(buttonId : Int) {
        try {
            isRecording = true
            mediaRecorder = MediaRecorder()
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder!!.setOutputFile(getRecordingFilePath(buttonId))
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()
            Toast.makeText(context, "recording", Toast.LENGTH_SHORT)
        } catch (e: Exception) {
            Toast.makeText(context, "recording failed", Toast.LENGTH_SHORT)
            isRecording = false
        }
    }

    fun stopRecord() {
        mediaRecorder!!.stop()
        mediaRecorder!!.release()
        mediaRecorder = null

        isRecording = false
        Toast.makeText(context, "recording is stopped", Toast.LENGTH_SHORT).show()
    }

    private fun getRecordingFilePath(buttonId: Int) : String{
        val contextWrapper = ContextWrapper(context)
        val musicDir = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(musicDir, "testRecordingFile$buttonId.mp3")
        return file.path
    }

    fun microPermissionsGranted() = REQUIRED_MIC_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            context, it
        ) == PackageManager.PERMISSION_GRANTED
    }
}