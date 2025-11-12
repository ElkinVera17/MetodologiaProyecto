**Sistema de Gestión de Inventario - Ferretería Carlín**

**Descripción del Proyecto** 

Este proyecto implementa un Sistema de Gestión de Inventario simple para la Ferretería Carlín. Fue desarrollado como un Producto Mínimo Viable (PMV) enfocándose en el núcleo de la funcionalidad (CRUD de productos y procesamiento de ventas) bajo el patrón Modelo-Vista-Controlador (MVC) utilizando Java Swing para la interfaz gráfica.

**Persistencia:** Los datos se almacenan en memoria (In-Memory) y se pierden al cerrar la aplicación (Ver ADR-002).

**Estructura del Repositorio** 

/

├── docs

│   ├── Informe\_Tecnico\_Proyecto.md (El informe completo de diseño y pruebas) │   └── ADR- 002\_Persistencia\_Memoria.md (Registro de decisión arquitectónica) ├── src

│   ├── App (Contiene Main.java)

│   ├── controller

│   │   └── InventoryController.java

│   └── model

│       ├── Inventory.java

│       ├── Product.java

│       └── Sale.java

├── tests (Contiene archivos de pruebas unitarias y de integración - No provistos en es ├── scripts (Scripts auxiliares de compilación/ejecución - No provistos en este paquete ├── README.md (Este archivo)

└── .env.example (Ejemplo de variables de entorno - No aplica por ahora)

1. **Prerrequisitos** 

![](Aspose.Words.56fa88d8-677e-44ad-9975-507b3d402974.001.png) **Java Development Kit (JDK):** Versión 11 o superior.

2. **Compilación y Ejecución** 

Siga estos pasos desde el directorio raíz del proyecto:

**Compilación (Asumiendo que las clases de la Vista están en** src/view/ **)**

- Crear directorio de binarios mkdir -p bin
- Compilar todos los archivos fuente

javac -d bin src/App/Main.java src/controller/InventoryController.java src/model/\*.java 

**Ejecución**

- Ejecutar la clase principal java -cp bin App.Main
3. **Uso** 

La aplicación se inicia con datos de muestra cargados en memoria. Utilice las pestañas para:

1. **Productos:** Agregar, modificar, o eliminar ítems del inventario.
1. **Ventas:** Registrar la venta de un producto, lo que actualiza automáticamente el stock.
