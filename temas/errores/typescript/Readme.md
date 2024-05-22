# Ejemplo Pedidos de Artículos a un Almacén 
Este ejemplo sirve para reflejar el manejo de errores y el uso de `undefined` en TypeScript.

El ejemplo se basa en realizar pedidos de artículos a un almacén, cada pedido tiene un número de artículos y la cantidad solicitada. El almacén tiene unos artículos determinados y una cantidad en stock por cada artículo. El objetivo es comprobar que se puede realizar el pedido de todos los artículos con éxito, y una vez realizado el pedido con éxito se actualiza el stock del almacén.

## Breve introducción a Typescript
TypeScript es un lenguaje de programación desarrollado por Microsoft que mejora JavaScript utilizando tipado estático. Esto hace que el código sea más sólido seguro y escalable. Se compila a JavaScript y además tiene herramientas que facilitan el desarrollo, como autocompletado y verificación de tipos. Es compatible con el ecosistema de JavaScript, lo que permite la compatibilidad con JavaScript.

## Explicación de [almacen.ts](./src/almacen.ts)
``` typescript
export class Articulo {
    nombre: string;
    cantidad: number; 

    constructor(nombre: string, cantidad: number) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }
}
```
La clase ´Articulo´ representa un artículo de un almacén. 
Tiene dos propiedades:
-  `nombre`: nombre del artículo.
-  `cantidad`: cantidad del artículo .

``` typescript
export class ArticuloError extends Error {
    private constructor(message: string) {
        super(message);
        this.name = 'ArticuloError';
    }

    public static ArticuloNoEncontrado(nombre: string): ArticuloError {
        return new ArticuloError(`El articulo ${nombre} no está disponible en el almacen.`);
    }

    // Resto de métodos estáticos para manejar diferentes errores
}
```
La clase `ArticuloError` extiende la clase `Error` y es utilizada para manejar los errores relacionados con los artículos de un almacén.
Define métodos estáticos para crear instancias de errores específicos:
- `ArticuloNoEncontrado(nombre: string)`
- `ArticuloStockInsuficiente(nombre: string)`
- `CantidadNegativa(nombre: string)`

```typescript
export class Almacen {
    private stockArticulos: Articulo[] = [];

    // Métodos para obtener artículos, insertar artículos, actualizar el stock y verificar la disponibilidad de los artículos
}
```
La clase `Almacen` representa un almacén. Contiene un array `stockArtículos` que almacena la cantidad en sotck de los artículos.
Proporciona métodos para introducir artículos y obtenerlos.

```typescript
export class Almacen {
    public actualizarStock(articuloPedido: Articulo): void {
        // Verifica la disponibilidad del artículo antes de actualizar el stock
        this.verificarDisponibilidadArticulo(articuloPedido.nombre, articuloPedido.cantidad);

        // Si el artículo está disponible, actualiza el stock
        const articulo = this.obtenerArticulosDisponibles().find(a => a.nombre === articuloPedido.nombre);
        if (articulo !== undefined) {
            articulo.cantidad -= articuloPedido.cantidad; // Resta la cantidad de artículos del stock
        }
    }
}
```
El método `actualizarStock` de `Almacen`se encarga de restar la cantidad de un artículo al stock del mismo den almacén.
Primero llama a `verificarDisponibilidadArticulo` para comprobar la disponibilidad de dicho artículo.
Finalmente busca el artículo en el almacén y si existe resta la cantidad que se ha solicitado al stock del almacén.

```typescript
export class Almacen {
    public verificarDisponibilidadArticulo(nombre: string, cantidad: number): void {
        const articuloStock = this.obtenerArticulosDisponibles().find(a => a.nombre === nombre);

        // Comprueba si el artículo existe
        if (articuloStock === undefined)
            throw ArticuloError.ArticuloNoEncontrado(nombre);

        // Comprueba si la cantidad es negativa
        else if (cantidad < 0)
            throw ArticuloError.CantidadNegativa(nombre);

        // Comprueba si hay suficiente stock
        else if (articuloStock.cantidad < cantidad)
            throw ArticuloError.ArticuloStockInsuficiente(nombre);
    }
}
```
El método `verificarDisponibilidadArticulo ` de ´Almacen´ se utiliza para verificar si un artículo se encuentra dispoible en el almacén y si hay suficiente cantidad en stock del mismo.
Primero busca el artículo y si no lo encuentra lanza un errpr indicando que no se ha encontrado.
Después verifica si la cantidad solicitada es negativa, lanza un error si se da el caso.
Finalmente verifica si el stock en el almacén es suficiente, sino lanza un error.

## Explicación de [PedidoService.ts](./src/pedidoService.ts)

``` typescript
export class PedidoError extends Error {
    private constructor(message: string) {
        super(message);
        this.name = 'PedidoError';
    }

    public static PedidoVacio(): PedidoError {
        return new PedidoError('El pedido no puede estar vacío.');
    }
}
```
La clase `PedidoError` extiende la clase `Error` y es utilizada para manejar los errores relacionados con los pedidos.
Define un método estático `PedidoVacio` para crear instancias del error específico.

``` typescript
export class PedidoService {
    private almacen: Almacen;

    constructor(almacen: Almacen) {
        this.almacen = almacen;
    }

    public async realizarPedido(pedido: Articulo[]): Promise<void> {
        // Comprueba si el pedido está vacío
        if (pedido === undefined || pedido.length === 0) throw PedidoError.PedidoVacio();

        // Verifica la disponibilidad de cada artículo antes de realizar el pedido
        pedido.forEach(articuloPedido => {
            this.almacen.verificarDisponibilidadArticulo(articuloPedido.nombre, articuloPedido.cantidad);
        });

        // Actualiza el stock de los artículos
        pedido.forEach(articuloPedido => {
            this.almacen.actualizarStock(articuloPedido);
        });

        return undefined;
    }
}
```

La clase `PedidoService` se encarga de realizar los pedidos al ´Almacen´.
El método `realizarPedido` recibe un array de objetos `Articulo`, primero comprueba si el pedido esta vacío, después verifica la disponibilidad de cada artículo en el almacén y finalmente actualiza el stock. Además devuelve una promesa que se resuelve con `undefined`.
En este método se utiliza `undefined` para devolver una promesa que no tiene un valor específico asociado.

El código  utiliza clases específicas de error y promesas para manejar errores y resultados, mientras que `undefined` se utiliza para indicar la ausencia de valor en ciertos contextos, como en el retorno de la promesa y en las verificaciones de pedidos vacíos.

## Explicación de [pedidoController.ts](./src/pedidoController.ts.ts)
Este código crea un servidor web usando Express, que está escuchando en el puerto 3300. Cuando recibe una solicitud en la ruta '/realizarPedido', llama a la función realizarPedido para realizar el pedido al almacén.

## Explicación de [index.html](./web/index.html)
Web diseñada para mostrar los resultados del ejemplo.
``` javascript
document.getElementById('añadirArticulo').addEventListener('click', () => {
    const nombreArticulo = document.getElementById('nombreArticulo').value;
    const cantidadArticulo = document.getElementById('cantidadArticulo').value;

    // Añade el artículo a la lista
    articulos.push({ nombre: nombreArticulo, cantidad: Number(cantidadArticulo) });

    // Muestra el artículo por pantalla
    const li = document.createElement('li');
    li.textContent = `${nombreArticulo} - ${cantidadArticulo}`;
    document.getElementById('listaArticulos').appendChild(li);
});    
```
EventListener para detectar cuando se ha pulsado el botón ´añadir´, y se añade el artículo especificado.

``` javascript
document.getElementById('realizarPedido').addEventListener('click', async () => {           
    try {
    const response = await fetch('http://localhost:3300/realizarPedido', 
    
    /* ...... Resto del código ...... */

});
```

Función encargada de envíar los pedidos a través de una solicitud HTTP.

## Instalar Dependencias
Se necesita `npm`, se puede instalar con algún gestor de packetes.
### Instalar `node.js`:
```
npm install npm@latest -g
```
###<a name="dependencias"></a> Instalar las dependencias
```
RUN npm install
```

## Pruebas
Para las pruebas se utiliza [almacen.test.ts](./test/almacen.test.ts). y [pedidoService.test.ts](./test/pedidoService.test.ts)
Las pruebas se realizan con `Jest`, instalar antes las [dependencias](#dependencias).

### Ejecutar los test
```
npm test
```

## Construir y desplegar
### Crear la imagen de docker
```
docker build -t almacen_app . 
```
### Desplegar el contenedor con la aplicación
```
terraform init
terraform apply
```
Una vez se haya iniciado correctamente, la web estará dispoible en:
`localhost:3300`

### Detener el contenedor
```
terraform destroy
```