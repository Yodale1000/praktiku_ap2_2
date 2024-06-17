enum class Priority {
    HIGH,
    MEDIUM,
    LOW;

    companion object {
        fun fromFactor(factor: Double): Priority {
            return when (factor.toInt()) {
                1 -> HIGH
                2 -> MEDIUM
                3 -> LOW
                else -> throw IllegalArgumentException(" Ungültiger Faktor : $factor ")
            }
        }
    }
}


interface Prioritizable {
    fun prioritize(): Double
}