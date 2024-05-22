import { Almacen, Articulo, ArticuloError } from '../src/almacen';

describe('Restaurante', () => {
    let almacen: Almacen;

    beforeEach(() => {
        almacen = new Almacen();
    });

    test('insertarArticulo añade un articulo al stock', () => {
        const articulo: Articulo = { nombre: 'Articulo1', cantidad: 10 };
        almacen.insertarArticulo(articulo);
        
        expect(almacen.obtenerArticulosDisponibles()).toContainEqual(articulo);
    });

    test('actualizarStock reduce la cantidad de un articulo en el stock', () => {
        const articulo: Articulo = { nombre: 'Articulo1', cantidad: 10 };
        almacen.insertarArticulo(articulo);
        almacen.actualizarStock({ nombre: 'Articulo1', cantidad: 1 });
        
        const articuloActualizado = almacen.obtenerArticulosDisponibles().find(p => p.nombre === 'Articulo1');
        expect(articuloActualizado && articuloActualizado.cantidad).toBe(9);
    });

    test('actualizarStock lanza un error si el articulo no existe en el stock', () => {
        expect(() => almacen.actualizarStock({ nombre: 'ArticuloInexistente', cantidad: 1 }))
            .toThrow(ArticuloError.ArticuloNoEncontrado('ArticuloInexistente'));
    });

    test('actualizarStock lanza un error si la cantidad es mayor que la disponible en el stock', () => {
        const articulo: Articulo = { nombre: 'Articulo1', cantidad: 10 };
        almacen.insertarArticulo(articulo);
        
        expect(() => almacen.actualizarStock({ nombre: 'Articulo1', cantidad: 11 }))
            .toThrow(ArticuloError.ArticuloStockInsuficiente('Articulo1'));
    });

    test('actualizarStock lanza un error si la cantidad es negativa', () => {
        const articulo: Articulo = { nombre: 'Articulo1', cantidad: 10 };
        almacen.insertarArticulo(articulo);
        
        expect(() => almacen.actualizarStock({ nombre: 'Articulo1', cantidad: -1 }))
            .toThrow(ArticuloError.CantidadNegativa('Articulo1'));});
});
