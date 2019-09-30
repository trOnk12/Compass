package com.example.compass.utills

interface SignalSmoothingStrategy{
    fun compute(dataInput:FloatArray,dataOutput:FloatArray):FloatArray
}