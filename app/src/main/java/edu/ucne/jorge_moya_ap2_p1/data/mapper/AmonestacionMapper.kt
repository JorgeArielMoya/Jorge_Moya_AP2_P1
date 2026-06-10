package edu.ucne.jorge_moya_ap2_p1.data.mapper

import edu.ucne.jorge_moya_ap2_p1.data.local.entity.AmonestacionEntity
import edu.ucne.jorge_moya_ap2_p1.domain.model.Amonestacion

fun AmonestacionEntity.toDomain() : Amonestacion = Amonestacion(
    amonestacionId = amonestacionId,
    nombres = nombres,
    razon = razon,
    monto = monto
)

fun Amonestacion.toEntity() : AmonestacionEntity = AmonestacionEntity(
    amonestacionId = amonestacionId,
    nombres = nombres,
    razon = razon,
    monto = monto
)