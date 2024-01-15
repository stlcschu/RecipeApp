package com.example.recipeapp.dataclasses

data class StepTimer(
    val name : String,
    val timeInSeconds : Int

) {
    override fun toString() : String {
        return if(timeInSeconds == 180) "3:00 H" else if (timeInSeconds >= 120) "2:${timeInSeconds - 120} H" else if(timeInSeconds >= 60) "1:${timeInSeconds - 60} H" else "0:${timeInSeconds} H"
    }

    fun toString2() : String {
        return if(timeInSeconds == 180) "3:00 H" else if (timeInSeconds >= 120) "2:${timeInSeconds - 120} H" else if(timeInSeconds >= 60) "1:${timeInSeconds - 60} H" else "${timeInSeconds} M"
    }
}
