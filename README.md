# Aplicación de Turismo Ecológico

Esta aplicación permite a los usuarios capturar fotos y grabar videos en el contexto del turismo ecológico. Además, incluye una galería para visualizar, seleccionar y eliminar archivos multimedia, junto con opciones de flash y filtros de mejora para optimizar las capturas en exteriores.

## Funcionalidades Principales
- **Captura de Fotos**: Con opciones de flash (auto, encendido, apagado) y filtros de color.
- **Grabación de Video**: Incluye control de flash.
- **Galería**: Permite ver, seleccionar y eliminar fotos y videos.
- **Opciones de Zoom**: Control deslizante de zoom en tiempo real.
- **Cambio de Cámara**: Permite alternar entre cámara frontal y trasera.

---

## Bibliotecas Utilizadas
- **CameraX**: Para la captura de fotos y grabación de videos.
- **RecyclerView**: Para mostrar la galería multimedia.
- **EXIF Interface**: Para ajustar la orientación de las imágenes.
- **MediaStore y ThumbnailUtils**: Para la gestión y generación de miniaturas de videos.
- **ConstraintLayout**: Para la creación de una interfaz de usuario adaptable y ordenada.

---

## Explicación de Funciones

### `MainActivity`
- **Función**: Muestra el menú principal con opciones para capturar fotos, grabar videos y ver la galería.
- **Diseño**: Botones centrados y un mensaje de agradecimiento personalizado en la parte superior.

### `PhotoCaptureActivity`
- **startCamera()**: Inicializa la cámara y aplica configuraciones iniciales.
- **takePhoto()**: Captura una foto y la guarda en el almacenamiento.
- **applyFilterToImage()**: Aplica un filtro a la imagen para mejorar colores en exteriores.
- **adjustBitmapOrientation()**: Ajusta la orientación de la imagen según los datos EXIF.
- **toggleFlashMode()**: Alterna entre los modos de flash (apagado, encendido, automático).
- **toggleFilter()**: Activa/desactiva el filtro de color para fotos.
- **Diseño**: Uso de CameraX para simplificar el cambio entre cámaras y mejorar capturas.

### `VideoCaptureActivity`
- **startCamera()**: Configura la cámara para grabación de video.
- **startRecording()**: Inicia la grabación de video y guarda el archivo.
- **toggleFlash()**: Permite activar o desactivar el flash durante la grabación.
- **toggleCamera()**: Cambia entre cámaras frontal y trasera.
- **Diseño**: Interfaz sencilla de grabación con control de flash tipo linterna.

### `GalleryAdapter`
- **bind()**: Muestra miniaturas de fotos y videos en la galería.
- **onClickListener**: Permite visualizar el archivo multimedia o eliminarlo.
- **Diseño**: Uso de miniaturas de video y carga de imágenes para mejorar la experiencia en galería.

### `PhotoViewerActivity`
- **Función**: Muestra una imagen seleccionada en pantalla completa.
- **Diseño**: Ofrece una vista detallada de la imagen en alta calidad.

### `VideoPlayerActivity`
- **Función**: Reproduce un video seleccionado en pantalla completa.
- **Diseño**: Reproducción en pantalla completa para una mejor experiencia de visualización.

---

## Decisiones de Diseño
- **Interfaz Centrada**: Los botones y opciones principales están centrados en pantalla para una apariencia minimalista.
- **Galería con Selección Múltiple**: La galería permite seleccionar y eliminar varios archivos a la vez.
- **Filtros Opcionales**: Filtro de mejora de colores naturales para fotos en exteriores.
- **Mensajes Claros**: Se muestran mensajes de confirmación en acciones importantes, y un mensaje de agradecimiento en la pantalla principal.

---
## autor

- [@KevinAlvardo](https://github.com/KevinAlvardo/parcial-camara)

---
## Installation

Instalación y Ejecución
Clona el repositorio.
Abre el proyecto en Android Studio.
Conecta un dispositivo o usa un emulador para probar.
Compila y ejecuta la aplicación.
## Ejemplo de Configuración de Archivos XML

### `activity_main.xml`

---
```xml
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/background_image">

    <!-- Texto de agradecimiento -->
    <TextView
        android:id="@+id/textViewThankYou"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Gracias por usar mi aplicación para turismo ecológico\nATTE: Kevin Gerardo Alvarado Moran"
        android:textStyle="bold"
        android:textSize="18sp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginTop="16dp"/>

    <!-- Botones de la aplicación en el centro de la pantalla -->
    <Button
        android:id="@+id/btnCapturePhoto"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Capturar Foto"
        app:layout_constraintTop_toBottomOf="@id/textViewThankYou"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <Button
        android:id="@+id/btnRecordVideo"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Grabar Video"
        app:layout_constraintTop_toBottomOf="@id/btnCapturePhoto"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <Button
        android:id="@+id/btnViewGallery"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="Ver Galería"
        app:layout_constraintTop_toBottomOf="@id/btnRecordVideo"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>
</androidx.constraintlayout.widget.ConstraintLayout>
---
