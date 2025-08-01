package com.example.bottomnavproj.ui.editinfo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bottomnavproj.R
import com.example.bottomnavproj.data.model.Record
import com.example.bottomnavproj.databinding.ActivityInfoEditBinding

class EditInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoEditBinding
    private var originalRecord: Record? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityInfoEditBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        @Suppress("DEPRECATION") // To suppress warning on API < 33
         originalRecord =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("record", Record::class.java)
            } else {
                intent.getParcelableExtra("record") // Deprecated, but still needed for old versions
            }


        if (originalRecord == null) {
            finish() // or show error
            return
        }

        binding.editInfoTitle.text = originalRecord!!.title
        binding.editDistanceInput.setText(originalRecord!!.distance)
        binding.editDateInput.setText(originalRecord!!.date)
        binding.editInfoSaveButton.setOnClickListener {
            saveUpdatedRecord()
        }
    }

    private fun saveUpdatedRecord() {
        val distance = binding.editDistanceInput.text.toString()
        val date = binding.editDateInput.text.toString()

        val updatedRecord = originalRecord?.copy(
            distance = distance,
            date = date
        )

        if (updatedRecord != null) {
            val resultIntent = Intent().apply {
                putExtra("updatedRecord", updatedRecord)
            }
            setResult(RESULT_OK, resultIntent)
        } else {
            setResult(RESULT_CANCELED)
        }

        finish()
    }
}