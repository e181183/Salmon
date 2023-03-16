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
import be.helmo.salmon.viewModel.SalmonButtonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class Sound(private val context: Context, private val buttonViewmodel : SalmonButtonViewModel) {

    companion object {
        private var isMute: Boolean = false
    }

    private var mediaRecorder: MediaRecorder? = null

    private var mediaPlayer = MediaPlayer()

    private lateinit var defaultMp : MediaPlayer

    private var isRecording: Boolean = false

    fun getIsRecording() : Boolean {
        return isRecording
    }

    fun getIsMute() : Boolean {
        return isMute
    }

    fun setIsMute(boolean: Boolean) {
        isMute = boolean
    }

    fun playAudio(buttonId : Int) {
        if (!isMute) {
            GlobalScope.launch(Dispatchers.IO) {
                if (buttonViewmodel.getSoundPath(buttonId) != null) {
                    try {
                        mediaPlayer = MediaPlayer()
                        mediaPlayer!!.setDataSource(buttonViewmodel.getSoundPath(buttonId))
                        mediaPlayer!!.prepare()
                        mediaPlayer!!.start()
                    } catch (exc: Exception) {
                        Toast.makeText(context, "fail play", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    when (buttonId) {
                        1 ->  defaultMp = MediaPlayer.create(context, R.raw.son_defaut_rouge)
                        2 ->  defaultMp = MediaPlayer.create(context, R.raw.son_defaut_vert)
                        3 ->  defaultMp = MediaPlayer.create(context, R.raw.son_defaut_bleu)
                        else ->  defaultMp = MediaPlayer.create(context, R.raw.son_defaut_jaune)
                    }
                    defaultMp.start()
                }
            }
        }
    }

    fun resetAllSounds() {
        GlobalScope.launch(Dispatchers.IO) {
            buttonViewmodel.resetSounds()
        }
    }

    fun recordAudio(buttonId : Int) {
        if (!isRecording) {
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
        } else {
            Toast.makeText(context, "already recording", Toast.LENGTH_SHORT)
        }
    }

    fun stopRecord(buttonId : Int) {
        mediaRecorder!!.stop()
        mediaRecorder!!.release()
        mediaRecorder = null

        GlobalScope.launch(Dispatchers.IO) {
            if (buttonViewmodel.isButtonStored(buttonId) != 0) {
                buttonViewmodel.setSoundPath(buttonId, getRecordingFilePath(buttonId))
            } else {
                buttonViewmodel.addButtonToDb(buttonId, null, getRecordingFilePath(buttonId))
            }
        }
        isRecording = false
        Toast.makeText(context, "recording is stopped", Toast.LENGTH_SHORT).show()
    }

    private fun getRecordingFilePath(buttonId: Int) : String{
        val contextWrapper = ContextWrapper(context)
        val musicDir = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(musicDir, "salmonButtonSound_$buttonId.mp3")
        return file.path
    }

    fun microPermissionsGranted() : Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }

}