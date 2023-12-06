import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.copdapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WalkTestFragment : Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var stepCountTextView: TextView
    private lateinit var startStopFab: FloatingActionButton
    private var stepCount = 0
    private var testStarted = false
    private var timer: CountDownTimer? = null
    private val testDuration: Long = 6 * 60 * 1000 // 6 minutes in milliseconds

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.walk_test_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        progressBar = view.findViewById(R.id.circularProgressBar)
        stepCountTextView = view.findViewById(R.id.stepCountTextView)
        startStopFab = view.findViewById(R.id.startStopFab)

        startStopFab.setOnClickListener {
            if (testStarted) {
                stopTest()
            } else {
                startTest()
            }
        }

        resetTest()
    }

    private fun startTest() {
        testStarted = true
        stepCount = 0
        stepCountTextView.text = "Steps: 0"
        startStopFab.setImageResource(R.drawable.ic_test) // Replace with your stop icon

        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)

        timer = object : CountDownTimer(testDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished.toFloat() / testDuration * 100).toInt()
                progressBar.progress = 100 - progress
            }

            override fun onFinish() {
                stopTest()
            }
        }.start()
    }

    private fun stopTest() {
        testStarted = false
        startStopFab.setImageResource(R.drawable.ic_test) // Replace with your start icon
        sensorManager.unregisterListener(this)
        timer?.cancel()
        resetTest()
    }

    private fun resetTest() {
        progressBar.progress = 100
        stepCountTextView.text = "Steps: 0"
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (testStarted && event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            stepCount++
            stepCountTextView.text = "Steps: $stepCount"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // You can handle sensor accuracy changes here if needed
    }

    override fun onResume() {
        super.onResume()
        if (testStarted) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
