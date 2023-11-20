package com.example.snacksquad

import androidx.room.*

@Dao
interface OrderDao {

    @Query("SELECT * FROM order_table WHERE  address= :address")
    suspend fun getOrderByAddress(address: String): Order?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("DELETE FROM order_table WHERE id = :orderId")
    suspend fun deleteOrderById(orderId: Long)


}