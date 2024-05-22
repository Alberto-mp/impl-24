// Articulos del almacen
export class Articulo {
    nombre: string;
    cantidad: number; 

    constructor(nombre: string, cantidad: number) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }
}

// Error para los Articulos
export class ArticuloError extends Error {
    private constructor(message: string) {
        super(message);
        this.name = 'ArticuloError';
    }

    public static ArticuloNoEncontrado(nombre: string): ArticuloError {
        return new ArticuloError(`El articulo ${nombre} no está disponible en el almacen.`);
    }

    public static ArticuloStockInsuficiente(nombre: string): ArticuloError {
        return new ArticuloError(`No hay suficientes articulos ${nombre} en el almacen.`);
    }

    public static CantidadNegativa(nombre: string): ArticuloError {
        return new ArticuloError(`La cantidad de articulos ${nombre} no puede ser negativa.`);
    }
}

// Clase principal del Almacen
export class Almacen {
    private stockArticulos: Articulo[] = [];

    public obtenerArticulosDisponibles(): Articulo[] {
        return this.stockArticulos;
    }

    public insertarArticulo(articulo: Articulo): void {
        this.stockArticulos.push(articulo);
    }

    // Resta el stock de los articulos
    public actualizarStock(articuloPedido: Articulo): void {
        // Verifica la disponibilidad del artículo antes de actualizar el stock
        this.verificarDisponibilidadArticulo(articuloPedido.nombre, articuloPedido.cantidad);

        // Si el artículo está disponible, actualiza el stock
        const articulo = this.obtenerArticulosDisponibles().find(a => a.nombre === articuloPedido.nombre);
        if (articulo !== undefined) {
            articulo.cantidad -= articuloPedido.cantidad; // Resta la cantidad de articulos del stock
        }
    }

    public verificarDisponibilidadArticulo(nombre: string, cantidad: number): void {
        const articuloStock = this.obtenerArticulosDisponibles().find(a => a.nombre === nombre);

        // Comprueba si el articulo existe
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