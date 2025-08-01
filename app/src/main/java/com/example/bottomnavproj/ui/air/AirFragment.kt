package com.example.bottomnavproj.ui.air

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.bottomnavproj.ui.editinfo.EditInfoActivity
import com.example.bottomnavproj.databinding.FragmentAirBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.fragment.app.viewModels
import com.example.bottomnavproj.data.model.Record

class AirFragment : Fragment() {

    private var _binding: FragmentAirBinding? = null

    private val binding get() = _binding!!

    private val viewModel: RecordListViewModel by viewModels()

    @Suppress("DEPRECATION") // To suppress warning on API < 33
    private val editInfoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedRecord =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("updatedRecord", Record::class.java)
                } else {
                    result.data?.getParcelableExtra("updatedRecord") // Deprecated, but still needed for old versions
                }
            updatedRecord?.let { viewModel.updateRecord(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Inflate the layout for the fragment
        _binding = FragmentAirBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecordListAdapter(
            onItemClick = { record ->
                val intent = Intent(requireContext(), EditInfoActivity::class.java)
                intent.putExtra("record", record)
                editInfoLauncher.launch(intent) },
            onDeleteClick = { record -> viewModel.removeRecord(record) }
        )

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.addMoreRecordButton.setOnClickListener {
            addMoreRecord()
        }
        // Observe data from ViewModel
        viewModel.items.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    private fun addMoreRecord() {
        viewModel.addNewRecord()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
