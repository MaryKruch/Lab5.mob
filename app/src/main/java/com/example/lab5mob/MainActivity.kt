import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.lcm

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.commandSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.commands,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> showInputDialog()
                    1 -> showCalcDialog()
                    2 -> showAboutDialog()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun showInputDialog() {
        val dialogView = layoutInflater.inflate(R.layout.input_dialog, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                //Save the data (Implement your saving logic here)
            }
            .setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()

        val calculateButton = dialogView.findViewById<Button>(R.id.calculateButton)
        calculateButton.setOnClickListener {
            val num1 = dialogView.findViewById<EditText>(R.id.number1Edit).text.toString().toIntOrNull()
            val num2 = dialogView.findViewById<EditText>(R.id.number2Edit).text.toString().toIntOrNull()
            val num3 = dialogView.findViewById<EditText>(R.id.number3Edit).text.toString().toIntOrNull()
            val summChecked = dialogView.findViewById<CheckBox>(R.id.summCheckBox).isChecked
            val lcmChecked = dialogView.findViewById<CheckBox>(R.id.lcmCheckBox).isChecked

            val results = calculateResults(num1, num2, num3, summChecked, lcmChecked)
            showCalcDialog(results)
            dialog.dismiss()
        }

    }

    private fun showCalcDialog(results: String = "") {
        AlertDialog.Builder(this)
            .setTitle("Results")
            .setMessage(results)
            .setPositiveButton("OK", null)
            .show()
    }


    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("About")
            .setMessage("Developed by: [Your Name]")
            .setPositiveButton("OK", null)
            .show()
    }


    private fun calculateResults(num1: Int?, num2: Int?, num3: Int?, summChecked: Boolean, lcmChecked: Boolean): String {
        var result = ""
        if (num1 != null && num2 != null && num3 != null) {
            if (summChecked) result += "Sum: ${num1 + num2 + num3}\n"
            if (lcmChecked && num1 > 0 && num2 > 0) result += "Least Common Multiple: ${lcm(num1, num2)}\n"
        } else {
            result = "Please enter valid numbers."
        }
        return result
    }
}
