ISIS 4208 - Tarea6 - sufijos

El entregable se compone de los siguientes elementos:

1. textGenerator.py - Programa generador de Entradas

    este programa se puede usar para generar ambas entradas del problema usando la siguiente sintaxis:

    python3 textGenerator.py <path1> <path 2> <textLength> <numberOfQueries>

    donde:
    
    <path1> es la ruta donde se guardara el texto completo, no se necesita crear el archivo previamente
    <path2> es la ruta donde se guardarán las consultas, no se necesita crear el archivo previamente
    <textLength> es el largo del texto a generar
    <numberOfQueries> es el numero de consultas a generar

    Adicionalmente, en la línea 6 del script, la variable maxQueryLength se puede modificar para que el programa genere consultas de más o menos longitud.

2. textSearching.java - Programa principal

    este programa se encarga de crear el índice y ejecutar las consultas según los archivos de entrada. Para usarse debe ejecutarse con la siguiente sintaxis:

    java textSearching <path1> <path2> > <path_salida>

    donde:

    <path1> es la ruta donde se encuentra el texto completo
    <path2> es la ruta donde se encuentran las consultas
    <path_salida> es la ruta donde se guardará la respuesta de las consultas, que corresponde a la salida estandar.

3. Resultados_Tarea6.xslx - Archivo de pruebas

    Contiene los resultados de las pruebas realizadas

    Los resultados marcados con una x corresponden a pruebas no ejecutadas debido a que tardaban demasiado tiempo, el tiempo estimado se calculó en una columna separada teniendo en cuenta las siguientes complejidades:

    Ordenamiento de los resultados: n^2log(n) [n = cantidad de caracteres]
        el nlog(n) corresponde al mergesort/timsort de java al usar Arrays.sort con un comparador personalizado, el c^2 al inicio corresponde a la comparación de los sufijos, que en el peor caso equivale a comparar el texto entero.

    Búsqueda de las consultas: (c*(4*(n)*log(n)+(4*n)*2)) [c= cantidad de consultas, n = cantidad de caracteres del texto]

        el log(n) corresponde a la búsqueda binaria inicial, que siempre se hace completa aún hallando el sufijo antes de terminar (para hallar todas las repeticiones), los 4*n que aparecen 2 veces corresponden a extraer el sufijo y compararlo (completo y truncado al tamaño de la consulta) y agregarlo al arraylist, el 2 que multiplica corresponde al caso que se asemejaba a los resultados vistos en práctica, para buscar todas las ocurrencias en el peor caso debería buscarse y compararse la consulta con todos los sufijos, que correspondería a n^2. 