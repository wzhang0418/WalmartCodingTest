package com.zhang.walmartassessment.domain.dto

/**
 * Represents a country details.
 *
 * @property capital The name of the capital city of the country.
 * @property code The ISO code used to identify the country.
 * @property name The full name of the country.
 * @property region The geographical region to which the country.
 */
data class Country(
    val capital: String,
    val code: String,
    val name: String,
    val region: String
)
