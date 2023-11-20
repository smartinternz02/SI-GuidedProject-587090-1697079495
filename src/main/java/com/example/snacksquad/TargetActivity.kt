package com.example.snacksquad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp

import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.snacksquad.ui.theme.SnackSquadTheme

import com.example.snacksquad.ui.theme.SnackSquadTheme

class TargetActivity : ComponentActivity() {
    private lateinit var orderDatabaseHelper: OrderDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderDatabaseHelper = OrderDatabaseHelper(this)
        setContent {
            SnackSquadTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xE2F3DD70))

                ) {
                    Order(this, orderDatabaseHelper)
                    val orders = orderDatabaseHelper.getAllOrders()
                    Log.d("swathi", orders.toString())

                }
            }
        }
    }
}

@Composable
fun Order(context: Context, orderDatabaseHelper: OrderDatabaseHelper){
    var isOrderPlacedAnimationVisible by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xE2F3DD70)),
        contentAlignment = Alignment.TopEnd // Place the content in the top-right corner
    ) {
        // Create a sign-out button
        IconButton(
            onClick = {
                Toast.makeText(context, "Signing out", Toast.LENGTH_SHORT).show()
                // Handle sign-out here
                context.startActivity(
                    android.content.Intent(
                        context,
                        LoginActivity::class.java,
                    ),
                )
            },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Sign Out"
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        val mContext = LocalContext.current
        var quantity by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }
        var error by remember { mutableStateOf("") }



        TextField(value = quantity, onValueChange = {quantity=it},
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(10.dp)
                .width(280.dp))

        Spacer(modifier = Modifier.padding(10.dp))

        TextField(value = address, onValueChange = {address=it},
            label = { Text("Address") },
            modifier = Modifier
                .padding(10.dp)
                .width(280.dp))

        Spacer(modifier = Modifier.padding(10.dp))



        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }


        Button(onClick = {
            if( quantity.isNotEmpty() and address.isNotEmpty()){
                val order = Order(
                    id = null,
                    quantity = quantity,
                    address = address,


                )
                orderDatabaseHelper.insertOrder(order)

                // Show the custom animation after placing the order successfully
                isOrderPlacedAnimationVisible = true

                // You can add a delay and then hide the animation if needed
                Handler(Looper.getMainLooper()).postDelayed({
                    isOrderPlacedAnimationVisible = false
                }, 20000) // 2000 milliseconds (2 seconds) delay as an example
            }
        },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White))
        {
            Text(text = "Order Place", color = Color.Black)
        }
        OrderPlacedAnimation(
            isVisible = isOrderPlacedAnimationVisible,
            onDismiss = {
                // You can add any actions you need when the animation is dismissed
                isOrderPlacedAnimationVisible = false
            },
            onOkClick = {
                showRatingDialog(context)
            }


        )

    }


    }


@Composable
fun OrderPlacedAnimation(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
    ) {
        // Content of the animation
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Order Placed Successfully!",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onOkClick()

                }
            ) {
                Text(text = "OK")
            }
        }
    }
}

private fun startMainPage(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    ContextCompat.startActivity(context, intent, null)
}

private fun showRatingDialog(context: Context) {
    val ratingDialog = RatingDialog(context)
    ratingDialog.showRatingDialog()
}
