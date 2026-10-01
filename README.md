# Laboratorio-SOLID
Laboratorio numero 2 de la matera de ingenieria de software

## Recorrido del laboratorio

  | Bloque | Qué van a hacer |
  |---|---|
  | 0. Arranque | Preparar el proyecto y conocer el dominio |
  | 1. Diagnóstico | Encontrar los problemas y medir el “antes” |
  | 2. Refactorización | Corregir el sistema con puntos de control S, O, L, I, D |
  | 3. Pruebas unitarias | Probar el diseño nuevo con dobles de prueba |
  | 4. Negocio pidió cambios | Implementar requerimientos que no conocían |
  | 5. Revisión cruzada | Extender el código de otra pareja |
  | 6. Cierre | UML final, comparación y reflexión |

## Reglas de trabajo

  1. Lenguaje libre. Pueden traducir el código a Kotlin, C#, Python, TypeScript, Dart, Swift,
  Go, etc. La traducción debe conservar los problemas de diseño: no los corrijan al traducir.
  Usen un solo lenguaje durante todo el laboratorio.
  2. Git obligatorio. Creen un repositorio y hagan un commit al final de cada bloque y de
  cada punto de control con los nombres indicados en esta guía. El historial es parte de la
  evaluación.
  3. El comportamiento no cambia. Refactorizar es mejorar el diseño sin cambiar lo que el
  programa hace. La salida del programa principal debe ser la misma antes y después (salvo la
  fecha y hora de auditoría).
  4. No hay una única solución correcta. Se evalúa que sus decisiones estén justificadas, no
  que coincidan con las del docente.

## Glosario bancario
  
  | Término | Significado en este laboratorio |
  |---|---|
  | **Cuenta de ahorros** | Cuenta en la que el cliente deposita, retira y transfiere dinero libremente. |
  | **CDT** | Certificado de Depósito a Término. El cliente deja un dinero “congelado” hasta una fecha de vencimiento a cambio de intereses. No permite retiros antes de esa fecha. |
  | **Cuota de manejo** | Valor mensual que el banco cobra por mantener una cuenta. |
  | **Transferencia interbancaria** | Transferencia hacia una cuenta de otro banco. Tiene comisión. |
  | **Avance** | Retiro de efectivo con cargo al cupo de una tarjeta de crédito. |
  | **Extracto** | Resumen del estado de un producto (saldo, deuda, etc.). |

## Qué hace el sistema
  - Transfiere dinero entre cuentas, cobra la comisión según el tipo de transferencia, guarda la
  transacción en Oracle, imprime el comprobante, avisa al cliente por SMS y deja un registro
  de auditoría.
  - Cobra la cuota de manejo mensual a una lista de cuentas.
  - Genera extractos de los productos de crédito (tarjeta y crédito de vivienda).
  La conexión a Oracle y el envío de SMS están simulados con mensajes en consola, pero
  imaginen que son reales: cada vez que se ejecutan, el sistema se conecta a la base de datos de
  producción y le llega un mensaje de texto al cliente.
