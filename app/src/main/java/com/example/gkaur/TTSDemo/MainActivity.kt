package com.example.gkaur.TTSDemo

import android.app.Activity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

import java.util.Locale


class MainActivity : Activity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        send.setOnClickListener {
            if (tts != null)
                speakOut()
        }
        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                tts!!.language = Locale.US
                speakOut()
            } else {
                Log.e("TTS", "Initilization Failed!")
            }
        })

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {

            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported")
            } else {
                speakOut()
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }


    private fun speakOut() {
        val text = "Hello ! welcome to the demo"

        //to demonstrate "no support of SSML in googleTTS"
        val textWithSSML = "<?xml version=\"1.0\"?>" +
                "<speak version=\"1.0\" xmlns=\"http://www.w3.org/2001/10/synthesis\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xsi:schemaLocation=\"http://www.w3.org/2001/10/synthesis " +
                "http://www.w3.org/TR/speech-synthesis/synthesis.xsd\" " +
                "xml:lang=\"en-US\">" +
                "<break time=\"2s\"/>" +
                "Step 1, take a deep breath. <break time=\"2s\"/>" + //break time works

                " Step 2, exhale." +
                "Step 3, take a deep breath again. <break strength=\"strong\"/>" + //ssml not working

                "Step 4, exhale." +

                "</speak>"

        //to demonstrate "no support of SSML in googleTTS"
        val textWithSSMLaudioTag = "<?xml version=\"1.0\"?>" +
                "<speak version=\"1.0\" xmlns=\"http://www.w3.org/2001/10/synthesis\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xsi:schemaLocation=\"http://www.w3.org/2001/10/synthesis " +
                "http://www.w3.org/TR/speech-synthesis/synthesis.xsd\" " +
                "xml:lang=\"en-US\">" +
                "<break time=\"2s\"/>" +
                "  <audio src=\"https://actions.google.com/sounds/v1/animals/cat_purr_close.ogg\"\">\n" +
                "    <desc>a cat purring</desc>\n" +
                "    PURR (sound didn't load)\n" +
                "  </audio>\n" +

                "</speak>"
        /*String text = "<?xml version=\"1.0\"?>" +
                "<speak version=\"1.0\" xmlns=\"http://www.w3.org/2001/10/synthesis\" " +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xsi:schemaLocation=\"http://www.w3.org/2001/10/synthesis " +
                "http://www.w3.org/TR/speech-synthesis/synthesis.xsd\" " +
                "xml:lang=\"en-US\">" +

               "<spurt audio=\"g0001_004\">cough</spurt>, excuse me, <spurt audio=\"g0001_018\">err</spurt>"+

                "</speak>";*/

        tts!!.speak(text, TextToSpeech.QUEUE_ADD, null)
        tts!!.speak(textWithSSML, TextToSpeech.QUEUE_ADD, null) // not working
        tts!!.speak(textWithSSMLaudioTag, TextToSpeech.QUEUE_ADD, null) //not working
    }

    public override fun onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}
