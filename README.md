Descripción de la Aplicación
Esta aplicación consiste en un juego simple que se divide en cuatro partes principales:

1. Archivo XML
El archivo XML contiene la única imagen utilizada en la aplicación. Esta imagen se compone de un canvas donde se dibujan elementos gráficos, así como dos textos: uno para mostrar el puntaje y el estado de la partida (victoria o derrota), y otro para iniciar o pausar la partida.

2. Carpeta de Sonidos
En esta carpeta se encuentran los archivos de sonido en formato raw que la aplicación utiliza. Los sonidos son esenciales para proporcionar una experiencia auditiva al usuario durante el juego.

3. Sound Manager
Este componente de la aplicación se encarga de cargar los sonidos y proporciona un método de reproducción para ser llamado desde la parte principal de la aplicación.

4. Main
El archivo principal de la aplicación, que gestiona el canvas y todas las interacciones del juego. Algunos de los métodos más importantes incluyen:

- drawCanvas: Dibuja los elementos visuales en el canvas, incluyendo triángulos separados por un límite y un círculo negro superpuesto.

- changeColor: Modifica dinámicamente el brillo de los colores utilizados en el juego, utilizando un valor de alfa y un entero como parámetros.

- detectTouch: Detecta los clics del usuario, identifica el centro para iniciar o pausar la partida, y maneja la detección de colores. También bloquea los clics si el juego está en curso.

- startGame: Prepara y ejecuta el inicio de una nueva partida.

- createGameArray: Genera un array de secuencia aleatoria cada vez que se inicia una partida.

- loseGame: Finaliza la partida y muestra el mensaje de derrota cuando el jugador pierde.

- playArray: Reproduce la secuencia de colores del juego, aumentando la dificultad con cada ronda y ajustando el brillo de los colores.

- comparaClick: Compara el clic del jugador con la secuencia correcta para determinar si es válido.

Esta estructura modular ayuda a mantener el código organizado y facilita la comprensión y el mantenimiento de la aplicación.
