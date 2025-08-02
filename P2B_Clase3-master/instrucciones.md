# Simulador de Negocio de Comida Rápida - Evaluación de Corrección de Errores

## Descripción del Proyecto

Este proyecto es un simulador de negocio de comida rápida desarrollado en Java con Maven. El sistema permite:

- Registrar productos con precio y stock
- Crear pedidos con productos asociados
- Control básico de inventario (actualización de stock al vender)
- Calcular ingresos generados por pedidos
- Aplicar descuentos simples
- Persistencia de datos con SQLite

## ⚠️ IMPORTANTE: ERRORES INTENCIONALES

Este proyecto contiene **12 errores intencionales** distribuidos en diferentes categorías. Tu tarea es detectarlos, analizarlos y corregirlos.

### Tipos de Errores Incluidos:

1. **Errores Lógicos** (3-4 errores)
   - Cálculos incorrectos
   - Condiciones mal formuladas
   - Stock no actualizado

2. **Errores Estructurales** (2-3 errores)
   - Problemas de encapsulamiento
   - Uso incorrecto de atributos
   - Constructores sin validaciones

3. **Malas Prácticas** (2-3 errores)
   - Variables públicas innecesarias
   - Código duplicado
   - Falta de validaciones

4. **Errores con Colecciones** (2 errores)
   - Búsqueda en lista vacía
   - Índices fuera de rango

5. **Errores de Control de Flujo** (2 errores)
   - Bucles con condiciones incorrectas
   - Incrementos/decrementos erróneos

## Instrucciones de Evaluación

### Formato de Entrega

Por cada error encontrado, completa el siguiente formato:

**Error #[número]:**
- **Ubicación:** [Archivo.java] (línea [número])
- **Tipo de error:** [Lógico/Estructural/Mala práctica/Colecciones/Control de flujo]
- **Descripción del problema:** [¿Por qué es un error?]
- **Solución implementada:** [¿Cómo lo corregiste?]
- **Impacto:** [¿Qué problemas causaba?]

### Ejemplo de Formato:

**Error #1:**
- **Ubicación:** Producto.java (línea 8)
- **Tipo de error:** Estructural - Encapsulamiento
- **Descripción del problema:** Los atributos `id`, `nombre`, `precio` y `stock` son públicos, violando el principio de encapsulamiento.
- **Solución implementada:** Cambié todos los atributos a privados y creé métodos getter y setter apropiados.
- **Impacto:** Permitía acceso directo y modificación no controlada de los datos del producto.

## Errores a Buscar (Pistas)

Busca especialmente estos tipos de problemas:

### 🔍 Errores Lógicos
- Descuentos que aumentan el precio en lugar de reducirlo
- Validaciones de stock incorrectas (>, >= , <, <=)
- Cálculos de totales que no consideran cantidades
- Stock que no se actualiza después de las ventas

### 🔍 Errores Estructurales
- Atributos que deberían ser privados
- Constructores que no validan parámetros
- Métodos que permiten estados inválidos

### 🔍 Errores de Colecciones
- Acceso a índices sin verificar si la lista está vacía
- Bucles que pueden causar `IndexOutOfBoundsException`
- Condiciones de bucle incorrectas (!=, <=, >=)

### 🔍 Errores de Control de Flujo
- Contadores que decrecen cuando deberían incrementar
- Condiciones de bucle que pueden crear bucles infinitos
- Comparaciones incorrectas en condicionles

## Instrucciones de Ejecución

1. **Compilar el proyecto:**
   ```bash
   mvn clean compile
   ```

2. **Ejecutar el programa:**
   ```bash
   mvn exec:java -Dexec.mainClass="umg.edu.gt.Main"
   ```

3. **Probar funcionalidades:**
   - Crear pedidos
   - Agregar productos
   - Verificar inventario
   - Calcular totales
   - Aplicar descuentos

## Criterios de Evaluación

- **Detección de errores (60%):** Encontrar al menos 10 de los 12 errores
- **Análisis (20%):** Explicar correctamente por qué cada elemento es un error
- **Corrección (20%):** Implementar soluciones apropiadas que funcionen

## Entrega

1. **Código corregido:** Proyecto Maven completo y funcional
2. **Reporte de errores:** Documento con el formato especificado
3. **Pruebas:** Demostración de que el sistema funciona correctamente después de las correcciones

---

**Tiempo estimado:** 3-4 horas
**Dificultad:** Intermedia
**Objetivo:** Desarrollar habilidades de debugging, análisis de código y aplicación de buenas prácticas de programación.

¡Buena suerte encontrando y corrigiendo todos los errores! 🐛🔍
