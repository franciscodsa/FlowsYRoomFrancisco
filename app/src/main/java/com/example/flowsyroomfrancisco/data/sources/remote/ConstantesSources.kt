package com.example.flowsyroomfrancisco.data.sources.remote

object ConstantesSources {
    const val noData = "No data"
    const val unknownError = "Unknown error"
    const val error = "Error"

    //OrderService
    const val id = "id"
    const val  ordersCustomers ="orders/customer"
    const val  orders ="orders"
    const val  deleteOrder = "orders/{id}"

    //CustomerService
    const val customers = "customers"
    const val customersPathId="customers/{id}"
    const val deleteCustomer ="customers/{id}"

    //NetworkModule
    const val urlsBase = "http://192.168.1.131:8080/PracticaLoginRegistro-1.0-SNAPSHOT/api/"

}