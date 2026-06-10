package edu.ucne.jorge_moya_ap2_p1.domain.usecase

data class ValidationResult(
    val isValid : Boolean,
    val error : String? = null
)

fun validateNombre (nombre : String) : ValidationResult{
    return when{
        nombre.isBlank() -> ValidationResult(false, "Nombre requerido")
        else -> ValidationResult(true)
    }
}

fun validateRazon (razon : String) : ValidationResult{
    return when{
        razon.isBlank() -> ValidationResult(false, "Razon requerida")
        else -> ValidationResult(true)
    }
}

fun validateMonto (monto : String) : ValidationResult{
    return when{
        monto.isBlank() -> ValidationResult(false, "Monto requerido")
        monto.toDouble() < 0 -> ValidationResult(false, "Monto no puede ser negativo")
        else -> ValidationResult(true)
    }
}