package com.example.recipeapp.enums

enum class MeasuringUnit(val stringify: String) {
    GRAM("g"),
    KILOGRAM("kg"),
    TABLESPOON("tp"),
    EATINGSPOON("el"),
    LITER("L"),
    MILLILITER("mL"),
    KNIFETIP("kt"),
    PACK("P"),
    CAN("c"),
    PINCH("p"),
    NONE("");

    companion object {
        fun convertToList() = listOf<MeasuringUnit>(
            NONE,
            GRAM,
            KILOGRAM,
            LITER,
            MILLILITER,
            TABLESPOON,
            EATINGSPOON,
            KNIFETIP,
            PACK,
            PINCH,
            CAN
        )
    }
}