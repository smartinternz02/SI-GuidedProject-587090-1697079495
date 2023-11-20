package com.example.snacksquad

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snacksquad.ui.theme.SnackSquadTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*



class AdminActivity : ComponentActivity() {
    private lateinit var orderDatabaseHelper: OrderDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderDatabaseHelper = OrderDatabaseHelper(this)


        setContent {
            SnackSquadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val data=orderDatabaseHelper.getAllOrders();
                    Log.d("swathi" ,data.toString())
                    val order = orderDatabaseHelper.getAllOrders()
                    ListListScopeSample(order)
                }
            }
        }
    }
}



@Composable
fun ListListScopeSample(order: List<Order>) {


        Image(
            painterResource(id = R.drawable.order), contentDescription = "",
            alpha = 0.5F,
            contentScale = ContentScale.FillHeight
        )
        Text(
            text = "Order Details",
            modifier = Modifier.padding(top = 24.dp, start = 106.dp, bottom = 24.dp),
            color = Color.Black,
            fontSize = 30.sp
        )
        // Create a sign-out button
        Box(
            modifier = Modifier
            .padding(8.dp)
        ) {
        SignOutButton()
        }


    Spacer(modifier = Modifier.height(30.dp))
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),

        horizontalArrangement = Arrangement.SpaceBetween
    ){
        item {

            LazyColumn {
                items(order) { order ->
                    Card(modifier = Modifier.padding(top = 16.dp, start = 48.dp, bottom = 20.dp)
                        .fillMaxWidth(), elevation = 4.dp, backgroundColor = Color.White, shape = MaterialTheme.shapes.medium) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Quantity: ${order.quantity}", fontSize = 18.sp)
                            Text("Address: ${order.address}", fontSize = 18.sp)
                        }
                    }
                }


            }
        }

    }
}

@Composable
fun SignOutButton() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clickable {
                    Toast.makeText(context, "Signing out", Toast.LENGTH_SHORT).show()
                    context.startActivity(
                        Intent(
                            context,
                            LoginActivity::class.java,
                        )
                    )
                }
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Sign Out"
            )
        }
    }
}
