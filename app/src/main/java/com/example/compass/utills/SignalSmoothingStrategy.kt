package com.example.compass.utills

interface SignalSmoothingStrategy{
    fun compute(data:Float):Double
}