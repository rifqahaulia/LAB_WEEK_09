package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                // ✅ sesuai modul: Surface membungkus Home() tanpa parameter
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

// ✅ Data Model untuk user input
data class Student(
    var name: String
)

// ✅ Parent Composable: mengatur state dan event handler
@Composable
fun Home() {
    // State list untuk menyimpan data student
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    // State untuk field input
    var inputField by remember { mutableStateOf(Student("")) }

    // Event handler untuk perubahan input
    val onInputValueChange: (String) -> Unit = { newValue ->
        inputField = Student(newValue)
    }

    // Event handler untuk tombol submit
    val onButtonClick: () -> Unit = {
        if (inputField.name.isNotBlank()) {
            listData.add(Student(inputField.name))
            inputField = Student("") // reset field setelah submit
        }
    }

    // Panggil child composable
    HomeContent(
        listData = listData,
        inputField = inputField,
        onInputValueChange = onInputValueChange,
        onButtonClick = onButtonClick
    )
}

// ✅ Child Composable: menampilkan isi halaman
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn {
        // Bagian input dan tombol
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ✅ Gunakan UI Element dari Elements.kt
                OnBackgroundTitleText(
                    text = stringResource(id = R.string.enter_item)
                )

                // ✅ TextField untuk input
                TextField(
                    value = inputField.name,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = {
                        onInputValueChange(it)
                    }
                )

                // ✅ Gunakan tombol custom dari Elements.kt
                PrimaryTextButton(
                    text = stringResource(id = R.string.button_click)
                ) {
                    onButtonClick()
                }
            }
        }

        // ✅ Tampilkan list data student
        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ✅ Gunakan UI Element custom untuk teks item
                OnBackgroundItemText(text = item.name)
            }
        }
    }
}

// ✅ Preview (opsional)
@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        Home()
    }
}
