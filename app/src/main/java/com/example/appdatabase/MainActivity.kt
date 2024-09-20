package com.example.appdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.appdatabase.roomDB.PessoaDataBase
import com.example.appdatabase.ui.theme.AppDatabaseTheme
import com.example.appdatabase.viewModel.PessoaViewModel
import com.example.appdatabase.viewModel.Repository

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            PessoaDataBase::class.java,
            "pessoa.db"
        ).build()
    }

    private val viewModel by viewModels<PessoaViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PessoaViewModel(Repository(db)) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppDatabaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(viewModel, this)
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppDatabaseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            App()
        }
    }
}
@Composable
fun App(viewModel: PessoaViewModel, mainActivity: MainActivity) {
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.padding(20.dp)) {
        }
        Row {
            Text(
                text = "App Database",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black)
        }
        Row {
            TextField(
                TextFieldName = "Nome",
                value = nome,
                onValueChange = nome)
        }
        Row {
            TextField(
                TextFieldName = "Idade",
                value = telefone,
                onValueChange = telefone)
        }
        Row(Modifier.padding(20.dp)) {
        }
        Row {
            Button(
                onClick = {
                    viewModel.upsertPessoa(pessoa)
                    nome = ""
                    telefone = ""
                }
            ) {
                Text(text = "Cadastrar")
            }
        }
    }
}
@Composable
fun TextField(TextFieldName: String, value: String, onValueChange: String) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
        },
        label = { Text(text = TextFieldName) },
        placeholder = { Text(text = "") },
    )
}
